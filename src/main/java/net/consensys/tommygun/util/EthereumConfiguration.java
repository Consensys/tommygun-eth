package net.consensys.tommygun.util;

import java.math.BigInteger;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EthereumConfiguration {

  public static final BigInteger DEFAULT_GAS_LIMIT = BigInteger.valueOf(21000);
  public static final BigInteger DEFAULT_GAS_PRICE = BigInteger.valueOf(1000);
  public static final int ADDRESS_LEN_STR = 40;
}
