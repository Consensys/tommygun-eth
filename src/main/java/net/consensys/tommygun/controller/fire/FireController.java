package net.consensys.tommygun.controller.fire;

import net.consensys.tommygun.api.fire.FireAPI;
import net.consensys.tommygun.api.fire.FireRequest;
import net.consensys.tommygun.api.fire.FireResponse;
import net.consensys.tommygun.model.task.Task;
import net.consensys.tommygun.service.FireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireController implements FireAPI {

  @Autowired private FireService fireService;

  @Override
  public FireResponse fire(final FireRequest fireRequest) {
    final Task fireTask = fireService.fire(fireRequest);
    return new FireResponse(fireTask.getTaskID().toString());
  }
}
