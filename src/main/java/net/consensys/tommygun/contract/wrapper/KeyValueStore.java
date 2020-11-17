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
      "608060405234801561001057600080fd5b506104d5806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80632f30c6f61461005157806336009094146100b55780639507d39a146100d3578063c37420211461012b575b600080fd5b61009d6004803603604081101561006757600080fd5b8101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610199565b60405180821515815260200191505060405180910390f35b6100bd6101af565b6040518082815260200191505060405180910390f35b6100ff600480360360208110156100e957600080fd5b81019080803590602001909291905050506101c0565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6101816004803603606081101561014157600080fd5b810190808035906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506101d4565b60405180821515815260200191505060405180910390f35b60006101a76000848461020b565b905092915050565b60006101bb6000610240565b905090565b60006101cd600083610255565b9050919050565b6000808490505b8385018110156101ff576101f16000828561020b565b5080806001019150506101db565b50600190509392505050565b6000610237846000018460001b8473ffffffffffffffffffffffffffffffffffffffff1660001b610272565b90509392505050565b600061024e8260000161034e565b9050919050565b6000610267836000018360001b61035f565b60001c905092915050565b600080846001016000858152602001908152602001600020549050600081141561031957846000016040518060400160405280868152602001858152509080600181540180825580915050600190039060005260206000209060020201600090919091909150600082015181600001556020820151816001015550508460000180549050856001016000868152602001908152602001600020819055506001915050610347565b8285600001600183038154811061032c57fe5b90600052602060002090600202016001018190555060009150505b9392505050565b600081600001805490509050919050565b60006103a183836040518060400160405280601e81526020017f456e756d657261626c654d61703a206e6f6e6578697374656e74206b657900008152506103a9565b905092915050565b60008084600101600085815260200190815260200160002054905060008114158390610470576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825283818151815260200191508051906020019080838360005b8381101561043557808201518184015260208101905061041a565b50505050905090810190601f1680156104625780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b5084600001600182038154811061048357fe5b906000526020600020906002020160010154915050939250505056fea2646970667358221220d04a5743da689c09a2cee72cc7e208e48b447b0444363803a05131d106081bae64736f6c634300060c0033";
  public static final String SIMPLE_STORE_BINARY =
      "608060405234801561001057600080fd5b506103b6806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80632f30c6f61461005c57806336009094146100c05780636057361d146100de5780639507d39a14610136578063c37420211461018e575b600080fd5b6100a86004803603604081101561007257600080fd5b8101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506101fc565b60405180821515815260200191505060405180910390f35b6100c86102d2565b6040518082815260200191505060405180910390f35b61010a600480360360208110156100f457600080fd5b81019080803590602001909291905050506102dc565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6101626004803603602081101561014c57600080fd5b810190808035906020019092919050505061030f565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6101e4600480360360608110156101a457600080fd5b810190808035906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061034b565b60405180821515815260200191505060405180910390f35b60008073ffffffffffffffffffffffffffffffffffffffff1660008085815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415610277576001600081548092919060010191905055505b8160008085815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001905092915050565b6000600154905090565b60006020528060005260406000206000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600080600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b6000808490505b8385018110156103745761036681846101fc565b508080600101915050610352565b5060019050939250505056fea26469706673582212203163ae59826c16636a893a42a38273e3d29c6f30d6c2c175d4c2b35b1fbe8cf364736f6c634300060c0033";
  public static final String FUNC_FILL = "fill";

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

  public RemoteFunctionCall<TransactionReceipt> fill(
      BigInteger startKey, BigInteger count, String value) {
    final Function function =
        new Function(
            FUNC_FILL,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.generated.Uint256(startKey),
                new org.web3j.abi.datatypes.generated.Uint256(count),
                new org.web3j.abi.datatypes.Address(160, value)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
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
