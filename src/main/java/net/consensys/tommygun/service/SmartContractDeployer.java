package net.consensys.tommygun.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SmartContractDeployer {
  @Autowired private Web3j web3j;

  public Optional<String> deploy(
      final Optional<Long> chainID,
      final Credentials credentials,
      final String contractBinary,
      final BigInteger initialValue)
      throws IOException {
    final String transactionHash =
        sendContractCreationTransaction(chainID, credentials, contractBinary, initialValue);
    log.info("transaction hash: {}", transactionHash);
    return retrieveContractAddress(transactionHash);
  }

  private String sendContractCreationTransaction(
      final Optional<Long> chainID,
      final Credentials credentials,
      final String contractBinary,
      final BigInteger initialValue)
      throws IOException {
    final long nonce =
        web3j
            .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST)
            .send()
            .getTransactionCount()
            .longValue();
    final RawTransaction contractTransaction =
        RawTransaction.createContractTransaction(
            BigInteger.valueOf(nonce),
            DefaultGasProvider.GAS_PRICE,
            DefaultGasProvider.GAS_LIMIT,
            initialValue,
            contractBinary);
    final byte[] signedTransaction;

    if (chainID.isPresent()) {
      signedTransaction =
          TransactionEncoder.signMessage(contractTransaction, chainID.get(), credentials);
    } else {
      signedTransaction = TransactionEncoder.signMessage(contractTransaction, credentials);
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
