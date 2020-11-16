package net.consensys.tommygun.repository.inmem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import net.consensys.tommygun.model.task.Task;
import net.consensys.tommygun.model.task.TaskStatus;
import net.consensys.tommygun.repository.TaskRepository;

public class InMemTaskRepository implements TaskRepository {
  private final Map<UUID, Task> tasks;

  public InMemTaskRepository() {
    tasks = new HashMap<>();
  }

  @Override
  public void save(final Task task) {
    if (task.getTaskID() == null) {
      task.setTaskID(UUID.randomUUID());
    }
    tasks.put(task.getTaskID(), task);
  }

  @Override
  public Optional<Task> findByID(final UUID taskID) {
    if (!tasks.containsKey(taskID)) {
      return Optional.empty();
    }
    return Optional.of(tasks.get(taskID));
  }

  @Override
  public List<Task> findByStatus(final TaskStatus status) {
    return tasks.values().stream()
        .filter(task -> task.getStatus().equals(status))
        .collect(Collectors.toList());
  }

  @Override
  public List<Task> findAll() {
    return new ArrayList<>(tasks.values());
  }
}
