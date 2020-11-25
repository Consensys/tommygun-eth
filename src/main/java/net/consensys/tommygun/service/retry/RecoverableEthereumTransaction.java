package net.consensys.tommygun.service.retry;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.web3j.protocol.exceptions.TransactionException;

import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

@UtilityClass
@Slf4j
public class RecoverableEthereumTransaction {

  public static void run(final Callable<Void> attempt, final int maxRetries) {
    int attempts = 0;
    boolean done = false;
    while (attempts < maxRetries && !done) {
      try {
        attempt.call();
        done = true;
      } catch (final TransactionException | SocketTimeoutException e) {
        log.warn("recoverable exception caught", e);
        attempts++;
      } catch (Exception e) {
        log.error("non recoverable exception caught, aborting process", e);
        throw new RuntimeException(e);
      }
    }
  }

  public static void runIgnoreTransactionException(final Callable<Void> attempt) {
    try {
      attempt.call();
    } catch (final TransactionException | SocketTimeoutException e) {
      log.warn("recoverable exception caught", e);
    } catch (Exception e) {
      log.error("non recoverable exception caught, aborting process", e);
      throw new RuntimeException(e);
    }
  }
}
