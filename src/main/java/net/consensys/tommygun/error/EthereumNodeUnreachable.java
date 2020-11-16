package net.consensys.tommygun.error;

public class EthereumNodeUnreachable extends RuntimeException {

  public EthereumNodeUnreachable(final Exception e) {
    super(e);
  }
}
