package net.consensys.tommygun.service;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import net.consensys.tommygun.api.fire.FireRequest;
import net.consensys.tommygun.model.task.Task;
import net.consensys.tommygun.model.task.TaskStatus;
import net.consensys.tommygun.model.task.TaskType;
import net.consensys.tommygun.repository.TaskRepository;
import net.consensys.tommygun.service.account.AccountCreatorService;
import net.consensys.tommygun.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

@Service
@Slf4j
public class FireService {
  @Autowired private Web3j web3j;
  @Autowired private TaskService taskService;
  @Autowired private TaskRepository taskRepository;
  @Autowired private AccountCreatorService accountCreator;

  public Task fire(final FireRequest fireRequest) {
    log.info("fire request initiated: {}", fireRequest.toString());
    final UUID taskID = UUID.randomUUID();
    return taskService.newTask(
        taskID, TaskType.FIRE.getType(), () -> this.startFire(taskID, fireRequest));
  }

  public void startFire(final UUID taskID, final FireRequest fireRequest) {
    final Task rootTask =
        taskRepository
            .findByID(taskID)
            .orElseThrow(
                () ->
                    new RuntimeException(
                        String.format("cannot find root task [%s]", taskID.toString())));
    final Task accountCreationTask =
        accountCreator.triggerAccountsCreation(
            taskID,
            fireRequest.getAccountNumber(),
            (subTaskID, newStatus) -> this.onSubTaskStatusChange(rootTask, subTaskID, newStatus));
  }

  public void onSubTaskStatusChange(
      final Task rootTask, final UUID subTaskID, final TaskStatus newStatus) {
    if (rootTask.getStatus().isFinalStatus()) {
      return;
    }
    final List<Task> subTasks = rootTask.getSubTasks();
    subTasks.forEach(
        task -> {
          if (task.getTaskID().equals(subTaskID)) {
            task.setStatus(newStatus);
            subTaskStatusChanged(rootTask);
          }
        });
  }

  public void subTaskStatusChanged(final Task rootTask) {
    int errorCount = 0;
    for (Task subTask : rootTask.getSubTasks()) {
      if (subTask.getStatus().equals(TaskStatus.PENDING)) {
        return;
      }
      if (subTask.getStatus().equals(TaskStatus.ERROR)) {
        errorCount++;
      }
    }
    rootTask.setStatus(errorCount == 0 ? TaskStatus.SUCCESS : TaskStatus.ERROR);
    taskRepository.save(rootTask);
  }
}
