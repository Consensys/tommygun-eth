package net.consensys.tommygun.api.state;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "State Storage API", description = "State Storage Web API documentation")
public interface StateStorageAPI {
  @Operation(summary = "Deploy key value store smart contract")
  @PostMapping(path = "/contract/deploy")
  DeployStateStorageContractResponse deploy();
}
