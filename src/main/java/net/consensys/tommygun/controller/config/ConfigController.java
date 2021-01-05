package net.consensys.tommygun.controller.config;

import net.consensys.tommygun.api.config.ConfigAPI;
import net.consensys.tommygun.api.config.ConfigResponse;
import net.consensys.tommygun.boot.TommyGunConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController implements ConfigAPI {

  @Autowired private TommyGunConfiguration configuration;

  @Override
  public ConfigResponse getConfig() {
    return new ConfigResponse(configuration);
  }

  @Override
  public ConfigResponse updateKeyValueStoreContractAddress(final String newAddress) {
    configuration.setKeyValueStoreContractAddress(newAddress);
    return getConfig();
  }

  @Override
  public ConfigResponse updateAccountCreatorContractAddress(final String newAddress) {
    configuration.setAccountCreatorContractAddress(newAddress);
    return getConfig();
  }
}
