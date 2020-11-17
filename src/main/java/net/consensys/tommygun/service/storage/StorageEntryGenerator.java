package net.consensys.tommygun.service.storage;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import net.consensys.tommygun.model.state.StorageEntry;
import org.springframework.stereotype.Service;

@Service
public class StorageEntryGenerator {
  private static final String DEFAULT_STORE_ENTRY_VALUE =
      "0000000000000000000000000000000000000000";

  public StorageEntry newStorageEntry(final long entryKey) {
    return new StorageEntry(BigInteger.valueOf(entryKey), newStorageEntryValue());
  }

  public StorageEntry newStorageEntry() {
    return new StorageEntry(newStorageEntryKey(), newStorageEntryValue());
  }

  public BigInteger newStorageEntryKey() {
    return new BigInteger(256, ThreadLocalRandom.current());
  }

  public String newStorageEntryValue() {
    return DEFAULT_STORE_ENTRY_VALUE;
  }
}
