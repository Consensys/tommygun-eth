package net.consensys.tommygun.service.storage;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import net.consensys.tommygun.boot.TommyGunConfiguration;
import net.consensys.tommygun.contract.wrapper.KeyValueStore;
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
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

@Service
@Slf4j
public class StateStorageService {
  @Autowired private Web3j web3j;

  @Autowired
  @Qualifier("stateStorageCreatorCredentials")
  private Credentials stateStorageCreatorCredentials;

  @Autowired private TommyGunConfiguration configuration;

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
            KeyValueStore.BINARY);
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
}
