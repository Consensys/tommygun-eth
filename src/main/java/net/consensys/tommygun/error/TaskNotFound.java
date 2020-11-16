package net.consensys.tommygun.error;

import java.util.UUID;

public class TaskNotFound extends RuntimeException {
  private static final String MESSAGE_TEMPLATE = "task [%s] was not found";

  public TaskNotFound(final UUID taskID) {
    super(String.format(MESSAGE_TEMPLATE, taskID.toString()));
  }

  public TaskNotFound(final String taskID) {
    super(String.format(MESSAGE_TEMPLATE, taskID));
  }
}
