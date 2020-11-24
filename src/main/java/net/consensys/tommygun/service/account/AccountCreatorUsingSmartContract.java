package net.consensys.tommygun.service.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import net.consensys.tommygun.boot.TommyGunConfiguration;
import net.consensys.tommygun.contract.wrapper.AccountCreator;
import net.consensys.tommygun.model.account.AccountCreatorContractInfo;
import net.consensys.tommygun.service.SmartContractDeployer;
import net.consensys.tommygun.service.retry.RecoverableEthereumTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

@Service
@Slf4j
public class AccountCreatorUsingSmartContract {
  @Autowired private Web3j web3j;

  @Autowired
  @Qualifier("accountGeneratorCredentials")
  private Credentials accountCreatorCredentials;

  @Autowired private TommyGunConfiguration configuration;
  private final ContractGasProvider contractGasProvider = new DefaultGasProvider();
  private final ContractGasProvider createFunctionGasProvider = new CreateFunctionGasProvider();
  @Autowired private SmartContractDeployer smartContractDeployer;

  public Optional<String> deployAccountCreatorContract() throws IOException {
    final Optional<String> address =
        smartContractDeployer.deploy(
            configuration.getChainID(),
            accountCreatorCredentials,
            AccountCreator.BINARY,
            Convert.toWei(
                    BigDecimal.valueOf(configuration.getAccountCreatorContractInitialValueEther()),
                    Convert.Unit.ETHER)
                .toBigInteger());
    address.ifPresent(configuration::setAccountCreatorContractAddress);
    return address;
  }

  public void create(final long accountNumber) {
    try {
      log.info("starting creation of {} accounts.", accountNumber);
      final TransactionManager transactionManager =
          configuration.getChainID().isPresent()
              ? new RawTransactionManager(
                  web3j, accountCreatorCredentials, configuration.getChainID().get())
              : new RawTransactionManager(web3j, accountCreatorCredentials);
      final String contractAddress = configuration.getAccountCreatorContractAddress().orElseThrow();
      log.info("loading account creator contract instance at: {}", contractAddress);
      final AccountCreator accountCreator =
          AccountCreator.load(
              contractAddress, web3j, transactionManager, createFunctionGasProvider);
      log.info("account creator contract is valid: {}", accountCreator.isValid());
      log.info(
          "contact balance: {}",
          web3j
              .ethGetBalance(contractAddress, DefaultBlockParameterName.LATEST)
              .send()
              .getBalance());
      final long iterations = accountNumber / configuration.getAccountCreatorContractSlice();
      final long remainder =
          accountNumber - (iterations * configuration.getAccountCreatorContractSlice());
      for (long i = 0; i < iterations; i++) {
        // TODO run with retries instead
        RecoverableEthereumTransaction.runIgnoreTransactionException(
            () -> {
              createUsingSmartContract(
                  accountCreator, configuration.getAccountCreatorContractSlice());
              return null;
            });
      }
      if (remainder > 0) {
        createUsingSmartContract(accountCreator, remainder);
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  public void createUsingSmartContract(final AccountCreator accountCreator, final long number)
      throws Exception {
    log.info("creating {} accounts using smart contract", number);
    final TransactionReceipt transactionReceipt =
        accountCreator.create(BigInteger.valueOf(number)).send();
    final String transactionHash = transactionReceipt.getTransactionHash();
    log.info("transaction hash: {}", transactionHash);
  }

  public AccountCreatorContractInfo getAccountCreatorContractInfo() throws Exception {
    final TransactionManager transactionManager =
        configuration.getChainID().isPresent()
            ? new RawTransactionManager(
                web3j, accountCreatorCredentials, configuration.getChainID().get())
            : new RawTransactionManager(web3j, accountCreatorCredentials);
    final AccountCreator accountCreator =
        AccountCreator.load(
            configuration.getAccountCreatorContractAddress().orElseThrow(),
            web3j,
            transactionManager,
            contractGasProvider);
    return AccountCreatorContractInfo.builder()
        .accountsCreated(accountCreator.getAccountsCreated().send().longValue())
        .lastCreatedAddress(accountCreator.getLastCreatedAddress().send())
        .build();
  }

  public static class CreateFunctionGasProvider extends StaticGasProvider {
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(1_000_000_000_000_000L);
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(1_000L);

    public CreateFunctionGasProvider() {
      super(GAS_PRICE, GAS_LIMIT);
    }
  }
}
