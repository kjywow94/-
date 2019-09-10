package com.bcauction.domain.wrapper;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
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
public class AuctionFactoryContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060008054600160a060020a03191633179055610df3806100326000396000f30060806040526004361061006c5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631bbcd4a58114610071578063431f21da146100d6578063571a26a0146101135780638da5cb5b1461012b578063f2fde38b14610140575b600080fd5b34801561007d57600080fd5b50610086610163565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156100c25781810151838201526020016100aa565b505050509050019250505060405180910390f35b3480156100e257600080fd5b506100f76004356024356044356064356101c5565b60408051600160a060020a039092168252519081900360200190f35b34801561011f57600080fd5b506100f760043561037e565b34801561013757600080fd5b506100f76103a6565b34801561014c57600080fd5b50610161600160a060020a03600435166103b5565b005b606060018054806020026020016040519081016040528092919081815260200182805480156101bb57602002820191906000526020600020905b8154600160a060020a0316815260019091019060200180831161019d575b5050505050905090565b60008033868686866101d5610449565b600160a060020a039095168552602085019390935260408085019290925260608401526080830191909152519081900360a001906000f08015801561021e573d6000803e3d6000fd5b506001805480820182556000919091527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6018054600160a060020a03831673ffffffffffffffffffffffffffffffffffffffff19909116811790915560408051918252336020830152818101899052606082018890526080820187905260a08201869052519192507f932f617c6c51dafe060274dfe3c3cb3e02ba867eede9a628e7dfe49eaa2bfa89919081900360c00190a16001805460408051600160a060020a038516815233602082018190529181018390526080606082018181529082018490527f0d314047f0d1e39dbbd521f0844c35332083a15926b906540faa68e1a4262b6894869492909160a082018385801561036457602002820191906000526020600020905b8154600160a060020a03168152600190910190602001808311610346575b50509550505050505060405180910390a195945050505050565b600180548290811061038c57fe5b600091825260209091200154600160a060020a0316905081565b600054600160a060020a031681565b600054600160a060020a031633146103cc57600080fd5b600160a060020a03811615156103e157600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b60405161096e8061045a833901905600608060405234801561001057600080fd5b5060405160a08061096e833981016040908152815160208301519183015160608401516080909401516000805433600160a060020a031991821617825560018054909116600160a060020a03909516949094179093556005939093556004556002929092556003556108e690819061008890396000f3006080604052600436106100b95763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166312fa6feb81146100be5780631998aeef146100e75780634b449cba146100ef5780636ba35e70146101165780638da5cb5b1461012b5780638fa8b7901461015c57806391f9015714610173578063963e63c714610188578063d57bde791461019d578063eb54f9ec146101b2578063f2fde38b146101c7578063fe67a54b146101e8575b600080fd5b3480156100ca57600080fd5b506100d36101fd565b604080519115158252519081900360200190f35b6100d3610206565b3480156100fb57600080fd5b506101046105a6565b60408051918252519081900360200190f35b34801561012257600080fd5b506101046105ac565b34801561013757600080fd5b506101406105b2565b60408051600160a060020a039092168252519081900360200190f35b34801561016857600080fd5b506101716105c1565b005b34801561017f57600080fd5b5061014061067f565b34801561019457600080fd5b5061010461068e565b3480156101a957600080fd5b50610104610694565b3480156101be57600080fd5b5061010461069a565b3480156101d357600080fd5b50610171600160a060020a03600435166106a0565b3480156101f457600080fd5b50610171610734565b60085460ff1681565b6001546000908190600160a060020a031633141561022357600080fd5b6002544210156102a3576040805160e560020a62461bcd02815260206004820152602b60248201527fec9584eca78120ec8b9cec9e91eb9098eca78020ec958aec9d8020eab2bdeba760448201527fa4ec9e85eb8b88eb8ba42e000000000000000000000000000000000000000000606482015290519081900360840190fd5b600354421115610323576040805160e560020a62461bcd02815260206004820152602160248201527fec9db4ebafb820eca285eba38ceb909c20eab2bdeba7a4ec9e85eb8b88eb8ba460448201527f2e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600654600160a060020a03163314156103ac576040805160e560020a62461bcd02815260206004820152603560248201527fecb59ceab3a0ec9e85ecb0b0ec9e90eb8a9420ec8381ec9c8420ec9e85ecb0b060448201527fed95a020ec889820ec9786ec8ab5eb8b88eb8ba42e0000000000000000000000606482015290519081900360840190fd5b60045434101561042c576040805160e560020a62461bcd02815260206004820152603160248201527fec9e85ecb0b0ec95a1ec9db420ecb59ceca08020ec9e85ecb0b0ec95a1ebb3b460448201527feb8ba420eb82aeec8ab5eb8b88eb8ba42e000000000000000000000000000000606482015290519081900360840190fd5b6007543410156104ac576040805160e560020a62461bcd02815260206004820152603160248201527fec9e85ecb0b0ec95a1ec9db420ecb59ceab3a020ec9e85ecb0b0ec95a1ebb3b460448201527feb8ba420eb82aeec8ab5eb8b88eb8ba42e000000000000000000000000000000606482015290519081900360840190fd5b600754156104fd5750600780546000918290556006546040519192600160a060020a039091169183156108fc0291849190818181858888f1935050505015156104fd576007819055600091506105a2565b6006805473ffffffffffffffffffffffffffffffffffffffff19163390811790915534600781905560408051928352602083019190915280517f9a7ee7c473e470da200bb28a0e3ee1fe516d900eaade5825f7960323b9d777409281900390910190a1600754604080519182523031602083015280517f32026a51a4c733cce89a887547a6f39cef95700015b9c5c32bd147fae47c5d069281900390910190a1600191505b5090565b60035481565b60055481565b600154600160a060020a031681565b600054600160a060020a031633146105d857600080fd5b60085460ff1615610633576040805160e560020a62461bcd02815260206004820152601860248201527f61756374696f6e20697320616c726561647920656e6465640000000000000000604482015290519081900360640190fd5b6008805460ff19166001179055600654600754604051600160a060020a039092169181156108fc0291906000818181858888f1935050505015801561067c573d6000803e3d6000fd5b50565b600654600160a060020a031681565b60045481565b60075481565b60025481565b600054600160a060020a031633146106b757600080fd5b600160a060020a03811615156106cc57600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600054600160a060020a0316331461074b57600080fd5b6003544210156107cb576040805160e560020a62461bcd02815260206004820152602160248201527f49742773206e6f74207965742074696d6520666f72207468652061756374696f60448201527f6e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60085460ff1615610826576040805160e560020a62461bcd02815260206004820152601860248201527f61756374696f6e20697320616c726561647920656e6465640000000000000000604482015290519081900360640190fd5b6008805460ff1916600117905560065460075460408051600160a060020a039093168352602083019190915280517fdaec4582d5d9595688c8c98545fdd1c696d41c6aeaeb636737e84ed2f5c00eda9281900390910190a1600154600754604051600160a060020a039092169181156108fc0291906000818181858888f1935050505015801561067c573d6000803e3d6000fd00a165627a7a72305820d32a826e24c6d86c2f2d37f19aa19eb0dd662ca9b86fd3b804d23356db06c9d60029a165627a7a72305820ee7126a7d2692fc193db915cbb7735913e8395be289d1e9a5cb2c8effaf9c2c50029";

    public static final String FUNC_ALLAUCTIONS = "allAuctions";

    public static final String FUNC_CREATEAUCTION = "createAuction";

    public static final String FUNC_AUCTIONS = "auctions";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event NEWAUCTION_EVENT = new Event("NewAuction", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONCREATED_EVENT = new Event("AuctionCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected AuctionFactoryContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AuctionFactoryContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AuctionFactoryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AuctionFactoryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<DynamicArray<Address>> allAuctions() {
        final Function function = new Function(FUNC_ALLAUCTIONS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> createAuction(Uint256 workId, Uint256 minValue, Uint256 startTime, Uint256 endTime) {
        final Function function = new Function(
                FUNC_CREATEAUCTION, 
                Arrays.<Type>asList(workId, minValue, startTime, endTime), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Address> auctions(Uint256 param0) {
        final Function function = new Function(FUNC_AUCTIONS, 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(Address newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(newOwner), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<NewAuctionEventResponse> getNewAuctionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWAUCTION_EVENT, transactionReceipt);
        ArrayList<NewAuctionEventResponse> responses = new ArrayList<NewAuctionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewAuctionEventResponse typedResponse = new NewAuctionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.auctionContract = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.owner = (Address) eventValues.getNonIndexedValues().get(1);
            typedResponse.workId = (Uint256) eventValues.getNonIndexedValues().get(2);
            typedResponse.minValue = (Uint256) eventValues.getNonIndexedValues().get(3);
            typedResponse.startTime = (Uint256) eventValues.getNonIndexedValues().get(4);
            typedResponse.endTime = (Uint256) eventValues.getNonIndexedValues().get(5);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewAuctionEventResponse> newAuctionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NewAuctionEventResponse>() {
            @Override
            public NewAuctionEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWAUCTION_EVENT, log);
                NewAuctionEventResponse typedResponse = new NewAuctionEventResponse();
                typedResponse.log = log;
                typedResponse.auctionContract = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.owner = (Address) eventValues.getNonIndexedValues().get(1);
                typedResponse.workId = (Uint256) eventValues.getNonIndexedValues().get(2);
                typedResponse.minValue = (Uint256) eventValues.getNonIndexedValues().get(3);
                typedResponse.startTime = (Uint256) eventValues.getNonIndexedValues().get(4);
                typedResponse.endTime = (Uint256) eventValues.getNonIndexedValues().get(5);
                return typedResponse;
            }
        });
    }

    public Flowable<NewAuctionEventResponse> newAuctionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWAUCTION_EVENT));
        return newAuctionEventFlowable(filter);
    }

    public List<AuctionCreatedEventResponse> getAuctionCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONCREATED_EVENT, transactionReceipt);
        ArrayList<AuctionCreatedEventResponse> responses = new ArrayList<AuctionCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.auctionContract = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.owner = (Address) eventValues.getNonIndexedValues().get(1);
            typedResponse.numAuctions = (Uint256) eventValues.getNonIndexedValues().get(2);
            typedResponse.allAuctions = (DynamicArray<Address>) eventValues.getNonIndexedValues().get(3);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AuctionCreatedEventResponse>() {
            @Override
            public AuctionCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(AUCTIONCREATED_EVENT, log);
                AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.auctionContract = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.owner = (Address) eventValues.getNonIndexedValues().get(1);
                typedResponse.numAuctions = (Uint256) eventValues.getNonIndexedValues().get(2);
                typedResponse.allAuctions = (DynamicArray<Address>) eventValues.getNonIndexedValues().get(3);
                return typedResponse;
            }
        });
    }

    public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONCREATED_EVENT));
        return auctionCreatedEventFlowable(filter);
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
    public static AuctionFactoryContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionFactoryContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AuctionFactoryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionFactoryContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AuctionFactoryContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AuctionFactoryContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AuctionFactoryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AuctionFactoryContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AuctionFactoryContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AuctionFactoryContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<AuctionFactoryContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AuctionFactoryContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AuctionFactoryContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AuctionFactoryContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AuctionFactoryContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AuctionFactoryContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class NewAuctionEventResponse {
        public Log log;

        public Address auctionContract;

        public Address owner;

        public Uint256 workId;

        public Uint256 minValue;

        public Uint256 startTime;

        public Uint256 endTime;
    }

    public static class AuctionCreatedEventResponse {
        public Log log;

        public Address auctionContract;

        public Address owner;

        public Uint256 numAuctions;

        public DynamicArray<Address> allAuctions;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public Address previousOwner;

        public Address newOwner;
    }
}
