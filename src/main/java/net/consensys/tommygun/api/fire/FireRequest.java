package net.consensys.tommygun.api.fire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FireRequest {
  private long accountNumber;
  private long stateEntriesNumber;
  private long stateEntrySize;
}
