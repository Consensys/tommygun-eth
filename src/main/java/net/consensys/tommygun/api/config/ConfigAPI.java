package net.consensys.tommygun.api.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(path = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Config API", description = "Config Web API documentation")
public interface ConfigAPI {

  @Operation(summary = "Get the current configuration")
  @GetMapping
  @ResponseBody
  ConfigResponse getConfig();

  @Operation(summary = "Update key value store contract address")
  @PutMapping(path = "/update/contract/state/{newAddress}")
  @ResponseBody
  ConfigResponse updateKeyValueStoreContractAddress(
      @Parameter(description = "new address") @PathVariable String newAddress);

  @Operation(summary = "Update account contract address")
  @PutMapping(path = "/update/contract/account/{newAddress}")
  @ResponseBody
  ConfigResponse updateAccountCreatorContractAddress(
      @Parameter(description = "new address") @PathVariable String newAddress);
}
