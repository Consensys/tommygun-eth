package net.consensys.tommygun.boot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateStorageServiceConfiguration {

  @Value("${key-value-store-contract-address:0x}")
  private String keyValueStoreContractAddress;

  public String getKeyValueStoreContractAddress() {
    return keyValueStoreContractAddress;
  }

  public void setKeyValueStoreContractAddress(final String keyValueStoreContractAddress) {
    this.keyValueStoreContractAddress = keyValueStoreContractAddress;
  }
}
