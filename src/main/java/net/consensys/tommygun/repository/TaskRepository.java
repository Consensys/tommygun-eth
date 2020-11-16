package net.consensys.tommygun.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.consensys.tommygun.model.task.Task;
import net.consensys.tommygun.model.task.TaskStatus;

public interface TaskRepository {

  void save(Task task);

  Optional<Task> findByID(UUID taskID);

  List<Task> findByStatus(TaskStatus status);

  List<Task> findAll();
}
