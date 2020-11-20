package net.consensys.tommygun.contract.wrapper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

@SuppressWarnings("rawtypes")
public class AccountCreator extends Contract {
  public static final String BINARY =
      "0x60806040526103e8600055600180556102258061001d6000396000f3fe6080604052600436106100345760003560e01c80636eb3a26614610039578063780900dc14610064578063bb9bf17d146100a8575b600080fd5b34801561004557600080fd5b5061004e6100e9565b6040518082815260200191505060405180910390f35b6100906004803603602081101561007a57600080fd5b81019080803590602001909291905050506100f3565b60405180821515815260200191505060405180910390f35b3480156100b457600080fd5b506100bd6101c5565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000600254905090565b600080600090505b828110156101bb5760006001600254600054010190508073ffffffffffffffffffffffffffffffffffffffff166108fc6001549081150290604051600060405180830381858888f19350505050158015610159573d6000803e3d6000fd5b5060026000815480929190600101919050555080600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505080806001019150506100fb565b5060019050919050565b6000600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690509056fea2646970667358221220462b94e35357ee5de7b717cd69202db5f8d9d67b0649af5e9ad581ad432f399c64736f6c634300060c0033";

  public static final String FUNC_CREATE = "create";

  public static final String FUNC_GETACCOUNTSCREATED = "getAccountsCreated";

  public static final String FUNC_GETLASTCREATEDADDRESS = "getLastCreatedAddress";

  protected static final HashMap<String, String> _addresses;

  static {
    _addresses = new HashMap<>();
  }

  @Deprecated
  protected AccountCreator(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected AccountCreator(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected AccountCreator(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected AccountCreator(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteFunctionCall<TransactionReceipt> create(BigInteger accountNumber) {
    final Function function =
        new Function(
            FUNC_CREATE, Arrays.asList(new Uint256(accountNumber)), Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<BigInteger> getAccountsCreated() {
    final Function function =
        new Function(
            FUNC_GETACCOUNTSCREATED,
            Arrays.asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<String> getLastCreatedAddress() {
    final Function function =
        new Function(
            FUNC_GETLASTCREATEDADDRESS,
            Arrays.asList(),
            Arrays.asList(new TypeReference<Address>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  @Deprecated
  public static AccountCreator load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new AccountCreator(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static AccountCreator load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new AccountCreator(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static AccountCreator load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new AccountCreator(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static AccountCreator load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new AccountCreator(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<AccountCreator> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        AccountCreator.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  public static RemoteCall<AccountCreator> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        AccountCreator.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<AccountCreator> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(
        AccountCreator.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<AccountCreator> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(
        AccountCreator.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }

  protected String getStaticDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }

  public static String getPreviouslyDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }
}
