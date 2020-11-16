package net.consensys.tommygun;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(args = "--account-creator-private-key=fakePrivateKey")
class TommyGunEthApplicationTests {

  @Test
  void contextLoads() {}

  @Test
  void applicationArgumentsPopulated(@Autowired ApplicationArguments args) {
    assertThat(args.getOptionNames()).containsOnly("account-creator-private-key");
    assertThat(args.getOptionValues("account-creator-private-key")).containsOnly("fakePrivateKey");
  }
}
