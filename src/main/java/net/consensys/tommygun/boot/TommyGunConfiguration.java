package net.consensys.tommygun.boot;

import java.util.Optional;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@ToString
public class TommyGunConfiguration {
  @Value("${rpc-url:http://localhost:8545}")
  private String rpcUrl;

  @Value("${chain-id:#{null}}")
  private Optional<Long> chainID;

  @Value("${account-creator-private-key}")
  private String accountCreatorPrivateKey;

  @Value("${state-storage-creator-private-key}")
  private String stateStorageCreatorPrivateKey;

  @Value("${key-value-store-contract-address:#{null}}")
  private Optional<String> keyValueStoreContractAddress;

  public void setKeyValueStoreContractAddress(final String address) {
    this.keyValueStoreContractAddress = Optional.ofNullable(address);
  }
}
