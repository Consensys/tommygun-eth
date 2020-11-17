package net.consensys.tommygun.model.state;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class StorageEntry {
  private BigInteger key;
  private String value;
}
