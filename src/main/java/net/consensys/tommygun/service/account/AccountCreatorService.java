package net.consensys.tommygun.service.account;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.consensys.tommygun.model.task.StatusChangeListener;
import net.consensys.tommygun.model.task.Task;
import net.consensys.tommygun.model.task.TaskType;
import net.consensys.tommygun.service.task.TaskService;
import org.web3j.protocol.Web3j;

@AllArgsConstructor
@Slf4j
public class AccountCreatorService {
  private final Web3j web3j;
  private final String accountCreatorPrivateKey;
  private final TaskService taskService;

  public Task triggerAccountsCreation(
      final UUID parentTaskID,
      final long accountNumber,
      final StatusChangeListener statusChangeListener) {
    final Task task =
        taskService.newTask(
            UUID.randomUUID(),
            String.format(TaskType.CREATE_ACCOUNT.getType(), accountNumber),
            () -> this.create(accountNumber),
            Optional.of(parentTaskID));
    task.addStatusChangeListener(statusChangeListener);
    return task;
  }

  public void create(final long accountNumber) {
    log.info("starting creation of {} accounts.", accountNumber);
    try {
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}
