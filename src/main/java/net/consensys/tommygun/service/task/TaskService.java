package net.consensys.tommygun.service.task;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;
import net.consensys.tommygun.model.task.Task;
import net.consensys.tommygun.model.task.TaskStatus;
import net.consensys.tommygun.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskService {
  @Autowired private TaskRepository repository;
  private final ExecutorService executorService = Executors.newCachedThreadPool();

  public Task newTask(final UUID taskID, final String name, final Runnable taskProcess) {
    return newTask(taskID, name, taskProcess, Optional.empty());
  }

  public Task newTask(
      final UUID taskID,
      final String name,
      final Runnable taskProcess,
      final Optional<UUID> parentTaskID) {
    final Task task =
        Task.builder()
            .taskID(taskID)
            .name(name)
            .status(TaskStatus.PENDING)
            .parentTaskID(parentTaskID)
            .taskProcess(taskProcess)
            .build();
    repository.save(task);
    executorService.submit(() -> this.process(task));
    return task;
  }

  public void process(final Task task) {
    try {
      log.info("starting task [{}]", task.getTaskID().toString());
      task.getTaskProcess().run();
      if (task.getSubTasks().isEmpty()) {
        log.info("task [{}] completed", task.getTaskID().toString());
        task.updateStatus(TaskStatus.SUCCESS);
      }
    } catch (final RuntimeException e) {
      log.error("task [{}] caused error: {}", task.getTaskID().toString(), e.getMessage());
      task.error(e.getMessage());
    } finally {
      repository.save(task);
    }
  }
}
