package net.consensys.tommygun.service.storage;

import lombok.extern.slf4j.Slf4j;
import net.consensys.tommygun.boot.StateStorageServiceConfiguration;
import net.consensys.tommygun.contract.wrapper.KeyValueStore;
import net.consensys.tommygun.error.EthereumNodeUnreachable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

import java.net.ConnectException;

@Service
@Slf4j
public class StateStorageService {
  @Autowired private Web3j web3j;

  @Autowired
  @Qualifier("stateStorageCreatorCredentials")
  private Credentials stateStorageCreatorCredentials;

  @Autowired private StateStorageServiceConfiguration stateStorageServiceConfiguration;

  public String deploySmartContract() {
    try {
      log.info(
          "current state storage smart contract address: {}",
          stateStorageServiceConfiguration.getKeyValueStoreContractAddress());
      final KeyValueStore keyValueStore =
          KeyValueStore.deploy(web3j, stateStorageCreatorCredentials, new DefaultGasProvider())
              .send();
      final String contractAddress = keyValueStore.getContractAddress();
      stateStorageServiceConfiguration.setKeyValueStoreContractAddress(contractAddress);
      return contractAddress;
    } catch (final ConnectException e) {
      log.error("ethereum node connection error", e);
      throw new EthereumNodeUnreachable(e);
    } catch (final Exception e) {
      log.error("cannot deploy state storage smart contract", e);
      throw new RuntimeException(e);
    }
  }

}
