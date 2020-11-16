package net.consensys.tommygun.model.task;

public enum TaskStatus {
  PENDING(false),
  SUCCESS(true),
  ERROR(true);

  private final boolean finalStatus;

  TaskStatus(boolean finalStatus) {
    this.finalStatus = finalStatus;
  }

  public boolean isFinalStatus() {
    return finalStatus;
  }
}
