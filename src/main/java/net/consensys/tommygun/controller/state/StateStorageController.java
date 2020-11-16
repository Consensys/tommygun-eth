package net.consensys.tommygun.controller.state;

import net.consensys.tommygun.api.state.DeployStateStorageContractResponse;
import net.consensys.tommygun.api.state.GetStateStorageContractResponse;
import net.consensys.tommygun.api.state.StateStorageAPI;
import net.consensys.tommygun.boot.StateStorageServiceConfiguration;
import net.consensys.tommygun.service.storage.StateStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateStorageController implements StateStorageAPI {
  @Autowired StateStorageService stateStorageService;
  @Autowired StateStorageServiceConfiguration stateStorageServiceConfiguration;

  @Override
  public DeployStateStorageContractResponse deploy() {
    return new DeployStateStorageContractResponse(stateStorageService.deploySmartContract());
  }

  @Override
  public GetStateStorageContractResponse getContractAddress() {
    return new GetStateStorageContractResponse(
        stateStorageServiceConfiguration.getKeyValueStoreContractAddress());
  }
}
