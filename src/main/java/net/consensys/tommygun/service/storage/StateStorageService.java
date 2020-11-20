package net.consensys.tommygun.service.storage;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import net.consensys.tommygun.boot.TommyGunConfiguration;
import net.consensys.tommygun.contract.wrapper.KeyValueStore;
import net.consensys.tommygun.model.state.StorageContractInfo;
import net.consensys.tommygun.model.state.StorageEntry;
import net.consensys.tommygun.model.task.StatusChangeListener;
import net.consensys.tommygun.model.task.Task;
import net.consensys.tommygun.model.task.TaskType;
import net.consensys.tommygun.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

@Service
@Slf4j
public class StateStorageService {
  @Autowired private Web3j web3j;
  @Autowired private StorageEntryGenerator storageEntryGenerator;
  @Autowired ContractGasProvider contractGasProvider;

  @Autowired
  @Qualifier("stateStorageCreatorCredentials")
  private Credentials stateStorageCreatorCredentials;

  @Autowired private TaskService taskService;

  @Autowired private TommyGunConfiguration configuration;

  public Task triggerFillStorage(
      final UUID parentTaskID,
      final long stateEntriesNumber,
      final long stateEntrySize,
      final StatusChangeListener statusChangeListener) {
    final Task task =
        taskService.newTask(
            UUID.randomUUID(),
            String.format(TaskType.FILL_STORAGE.getType(), stateEntriesNumber, stateEntrySize),
            () -> this.fillStorage(stateEntriesNumber, stateEntrySize),
            Optional.of(parentTaskID));
    task.addStatusChangeListener(statusChangeListener);
    return task;
  }

  public void fillStorage(final long stateEntriesNumber, final long stateEntrySize) {
    try {
      final String contractAddress =
          configuration
              .getKeyValueStoreContractAddress()
              .orElseThrow(() -> new RuntimeException("no key value store contract address found"));
      log.info("starting fill storage with {} entries.", stateEntriesNumber);
      final TransactionManager transactionManager =
          configuration.getChainID().isPresent()
              ? new RawTransactionManager(
                  web3j, stateStorageCreatorCredentials, configuration.getChainID().get())
              : new RawTransactionManager(web3j, stateStorageCreatorCredentials);
      log.info("loading key value store contract instance at: {}", contractAddress);
      final KeyValueStore keyValueStore =
          KeyValueStore.load(contractAddress, web3j, transactionManager, contractGasProvider);
      final BigInteger storeSize = keyValueStore.storeSize().send();
      final long startEntryKey = storeSize.longValue();
      int multiplier = 0;
      for (long i = startEntryKey; i < stateEntriesNumber + startEntryKey; i++) {
        putSingleEntry(i + (multiplier * stateEntrySize), stateEntrySize, keyValueStore);
        multiplier++;
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  public void putSingleEntry(
      final long entryKey, final long stateEntrySize, final KeyValueStore keyValueStore)
      throws Exception {
    final StorageEntry storageEntry = storageEntryGenerator.newStorageEntry(entryKey);
    log.info("generated storage entry: {}", storageEntry);
    final TransactionReceipt transactionReceipt =
        keyValueStore
            .fill(
                BigInteger.valueOf(entryKey),
                BigInteger.valueOf(stateEntrySize),
                storageEntry.getValue())
            .sendAsync()
            .get();
    final String transactionHash = transactionReceipt.getTransactionHash();
    log.info("transaction hash: {}", transactionHash);
  }

  public String deploySmartContract() {
    try {
      log.info(
          "current state storage smart contract address: {}",
          configuration.getKeyValueStoreContractAddress());
      final String transactionHash = deploy();
      log.info("transaction hash: {}", transactionHash);
      final Optional<String> contractAddress = retrieveContractAddress(transactionHash);
      configuration.setKeyValueStoreContractAddress(contractAddress.orElse(null));
      return contractAddress.orElse(null);
    } catch (final Exception e) {
      log.error("cannot deploy state storage smart contract", e);
      throw new RuntimeException(e);
    }
  }

  private String deploy() throws IOException {
    final long nonce =
        web3j
            .ethGetTransactionCount(
                stateStorageCreatorCredentials.getAddress(), DefaultBlockParameterName.LATEST)
            .send()
            .getTransactionCount()
            .longValue();
    final RawTransaction contractTransaction =
        RawTransaction.createContractTransaction(
            BigInteger.valueOf(nonce),
            DefaultGasProvider.GAS_PRICE,
            DefaultGasProvider.GAS_LIMIT,
            BigInteger.ZERO,
            KeyValueStore.SIMPLE_STORE_BINARY);
    final byte[] signedTransaction;

    if (configuration.getChainID().isPresent()) {
      signedTransaction =
          TransactionEncoder.signMessage(
              contractTransaction,
              configuration.getChainID().get(),
              stateStorageCreatorCredentials);
    } else {
      signedTransaction =
          TransactionEncoder.signMessage(contractTransaction, stateStorageCreatorCredentials);
    }

    final EthSendTransaction ethSendTransactionResponse =
        web3j.ethSendRawTransaction(Numeric.toHexString(signedTransaction)).send();
    return ethSendTransactionResponse.getTransactionHash();
  }

  public Optional<String> retrieveContractAddress(final String transactionHash) {
    log.info("waiting for transaction receipt: {}", transactionHash);
    final BlockingQueue<String> contractAddressQueue = new LinkedBlockingDeque<>();
    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(pollTransactionReceipt(transactionHash, contractAddressQueue));
    String contractAddress = null;
    try {
      contractAddress = contractAddressQueue.poll(2, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      log.error("cannot retrieve transaction receipt", e);
    } finally {
      log.info("shutdown transaction receipt polling");
      executorService.shutdownNow();
    }
    return Optional.ofNullable(contractAddress);
  }

  private Runnable pollTransactionReceipt(
      final String transactionHash, final BlockingQueue<String> contractAddressQueue) {
    return () -> {
      try {
        String contractAddress = null;
        while (contractAddress == null) {
          final EthGetTransactionReceipt transactionReceiptResponse =
              web3j.ethGetTransactionReceipt(transactionHash).send();
          if (transactionReceiptResponse != null
              && transactionReceiptResponse.getTransactionReceipt().isPresent()) {
            final TransactionReceipt transactionReceipt =
                transactionReceiptResponse.getTransactionReceipt().get();
            log.info("found transaction receipt: {}", transactionReceipt);
            contractAddress = transactionReceipt.getContractAddress();
          }

          Thread.sleep(500);
        }
        contractAddressQueue.put(contractAddress);
      } catch (IOException | InterruptedException e) {
        log.error("error occurred while polling transaction receipt", e);
      }
    };
  }

  public StorageContractInfo getStorageContractInfo() throws Exception {
    return getStorageContractInfo(configuration.getKeyValueStoreContractAddress().orElseThrow());
  }

  public StorageContractInfo getStorageContractInfo(final String address) throws Exception {
    final TransactionManager transactionManager =
        configuration.getChainID().isPresent()
            ? new RawTransactionManager(
                web3j, stateStorageCreatorCredentials, configuration.getChainID().get())
            : new RawTransactionManager(web3j, stateStorageCreatorCredentials);
    final KeyValueStore keyValueStore =
        KeyValueStore.load(address, web3j, transactionManager, contractGasProvider);
    final BigInteger storeSize = keyValueStore.storeSize().send();
    return StorageContractInfo.builder().stateEntriesNumber(storeSize.longValue()).build();
  }
}
