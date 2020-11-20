package net.consensys.tommygun.api.account;

import java.io.IOException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(path = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Account API", description = "Account Web API documentation")
public interface AccountAPI {
  @Operation(summary = "Deploy key value store smart contract")
  @PostMapping(path = "/contract/deploy")
  @ResponseBody
  DeployAccountCreatorContractResponse deploy() throws IOException;

  @Operation(summary = "Get account creator contract info")
  @GetMapping(path = "/contract/info")
  @ResponseBody
  AccountCreatorInfoResponse getContractInfo() throws Exception;
}
