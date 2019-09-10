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
    private static final String BINARY = "608060405234801561001057600080fd5b5060405160a08061096e833981016040908152815160208301519183015160608401516080909401516000805433600160a060020a031991821617825560018054909116600160a060020a03909516949094179093556005939093556004556002929092556003556108e690819061008890396000f3006080604052600436106100b95763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166312fa6feb81146100be5780631998aeef146100e75780634b449cba146100ef5780636ba35e70146101165780638da5cb5b1461012b5780638fa8b7901461015c57806391f9015714610173578063963e63c714610188578063d57bde791461019d578063eb54f9ec146101b2578063f2fde38b146101c7578063fe67a54b146101e8575b600080fd5b3480156100ca57600080fd5b506100d36101fd565b604080519115158252519081900360200190f35b6100d3610206565b3480156100fb57600080fd5b506101046105a6565b60408051918252519081900360200190f35b34801561012257600080fd5b506101046105ac565b34801561013757600080fd5b506101406105b2565b60408051600160a060020a039092168252519081900360200190f35b34801561016857600080fd5b506101716105c1565b005b34801561017f57600080fd5b5061014061067f565b34801561019457600080fd5b5061010461068e565b3480156101a957600080fd5b50610104610694565b3480156101be57600080fd5b5061010461069a565b3480156101d357600080fd5b50610171600160a060020a03600435166106a0565b3480156101f457600080fd5b50610171610734565b60085460ff1681565b6001546000908190600160a060020a031633141561022357600080fd5b6002544210156102a3576040805160e560020a62461bcd02815260206004820152602b60248201527fec9584eca78120ec8b9cec9e91eb9098eca78020ec958aec9d8020eab2bdeba760448201527fa4ec9e85eb8b88eb8ba42e000000000000000000000000000000000000000000606482015290519081900360840190fd5b600354421115610323576040805160e560020a62461bcd02815260206004820152602160248201527fec9db4ebafb820eca285eba38ceb909c20eab2bdeba7a4ec9e85eb8b88eb8ba460448201527f2e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600654600160a060020a03163314156103ac576040805160e560020a62461bcd02815260206004820152603560248201527fecb59ceab3a0ec9e85ecb0b0ec9e90eb8a9420ec8381ec9c8420ec9e85ecb0b060448201527fed95a020ec889820ec9786ec8ab5eb8b88eb8ba42e0000000000000000000000606482015290519081900360840190fd5b60045434101561042c576040805160e560020a62461bcd02815260206004820152603160248201527fec9e85ecb0b0ec95a1ec9db420ecb59ceca08020ec9e85ecb0b0ec95a1ebb3b460448201527feb8ba420eb82aeec8ab5eb8b88eb8ba42e000000000000000000000000000000606482015290519081900360840190fd5b6007543410156104ac576040805160e560020a62461bcd02815260206004820152603160248201527fec9e85ecb0b0ec95a1ec9db420ecb59ceab3a020ec9e85ecb0b0ec95a1ebb3b460448201527feb8ba420eb82aeec8ab5eb8b88eb8ba42e000000000000000000000000000000606482015290519081900360840190fd5b600754156104fd5750600780546000918290556006546040519192600160a060020a039091169183156108fc0291849190818181858888f1935050505015156104fd576007819055600091506105a2565b6006805473ffffffffffffffffffffffffffffffffffffffff19163390811790915534600781905560408051928352602083019190915280517f9a7ee7c473e470da200bb28a0e3ee1fe516d900eaade5825f7960323b9d777409281900390910190a1600754604080519182523031602083015280517f32026a51a4c733cce89a887547a6f39cef95700015b9c5c32bd147fae47c5d069281900390910190a1600191505b5090565b60035481565b60055481565b600154600160a060020a031681565b600054600160a060020a031633146105d857600080fd5b60085460ff1615610633576040805160e560020a62461bcd02815260206004820152601860248201527f61756374696f6e20697320616c726561647920656e6465640000000000000000604482015290519081900360640190fd5b6008805460ff19166001179055600654600754604051600160a060020a039092169181156108fc0291906000818181858888f1935050505015801561067c573d6000803e3d6000fd5b50565b600654600160a060020a031681565b60045481565b60075481565b60025481565b600054600160a060020a031633146106b757600080fd5b600160a060020a03811615156106cc57600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600054600160a060020a0316331461074b57600080fd5b6003544210156107cb576040805160e560020a62461bcd02815260206004820152602160248201527f49742773206e6f74207965742074696d6520666f72207468652061756374696f60448201527f6e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60085460ff1615610826576040805160e560020a62461bcd02815260206004820152601860248201527f61756374696f6e20697320616c726561647920656e6465640000000000000000604482015290519081900360640190fd5b6008805460ff1916600117905560065460075460408051600160a060020a039093168352602083019190915280517fdaec4582d5d9595688c8c98545fdd1c696d41c6aeaeb636737e84ed2f5c00eda9281900390910190a1600154600754604051600160a060020a039092169181156108fc0291906000818181858888f1935050505015801561067c573d6000803e3d6000fd00a165627a7a72305820d32a826e24c6d86c2f2d37f19aa19eb0dd662ca9b86fd3b804d23356db06c9d60029";

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

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_ENDAUCTION = "endAuction";

    public static final Event HIGHESTBIDINCEREASED_EVENT = new Event("HighestBidIncereased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONENDED_EVENT = new Event("AuctionEnded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event GETBALANCE_EVENT = new Event("getBalance", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
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

    public RemoteCall<TransactionReceipt> transferOwnership(Address newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(newOwner), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(1);
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
                typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(1);
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
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(1);
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
                typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONENDED_EVENT));
        return auctionEndedEventFlowable(filter);
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

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.newOwner = (Address) eventValues.getIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.newOwner = (Address) eventValues.getIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
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

        public Uint256 amount;
    }

    public static class AuctionEndedEventResponse {
        public Log log;

        public Address winner;

        public Uint256 amount;
    }

    public static class GetBalanceEventResponse {
        public Log log;

        public Uint256 highestBid;

        public Uint256 thisBalance;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public Address previousOwner;

        public Address newOwner;
    }
}
