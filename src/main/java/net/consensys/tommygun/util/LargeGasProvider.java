package net.consensys.tommygun.util;

import java.math.BigInteger;

import org.web3j.tx.gas.StaticGasProvider;

public class LargeGasProvider extends StaticGasProvider {
  public static final BigInteger GAS_LIMIT = BigInteger.valueOf(1_000_000_000_000L);
  public static final BigInteger GAS_PRICE = BigInteger.valueOf(4_100_000_000L);

  public LargeGasProvider() {
    super(GAS_PRICE, GAS_LIMIT);
  }
}
