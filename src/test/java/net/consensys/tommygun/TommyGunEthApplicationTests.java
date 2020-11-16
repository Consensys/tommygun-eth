package net.consensys.tommygun;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    args =
        "--account-creator-private-key=0x8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63")
class TommyGunEthApplicationTests {

  @Test
  void contextLoads() {}

  @Test
  void applicationArgumentsPopulated(@Autowired ApplicationArguments args) {
    assertThat(args.getOptionNames()).containsOnly("account-creator-private-key");
    assertThat(args.getOptionValues("account-creator-private-key"))
        .containsOnly("0x8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63");
  }
}
