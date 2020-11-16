package net.consensys.tommygun.model.task;

public enum TaskType {
  FIRE("fire"),
  CREATE_ACCOUNT("create-account-%s");

  private final String type;

  TaskType(final String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
