package net.consensys.tommygun;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    args = {
      "--account-creator-private-key=0x8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63",
      "--state-storage-creator-private-key=0xae6ae8e5ccbfb04590405997ee2d52d2b330726137b875053c36d94e974d162f"
    })
class TommyGunEthApplicationTests {

  @Test
  void contextLoads() {}

  @Test
  void applicationArgumentsPopulated(@Autowired ApplicationArguments args) {
    assertThat(args.getOptionNames())
        .contains("account-creator-private-key", "state-storage-creator-private-key");
    assertThat(args.getOptionValues("account-creator-private-key"))
        .containsOnly("0x8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63");
    assertThat(args.getOptionValues("state-storage-creator-private-key"))
        .containsOnly("0xae6ae8e5ccbfb04590405997ee2d52d2b330726137b875053c36d94e974d162f");
  }
}
