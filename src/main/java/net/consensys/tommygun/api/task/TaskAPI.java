package net.consensys.tommygun.api.task;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(path = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Task API", description = "Task Web API documentation")
public interface TaskAPI {
  @Operation(summary = "Find a task by id")
  @GetMapping(path = "/{taskID}")
  @ResponseBody
  TaskResponse findByID(@Parameter(description = "task id") @PathVariable String taskID);

  @Operation(summary = "Find all tasks")
  @GetMapping(path = "/")
  @ResponseBody
  List<TaskResponse> findAll();

  @Operation(summary = "Find tasks by status")
  @GetMapping(path = "/status/{taskStatus}")
  @ResponseBody
  List<TaskResponse> findByStatus(
      @Parameter(description = "task status") @PathVariable String taskStatus);
}
