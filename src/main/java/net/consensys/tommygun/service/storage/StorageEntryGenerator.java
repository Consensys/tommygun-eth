package net.consensys.tommygun.service.storage;

import static net.consensys.tommygun.util.EthereumConfiguration.ADDRESS_LEN_STR;

import java.math.BigInteger;

import com.google.common.base.Strings;
import net.consensys.tommygun.model.state.StorageEntry;
import org.springframework.stereotype.Service;

@Service
public class StorageEntryGenerator {

  public StorageEntry newStorageEntry(final long entryKey) {
    return new StorageEntry(BigInteger.valueOf(entryKey), newStorageEntryValue(entryKey));
  }

  public String newStorageEntryValue(final long key) {
    return padToAddress(key);
  }

  public String padToAddress(final long value) {
    return Strings.padStart(Long.toHexString(value), ADDRESS_LEN_STR, '0');
  }
}
