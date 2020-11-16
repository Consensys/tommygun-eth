package net.consensys.tommygun.model.task;

import java.util.UUID;

@FunctionalInterface
public interface StatusChangeListener {

  void onStatusChange(UUID taskID, TaskStatus newStatus);
}
