package net.consensys.tommygun.api.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(path = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Config API", description = "Config Web API documentation")
public interface ConfigAPI {

  @Operation(summary = "Get the current configuration")
  @GetMapping
  @ResponseBody
  ConfigResponse getConfig();
}
