package net.consensys.tommygun.controller.account;

import java.io.IOException;

import net.consensys.tommygun.api.account.AccountAPI;
import net.consensys.tommygun.api.account.AccountCreatorInfoResponse;
import net.consensys.tommygun.api.account.DeployAccountCreatorContractResponse;
import net.consensys.tommygun.service.account.AccountCreatorUsingSmartContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountAPI {

  @Autowired private AccountCreatorUsingSmartContract accountCreatorUsingSmartContract;

  @Override
  public DeployAccountCreatorContractResponse deploy() throws IOException {
    return accountCreatorUsingSmartContract
        .deployAccountCreatorContract()
        .map(DeployAccountCreatorContractResponse::new)
        .orElseThrow();
  }

  @Override
  public AccountCreatorInfoResponse getContractInfo() throws Exception {
    return new AccountCreatorInfoResponse(
        accountCreatorUsingSmartContract.getAccountCreatorContractInfo());
  }
}
