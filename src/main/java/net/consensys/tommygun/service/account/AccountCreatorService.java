package net.consensys.tommygun.service.account;

import static net.consensys.tommygun.util.EthereumConfiguration.ADDRESS_LEN_STR;
import static net.consensys.tommygun.util.EthereumConfiguration.DEFAULT_GAS_LIMIT;
import static net.consensys.tommygun.util.EthereumConfiguration.DEFAULT_GAS_PRICE;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import net.consensys.tommygun.boot.TommyGunConfiguration;
import net.consensys.tommygun.model.task.StatusChangeListener;
import net.consensys.tommygun.model.task.Task;
import net.consensys.tommygun.model.task.TaskType;
import net.consensys.tommygun.service.task.TaskService;
import net.consensys.tommygun.util.NonceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

@Service
@Slf4j
public class AccountCreatorService {
  private static final long ACCOUNT_CREATION_START_NUMBER = 1000;
  @Autowired private Web3j web3j;

  @Autowired
  @Qualifier("accountGeneratorCredentials")
  private Credentials accountCreatorCredentials;

  @Autowired private TaskService taskService;
  @Autowired private TommyGunConfiguration configuration;
  @Autowired private AccountCreatorUsingSmartContract accountCreatorUsingSmartContract;

  public Task triggerAccountsCreation(
      final UUID parentTaskID,
      final long accountNumber,
      final StatusChangeListener statusChangeListener) {
    final Task task =
        taskService.newTask(
            UUID.randomUUID(),
            String.format(TaskType.CREATE_ACCOUNT.getType(), accountNumber),
            getAccountCreatorProcess(accountNumber),
            Optional.of(parentTaskID));
    task.addStatusChangeListener(statusChangeListener);
    return task;
  }

  private Runnable getAccountCreatorProcess(final long accountNumber) {
    if (configuration.isUseSmartContractForAccountCreation()
        && configuration.getAccountCreatorContractAddress().isPresent()) {
      return () -> accountCreatorUsingSmartContract.create(accountNumber);
    } else {
      return () -> this.create(accountNumber);
    }
  }

  public void create(final long accountNumber) {
    try {
      log.info("starting creation of {} accounts.", accountNumber);
      final AtomicLong nonce = NonceUtil.getNonce(web3j, accountCreatorCredentials.getAddress());
      for (long i = ACCOUNT_CREATION_START_NUMBER;
          i < ACCOUNT_CREATION_START_NUMBER + accountNumber;
          i++) {
        createSingleAccount(nonce, createAddressForAccountNumber(i));
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  public void createSingleAccount(final AtomicLong nonce, final String address) throws IOException {
    log.info("creating account for address: {}", address);
    final RawTransaction etherTransaction =
        RawTransaction.createEtherTransaction(
            BigInteger.valueOf(nonce.getAndIncrement()),
            DEFAULT_GAS_PRICE,
            DEFAULT_GAS_LIMIT,
            address,
            BigInteger.ONE);
    final byte[] signedTransaction =
        TransactionEncoder.signMessage(etherTransaction, accountCreatorCredentials);
    final EthSendTransaction ethSendTransactionResponse =
        web3j.ethSendRawTransaction(Numeric.toHexString(signedTransaction)).send();
    log.info("transaction sent with hash: {}", ethSendTransactionResponse.getTransactionHash());
  }

  public String createAddressForAccountNumber(final long accountNumber) {
    return Strings.padStart(Long.toHexString(accountNumber), ADDRESS_LEN_STR, '0');
  }
}
