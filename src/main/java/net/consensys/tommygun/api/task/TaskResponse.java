package net.consensys.tommygun.api.task;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
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

  @JsonInclude(NON_NULL)
  private String parentTaskID;

  @JsonInclude(NON_NULL)
  private String errorMessage;

  @JsonInclude(NON_NULL)
  private Instant startedAt;

  @JsonInclude(NON_NULL)
  private Instant endedAt;

  @JsonInclude(NON_NULL)
  private Long durationMillis;

  public TaskResponse(final Task task) {
    this.id = task.getTaskID().toString();
    this.status = task.getStatus().name();
    this.name = task.getName();
    this.errorMessage = task.getErrorMessage();
    this.parentTaskID = task.getParentTaskID().map(UUID::toString).orElse(null);
    this.startedAt = task.getStartedAt();
    this.endedAt = task.getEndedAt();
    this.durationMillis = task.getDurationMillis();
  }
}
