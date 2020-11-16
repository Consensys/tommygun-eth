package net.consensys.tommygun.boot;

import net.consensys.tommygun.repository.TaskRepository;
import net.consensys.tommygun.repository.inmem.InMemTaskRepository;
import net.consensys.tommygun.service.account.AccountCreatorService;
import net.consensys.tommygun.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class TommyGunBeanFactory {
  @Value("${rpc-url:http://localhost:8545}")
  private String rpcUrl;

  @Value("${account-creator-private-key}")
  private String accountCreatorPrivateKey;

  @Value("${state-storage-creator-private-key}")
  private String stateStorageCreatorPrivateKey;

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(rpcUrl));
  }

  @Bean
  @Qualifier("accountGeneratorCredentials")
  public Credentials accountGeneratorCredentials() {
    return Credentials.create(accountCreatorPrivateKey);
  }

  @Bean
  @Qualifier("stateStorageCreatorCredentials")
  public Credentials stateStorageCreatorCredentials() {
    return Credentials.create(stateStorageCreatorPrivateKey);
  }

  @Bean
  public AccountCreatorService accountCreatorService(
      @Autowired final Web3j web3j,
      @Autowired final TaskService taskService,
      @Autowired @Qualifier("accountGeneratorCredentials")
          final Credentials accountGeneratorCredentials) {
    return new AccountCreatorService(web3j, accountGeneratorCredentials, taskService);
  }

  @Bean
  public TaskRepository taskRepository() {
    return new InMemTaskRepository();
  }
}
