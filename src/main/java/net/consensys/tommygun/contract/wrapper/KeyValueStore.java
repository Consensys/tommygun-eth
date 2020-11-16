package net.consensys.tommygun.contract.wrapper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modify!</strong>
 *
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class KeyValueStore extends Contract {
  public static final String BINARY =
      "0x608060405234801561001057600080fd5b50610425806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80632f30c6f61461004657806336009094146100aa5780639507d39a146100c8575b600080fd5b6100926004803603604081101561005c57600080fd5b8101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610120565b60405180821515815260200191505060405180910390f35b6100b2610136565b6040518082815260200191505060405180910390f35b6100f4600480360360208110156100de57600080fd5b8101908080359060200190929190505050610147565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b600061012e6000848461015b565b905092915050565b60006101426000610190565b905090565b60006101546000836101a5565b9050919050565b6000610187846000018460001b8473ffffffffffffffffffffffffffffffffffffffff1660001b6101c2565b90509392505050565b600061019e8260000161029e565b9050919050565b60006101b7836000018360001b6102af565b60001c905092915050565b600080846001016000858152602001908152602001600020549050600081141561026957846000016040518060400160405280868152602001858152509080600181540180825580915050600190039060005260206000209060020201600090919091909150600082015181600001556020820151816001015550508460000180549050856001016000868152602001908152602001600020819055506001915050610297565b8285600001600183038154811061027c57fe5b90600052602060002090600202016001018190555060009150505b9392505050565b600081600001805490509050919050565b60006102f183836040518060400160405280601e81526020017f456e756d657261626c654d61703a206e6f6e6578697374656e74206b657900008152506102f9565b905092915050565b600080846001016000858152602001908152602001600020549050600081141583906103c0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825283818151815260200191508051906020019080838360005b8381101561038557808201518184015260208101905061036a565b50505050905090810190601f1680156103b25780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b508460000160018203815481106103d357fe5b906000526020600020906002020160010154915050939250505056fea2646970667358221220417788f400a81478dc1b01d6c38d1fbeb54e64d290eec7bae5d232bec8f1646964736f6c634300060c0033\n";

  public static final String FUNC_GET = "get";

  public static final String FUNC_SET = "set";

  public static final String FUNC_STORESIZE = "storeSize";

  @Deprecated
  protected KeyValueStore(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected KeyValueStore(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected KeyValueStore(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected KeyValueStore(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteFunctionCall<String> get(BigInteger key) {
    final Function function =
        new Function(
            FUNC_GET,
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(key)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<TransactionReceipt> set(BigInteger key, String value) {
    final Function function =
        new Function(
            FUNC_SET,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.generated.Uint256(key),
                new org.web3j.abi.datatypes.Address(160, value)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<BigInteger> storeSize() {
    final Function function =
        new Function(
            FUNC_STORESIZE,
            Arrays.<Type>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  @Deprecated
  public static KeyValueStore load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new KeyValueStore(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static KeyValueStore load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new KeyValueStore(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static KeyValueStore load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new KeyValueStore(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static KeyValueStore load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new KeyValueStore(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<KeyValueStore> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        KeyValueStore.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  public static RemoteCall<KeyValueStore> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        KeyValueStore.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<KeyValueStore> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(
        KeyValueStore.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<KeyValueStore> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(
        KeyValueStore.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }
}
