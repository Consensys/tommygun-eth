package net.consensys.tommygun.api.state;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(path = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "State Storage API", description = "State Storage Web API documentation")
public interface StateStorageAPI {
  @Operation(summary = "Deploy key value store smart contract")
  @PostMapping(path = "/contract/deploy")
  @ResponseBody
  DeployStateStorageContractResponse deploy();

  @Operation(summary = "Get key value store smart contract info")
  @GetMapping(path = "/contract/info")
  @ResponseBody
  StorageContractInfoResponse getStorageContractInfo() throws Exception;

  @Operation(summary = "Get key value store smart contract info")
  @GetMapping(path = "/contract/info/{address}")
  @ResponseBody
  StorageContractInfoResponse getStorageContractInfo(
      @Parameter(description = "contract address") @PathVariable String address) throws Exception;
}
