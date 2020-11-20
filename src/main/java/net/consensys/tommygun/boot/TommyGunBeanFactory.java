package net.consensys.tommygun.boot;

import net.consensys.tommygun.repository.TaskRepository;
import net.consensys.tommygun.repository.inmem.InMemTaskRepository;
import net.consensys.tommygun.util.LargeGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;

@Configuration
public class TommyGunBeanFactory {

  @Autowired private TommyGunConfiguration configuration;

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(configuration.getRpcUrl()));
  }

  @Bean
  @Qualifier("accountGeneratorCredentials")
  public Credentials accountGeneratorCredentials() {
    return Credentials.create(configuration.getAccountCreatorPrivateKey());
  }

  @Bean
  @Qualifier("stateStorageCreatorCredentials")
  public Credentials stateStorageCreatorCredentials() {
    return Credentials.create(configuration.getStateStorageCreatorPrivateKey());
  }

  @Bean
  public TaskRepository taskRepository() {
    return new InMemTaskRepository();
  }

  @Bean
  public ContractGasProvider contractGasProvider() {
    return new LargeGasProvider();
  }
}
