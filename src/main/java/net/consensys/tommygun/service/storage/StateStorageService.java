package net.consensys.tommygun.service.storage;

import java.io.IOException;
import java.math.BigInteger;

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
import org.web3j.protocol.core.methods.response.EthSendTransaction;
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
      // TODO get transaction receipt to retrieve the contract address
      // web3j.ethGetTransactionReceipt()
      configuration.setKeyValueStoreContractAddress(null);
      return null;
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
    log.info("transaction sent with hash: {}", ethSendTransactionResponse.getTransactionHash());
    return ethSendTransactionResponse.getTransactionHash();
  }
}
