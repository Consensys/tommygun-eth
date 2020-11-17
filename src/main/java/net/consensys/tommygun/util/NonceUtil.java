package net.consensys.tommygun.util;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import lombok.experimental.UtilityClass;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;

@UtilityClass
public class NonceUtil {

  public static AtomicLong getNonce(final Web3j web3j, final String address) throws IOException {
    return new AtomicLong(
        web3j
            .ethGetTransactionCount(address, DefaultBlockParameterName.LATEST)
            .send()
            .getTransactionCount()
            .longValue());
  }
}
