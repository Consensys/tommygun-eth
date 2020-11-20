package net.consensys.tommygun.api.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.consensys.tommygun.model.account.AccountCreatorContractInfo;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class AccountCreatorInfoResponse {
  private long accountsCreated;
  private String lastCreatedAddress;

  public AccountCreatorInfoResponse(final AccountCreatorContractInfo accountCreatorContractInfo) {
    this.accountsCreated = accountCreatorContractInfo.getAccountsCreated();
    this.lastCreatedAddress = accountCreatorContractInfo.getLastCreatedAddress();
  }
}
