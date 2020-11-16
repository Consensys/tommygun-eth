package net.consensys.tommygun.controller.task;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import net.consensys.tommygun.api.task.TaskAPI;
import net.consensys.tommygun.api.task.TaskResponse;
import net.consensys.tommygun.error.TaskNotFound;
import net.consensys.tommygun.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskController implements TaskAPI {
  @Autowired private TaskRepository repository;

  @Override
  public TaskResponse findByID(final String taskID) {
    return new TaskResponse(
        repository.findByID(UUID.fromString(taskID)).orElseThrow(() -> new TaskNotFound(taskID)));
  }

  @Override
  public List<TaskResponse> findAll() {
    return repository.findAll().stream().map(TaskResponse::new).collect(Collectors.toList());
  }
}
