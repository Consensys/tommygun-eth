package net.consensys.tommygun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class TommyGunEthApplication {

  public static void main(final String[] args) {
    SpringApplication.run(TommyGunEthApplication.class, args);
  }
}
