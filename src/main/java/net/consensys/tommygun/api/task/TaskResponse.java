package net.consensys.tommygun.api.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.consensys.tommygun.model.task.Task;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponse {
  private String id;
  private String status;
  private String name;
  private String errorMessage;

  public TaskResponse(final Task task) {
    this.id = task.getTaskID().toString();
    this.status = task.getStatus().name();
    this.name = task.getName();
    this.errorMessage = task.getErrorMessage();
  }
}
