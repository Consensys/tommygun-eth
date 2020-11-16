package net.consensys.tommygun.api.fire;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/fire", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Fire API", description = "Fire Web API documentation")
public interface FireAPI {
  @Operation(summary = "Start generating the large state testnet")
  @PostMapping
  FireResponse fire(@Valid @RequestBody final FireRequest fireRequest);
}
