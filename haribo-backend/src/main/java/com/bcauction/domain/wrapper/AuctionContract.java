package com.bcauction.domain.wrapper;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class AuctionContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405160a080610a898339810160409081528151602083015191830151606084015160809094015160008054600160a060020a031916600160a060020a03909416939093178355600493909355600355600192909255600255610a0f90819061007a90396000f3006080604052600436106100ae5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166312fa6feb81146100b35780631998aeef146100dc5780634b449cba146100e45780636ba35e701461010b5780638da5cb5b146101205780638fa8b7901461015157806391f9015714610168578063963e63c71461017d578063d57bde7914610192578063eb54f9ec146101a7578063fe67a54b146101bc575b600080fd5b3480156100bf57600080fd5b506100c86101d1565b604080519115158252519081900360200190f35b6100c86101da565b3480156100f057600080fd5b506100f9610668565b60408051918252519081900360200190f35b34801561011757600080fd5b506100f961066e565b34801561012c57600080fd5b50610135610674565b60408051600160a060020a039092168252519081900360200190f35b34801561015d57600080fd5b50610166610683565b005b34801561017457600080fd5b506101356107d9565b34801561018957600080fd5b506100f96107e8565b34801561019e57600080fd5b506100f96107ee565b3480156101b357600080fd5b506100f96107f4565b3480156101c857600080fd5b506101666107fa565b60075460ff1681565b600080548190600160a060020a0316331415610240576040805160e560020a62461bcd02815260206004820152600f60248201527f73656e646572206973206f776e65720000000000000000000000000000000000604482015290519081900360640190fd5b6001544210156102c0576040805160e560020a62461bcd02815260206004820152602b60248201527fec9584eca78120ec8b9cec9e91eb9098eca78020ec958aec9d8020eab2bdeba760448201527fa4ec9e85eb8b88eb8ba42e000000000000000000000000000000000000000000606482015290519081900360840190fd5b600254421115610340576040805160e560020a62461bcd02815260206004820152602160248201527fec9db4ebafb820eca285eba38ceb909c20eab2bdeba7a4ec9e85eb8b88eb8ba460448201527f2e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60075460ff16156103c1576040805160e560020a62461bcd02815260206004820152602160248201527fec9db4ebafb820eca285eba38ceb909c20eab2bdeba7a4ec9e85eb8b88eb8ba460448201527f2e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600554600160a060020a031633141561044a576040805160e560020a62461bcd02815260206004820152603560248201527fecb59ceab3a0ec9e85ecb0b0ec9e90eb8a9420ec8381ec9c8420ec9e85ecb0b060448201527fed95a020ec889820ec9786ec8ab5eb8b88eb8ba42e0000000000000000000000606482015290519081900360840190fd5b6003543410156104ca576040805160e560020a62461bcd02815260206004820152603160248201527fec9e85ecb0b0ec95a1ec9db420ecb59ceca08020ec9e85ecb0b0ec95a1ebb3b460448201527feb8ba420eb82aeec8ab5eb8b88eb8ba42e000000000000000000000000000000606482015290519081900360840190fd5b60065434101561054a576040805160e560020a62461bcd02815260206004820152603160248201527fec9e85ecb0b0ec95a1ec9db420ecb59ceab3a020ec9e85ecb0b0ec95a1ebb3b460448201527feb8ba420eb82aeec8ab5eb8b88eb8ba42e000000000000000000000000000000606482015290519081900360840190fd5b6006541561059b5750600680546000918290556005546040519192600160a060020a039091169183156108fc0291849190818181858888f19350505050151561059b57600681905560009150610664565b60055460005460045460408051338152600160a060020a039485166020820152929093168284015234606083015260808201524360a082015290517f314755ebdafaf0af6d1f76e9ab63f8fc0c856b5af24de9444f4c8524acc3b9f69181900360c00190a16005805473ffffffffffffffffffffffffffffffffffffffff191633179055346006819055604080519182523031602083015280517f32026a51a4c733cce89a887547a6f39cef95700015b9c5c32bd147fae47c5d069281900390910190a1600191505b5090565b60025481565b60045481565b600054600160a060020a031681565b600054600160a060020a031633146106e5576040805160e560020a62461bcd02815260206004820152601360248201527f73656e646572206973206e6f74206f776e657200000000000000000000000000604482015290519081900360640190fd5b60075460ff1615610740576040805160e560020a62461bcd02815260206004820152601860248201527f61756374696f6e20697320616c726561647920656e6465640000000000000000604482015290519081900360640190fd5b6007805460ff1916600117905560045460055460408051928352600160a060020a0390911660208301524382820152517fc326dcfb5d4e924f5e3e717c0f667b0eecb5abb73fc0b33236b6107c1fd48a579181900360600190a1600554600654604051600160a060020a039092169181156108fc0291906000818181858888f193505050501580156107d6573d6000803e3d6000fd5b50565b600554600160a060020a031681565b60035481565b60065481565b60015481565b600054600160a060020a0316331461085c576040805160e560020a62461bcd02815260206004820152601360248201527f73656e646572206973206e6f74206f776e657200000000000000000000000000604482015290519081900360640190fd5b6002544210156108dc576040805160e560020a62461bcd02815260206004820152602160248201527f49742773206e6f74207965742074696d6520666f72207468652061756374696f60448201527f6e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60075460ff1615610937576040805160e560020a62461bcd02815260206004820152601860248201527f61756374696f6e20697320616c726561647920656e6465640000000000000000604482015290519081900360640190fd5b6007805460ff1916600117905560055460005460065460045460408051600160a060020a03958616815293909416602084015282840191909152606082015243608082015290517f59ed408cbaf81c21289519772ca20bf3bf46445b7c20269c6a2adb188358241f9181900360a00190a160008054600654604051600160a060020a039092169281156108fc029290818181858888f193505050501580156107d6573d6000803e3d6000fd00a165627a7a72305820e9b29a58acbd564cffd2dd5baf0324a8f1a1d3c2b2676dfa4c22b280702d4ade0029";

    public static final String FUNC_ENDED = "ended";

    public static final String FUNC_BID = "bid";

    public static final String FUNC_AUCTIONENDTIME = "auctionEndTime";

    public static final String FUNC_DIGITALWORKID = "digitalWorkId";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_CANCELAUCTION = "cancelAuction";

    public static final String FUNC_HIGHESTBIDDER = "highestBidder";

    public static final String FUNC_MINVALUE = "minValue";

    public static final String FUNC_HIGHESTBID = "highestBid";

    public static final String FUNC_AUCTIONSTARTTIME = "auctionStartTime";

    public static final String FUNC_ENDAUCTION = "endAuction";

    public static final Event HIGHESTBIDINCEREASED_EVENT = new Event("HighestBidIncereased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONENDED_EVENT = new Event("AuctionEnded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONCANCELED_EVENT = new Event("AuctionCanceled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event GETBALANCE_EVENT = new Event("getBalance", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected AuctionContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AuctionContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AuctionContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AuctionContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Bool> ended() {
        final Function function = new Function(FUNC_ENDED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> bid(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<Uint256> auctionEndTime() {
        final Function function = new Function(FUNC_AUCTIONENDTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> digitalWorkId() {
        final Function function = new Function(FUNC_DIGITALWORKID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> cancelAuction() {
        final Function function = new Function(
                FUNC_CANCELAUCTION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Address> highestBidder() {
        final Function function = new Function(FUNC_HIGHESTBIDDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> minValue() {
        final Function function = new Function(FUNC_MINVALUE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> highestBid() {
        final Function function = new Function(FUNC_HIGHESTBID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> auctionStartTime() {
        final Function function = new Function(FUNC_AUCTIONSTARTTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> endAuction() {
        final Function function = new Function(
                FUNC_ENDAUCTION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<HighestBidIncereasedEventResponse> getHighestBidIncereasedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(HIGHESTBIDINCEREASED_EVENT, transactionReceipt);
        ArrayList<HighestBidIncereasedEventResponse> responses = new ArrayList<HighestBidIncereasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HighestBidIncereasedEventResponse typedResponse = new HighestBidIncereasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.bidder = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.beforeBidder = (Address) eventValues.getNonIndexedValues().get(1);
            typedResponse.owner = (Address) eventValues.getNonIndexedValues().get(2);
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(3);
            typedResponse.digitalWorkId = (Uint256) eventValues.getNonIndexedValues().get(4);
            typedResponse.blockNumber = (Uint256) eventValues.getNonIndexedValues().get(5);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<HighestBidIncereasedEventResponse> highestBidIncereasedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, HighestBidIncereasedEventResponse>() {
            @Override
            public HighestBidIncereasedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(HIGHESTBIDINCEREASED_EVENT, log);
                HighestBidIncereasedEventResponse typedResponse = new HighestBidIncereasedEventResponse();
                typedResponse.log = log;
                typedResponse.bidder = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.beforeBidder = (Address) eventValues.getNonIndexedValues().get(1);
                typedResponse.owner = (Address) eventValues.getNonIndexedValues().get(2);
                typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(3);
                typedResponse.digitalWorkId = (Uint256) eventValues.getNonIndexedValues().get(4);
                typedResponse.blockNumber = (Uint256) eventValues.getNonIndexedValues().get(5);
                return typedResponse;
            }
        });
    }

    public Flowable<HighestBidIncereasedEventResponse> highestBidIncereasedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HIGHESTBIDINCEREASED_EVENT));
        return highestBidIncereasedEventFlowable(filter);
    }

    public List<AuctionEndedEventResponse> getAuctionEndedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONENDED_EVENT, transactionReceipt);
        ArrayList<AuctionEndedEventResponse> responses = new ArrayList<AuctionEndedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.winner = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.owner = (Address) eventValues.getNonIndexedValues().get(1);
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(2);
            typedResponse.digitalWorkId = (Uint256) eventValues.getNonIndexedValues().get(3);
            typedResponse.blockNumber = (Uint256) eventValues.getNonIndexedValues().get(4);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AuctionEndedEventResponse>() {
            @Override
            public AuctionEndedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(AUCTIONENDED_EVENT, log);
                AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
                typedResponse.log = log;
                typedResponse.winner = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.owner = (Address) eventValues.getNonIndexedValues().get(1);
                typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(2);
                typedResponse.digitalWorkId = (Uint256) eventValues.getNonIndexedValues().get(3);
                typedResponse.blockNumber = (Uint256) eventValues.getNonIndexedValues().get(4);
                return typedResponse;
            }
        });
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONENDED_EVENT));
        return auctionEndedEventFlowable(filter);
    }

    public List<AuctionCanceledEventResponse> getAuctionCanceledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONCANCELED_EVENT, transactionReceipt);
        ArrayList<AuctionCanceledEventResponse> responses = new ArrayList<AuctionCanceledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionCanceledEventResponse typedResponse = new AuctionCanceledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.digitalWorkId = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.highestBidder = (Address) eventValues.getNonIndexedValues().get(1);
            typedResponse.blockNumber = (Uint256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AuctionCanceledEventResponse> auctionCanceledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AuctionCanceledEventResponse>() {
            @Override
            public AuctionCanceledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(AUCTIONCANCELED_EVENT, log);
                AuctionCanceledEventResponse typedResponse = new AuctionCanceledEventResponse();
                typedResponse.log = log;
                typedResponse.digitalWorkId = (Uint256) eventValues.getNonIndexedValues().get(0);
                typedResponse.highestBidder = (Address) eventValues.getNonIndexedValues().get(1);
                typedResponse.blockNumber = (Uint256) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public Flowable<AuctionCanceledEventResponse> auctionCanceledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONCANCELED_EVENT));
        return auctionCanceledEventFlowable(filter);
    }

    public List<GetBalanceEventResponse> getGetBalanceEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(GETBALANCE_EVENT, transactionReceipt);
        ArrayList<GetBalanceEventResponse> responses = new ArrayList<GetBalanceEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            GetBalanceEventResponse typedResponse = new GetBalanceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.highestBid = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.thisBalance = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<GetBalanceEventResponse> getBalanceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, GetBalanceEventResponse>() {
            @Override
            public GetBalanceEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(GETBALANCE_EVENT, log);
                GetBalanceEventResponse typedResponse = new GetBalanceEventResponse();
                typedResponse.log = log;
                typedResponse.highestBid = (Uint256) eventValues.getNonIndexedValues().get(0);
                typedResponse.thisBalance = (Uint256) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Flowable<GetBalanceEventResponse> getBalanceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GETBALANCE_EVENT));
        return getBalanceEventFlowable(filter);
    }

    @Deprecated
    public static AuctionContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AuctionContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AuctionContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AuctionContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AuctionContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AuctionContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AuctionContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, Address _owner, Uint256 workId, Uint256 minimum, Uint256 startTime, Uint256 endTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_owner, workId, minimum, startTime, endTime));
        return deployRemoteCall(AuctionContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<AuctionContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Address _owner, Uint256 workId, Uint256 minimum, Uint256 startTime, Uint256 endTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_owner, workId, minimum, startTime, endTime));
        return deployRemoteCall(AuctionContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AuctionContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Address _owner, Uint256 workId, Uint256 minimum, Uint256 startTime, Uint256 endTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_owner, workId, minimum, startTime, endTime));
        return deployRemoteCall(AuctionContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AuctionContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Address _owner, Uint256 workId, Uint256 minimum, Uint256 startTime, Uint256 endTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_owner, workId, minimum, startTime, endTime));
        return deployRemoteCall(AuctionContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class HighestBidIncereasedEventResponse {
        public Log log;

        public Address bidder;

        public Address beforeBidder;

        public Address owner;

        public Uint256 amount;

        public Uint256 digitalWorkId;

        public Uint256 blockNumber;
    }

    public static class AuctionEndedEventResponse {
        public Log log;

        public Address winner;

        public Address owner;

        public Uint256 amount;

        public Uint256 digitalWorkId;

        public Uint256 blockNumber;
    }

    public static class AuctionCanceledEventResponse {
        public Log log;

        public Uint256 digitalWorkId;

        public Address highestBidder;

        public Uint256 blockNumber;
    }

    public static class GetBalanceEventResponse {
        public Log log;

        public Uint256 highestBid;

        public Uint256 thisBalance;
    }
}
