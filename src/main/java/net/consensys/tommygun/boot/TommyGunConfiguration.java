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

  @Value("${use-smart-contract-for-account-creation:#{true}}")
  private boolean useSmartContractForAccountCreation;

  @Value("${account-creator-contract-address:#{null}}")
  private Optional<String> accountCreatorContractAddress;

  @Value("${account-creator-contract-slice:#{100000}}")
  private long accountCreatorContractSlice;

  @Value("${account-creator-contract-initial-value-ether:#{1}}")
  private long accountCreatorContractInitialValueEther;

  @Value("${account-creator-retries:#{3}}")
  private long accountCreatorRetries;

  public void setKeyValueStoreContractAddress(final String address) {
    this.keyValueStoreContractAddress = Optional.ofNullable(address);
  }

  public void setAccountCreatorContractAddress(final String address) {
    this.accountCreatorContractAddress = Optional.ofNullable(address);
  }
}
