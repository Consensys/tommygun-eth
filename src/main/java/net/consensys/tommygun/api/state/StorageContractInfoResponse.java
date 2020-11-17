package net.consensys.tommygun.api.state;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.consensys.tommygun.model.state.StorageContractInfo;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class StorageContractInfoResponse {
  private long stateEntriesNumber;

  public StorageContractInfoResponse(final StorageContractInfo storageContractInfo) {
    this.stateEntriesNumber = storageContractInfo.getStateEntriesNumber();
  }
}
