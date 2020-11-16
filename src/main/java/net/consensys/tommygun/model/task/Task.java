package net.consensys.tommygun.model.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class Task {
  private UUID taskID;
  @Builder.Default private Optional<UUID> parentTaskID = Optional.empty();
  @Builder.Default private List<Task> subTasks = new ArrayList<>();
  private TaskStatus status;
  private String name;
  private String errorMessage;
  private Runnable taskProcess;
  @Builder.Default private List<StatusChangeListener> statusChangeListeners = new ArrayList<>();

  public void error(final String errorMessage) {
    this.setStatus(TaskStatus.ERROR);
    this.setErrorMessage(errorMessage);
  }

  public void updateStatus(final TaskStatus status) {
    this.setStatus(status);
    this.statusChangeListeners.forEach(listener -> listener.onStatusChange(taskID, status));
  }

  public void addStatusChangeListener(final StatusChangeListener listener) {
    this.statusChangeListeners.add(listener);
  }
}
