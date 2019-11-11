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
    private static final String BINARY = "608060405234801561001057600080fd5b50610e1b806100206000396000f3006080604052600436106100565763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631bbcd4a5811461005b578063431f21da146100c0578063571a26a0146100fd575b600080fd5b34801561006757600080fd5b50610070610115565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156100ac578181015183820152602001610094565b505050509050019250505060405180910390f35b3480156100cc57600080fd5b506100e1600435602435604435606435610177565b60408051600160a060020a039092168252519081900360200190f35b34801561010957600080fd5b506100e160043561032e565b6060600080548060200260200160405190810160405280929190818152602001828054801561016d57602002820191906000526020600020905b8154600160a060020a0316815260019091019060200180831161014f575b5050505050905090565b6000803386868686610187610356565b600160a060020a039095168552602085019390935260408085019290925260608401526080830191909152519081900360a001906000f0801580156101d0573d6000803e3d6000fd5b50600080546001810182559080527f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563018054600160a060020a03831673ffffffffffffffffffffffffffffffffffffffff19909116811790915560408051918252336020830152818101899052606082018890526080820187905260a08201869052519192507f932f617c6c51dafe060274dfe3c3cb3e02ba867eede9a628e7dfe49eaa2bfa89919081900360c00190a16000805460408051600160a060020a038516815233602082018190529181018390526080606082018181529082018490527f0d314047f0d1e39dbbd521f0844c35332083a15926b906540faa68e1a4262b6894869492909160a082018385801561031457602002820191906000526020600020905b8154600160a060020a031681526001909101906020018083116102f6575b50509550505050505060405180910390a195945050505050565b600080548290811061033c57fe5b600091825260209091200154600160a060020a0316905081565b604051610a8980610367833901905600608060405234801561001057600080fd5b5060405160a080610a898339810160409081528151602083015191830151606084015160809094015160008054600160a060020a031916600160a060020a03909416939093178355600493909355600355600192909255600255610a0f90819061007a90396000f3006080604052600436106100ae5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166312fa6feb81146100b35780631998aeef146100dc5780634b449cba146100e45780636ba35e701461010b5780638da5cb5b146101205780638fa8b7901461015157806391f9015714610168578063963e63c71461017d578063d57bde7914610192578063eb54f9ec146101a7578063fe67a54b146101bc575b600080fd5b3480156100bf57600080fd5b506100c86101d1565b604080519115158252519081900360200190f35b6100c86101da565b3480156100f057600080fd5b506100f9610668565b60408051918252519081900360200190f35b34801561011757600080fd5b506100f961066e565b34801561012c57600080fd5b50610135610674565b60408051600160a060020a039092168252519081900360200190f35b34801561015d57600080fd5b50610166610683565b005b34801561017457600080fd5b506101356107d9565b34801561018957600080fd5b506100f96107e8565b34801561019e57600080fd5b506100f96107ee565b3480156101b357600080fd5b506100f96107f4565b3480156101c857600080fd5b506101666107fa565b60075460ff1681565b600080548190600160a060020a0316331415610240576040805160e560020a62461bcd02815260206004820152600f60248201527f73656e646572206973206f776e65720000000000000000000000000000000000604482015290519081900360640190fd5b6001544210156102c0576040805160e560020a62461bcd02815260206004820152602b60248201527fec9584eca78120ec8b9cec9e91eb9098eca78020ec958aec9d8020eab2bdeba760448201527fa4ec9e85eb8b88eb8ba42e000000000000000000000000000000000000000000606482015290519081900360840190fd5b600254421115610340576040805160e560020a62461bcd02815260206004820152602160248201527fec9db4ebafb820eca285eba38ceb909c20eab2bdeba7a4ec9e85eb8b88eb8ba460448201527f2e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60075460ff16156103c1576040805160e560020a62461bcd02815260206004820152602160248201527fec9db4ebafb820eca285eba38ceb909c20eab2bdeba7a4ec9e85eb8b88eb8ba460448201527f2e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600554600160a060020a031633141561044a576040805160e560020a62461bcd02815260206004820152603560248201527fecb59ceab3a0ec9e85ecb0b0ec9e90eb8a9420ec8381ec9c8420ec9e85ecb0b060448201527fed95a020ec889820ec9786ec8ab5eb8b88eb8ba42e0000000000000000000000606482015290519081900360840190fd5b6003543410156104ca576040805160e560020a62461bcd02815260206004820152603160248201527fec9e85ecb0b0ec95a1ec9db420ecb59ceca08020ec9e85ecb0b0ec95a1ebb3b460448201527feb8ba420eb82aeec8ab5eb8b88eb8ba42e000000000000000000000000000000606482015290519081900360840190fd5b60065434101561054a576040805160e560020a62461bcd02815260206004820152603160248201527fec9e85ecb0b0ec95a1ec9db420ecb59ceab3a020ec9e85ecb0b0ec95a1ebb3b460448201527feb8ba420eb82aeec8ab5eb8b88eb8ba42e000000000000000000000000000000606482015290519081900360840190fd5b6006541561059b5750600680546000918290556005546040519192600160a060020a039091169183156108fc0291849190818181858888f19350505050151561059b57600681905560009150610664565b60055460005460045460408051338152600160a060020a039485166020820152929093168284015234606083015260808201524360a082015290517f314755ebdafaf0af6d1f76e9ab63f8fc0c856b5af24de9444f4c8524acc3b9f69181900360c00190a16005805473ffffffffffffffffffffffffffffffffffffffff191633179055346006819055604080519182523031602083015280517f32026a51a4c733cce89a887547a6f39cef95700015b9c5c32bd147fae47c5d069281900390910190a1600191505b5090565b60025481565b60045481565b600054600160a060020a031681565b600054600160a060020a031633146106e5576040805160e560020a62461bcd02815260206004820152601360248201527f73656e646572206973206e6f74206f776e657200000000000000000000000000604482015290519081900360640190fd5b60075460ff1615610740576040805160e560020a62461bcd02815260206004820152601860248201527f61756374696f6e20697320616c726561647920656e6465640000000000000000604482015290519081900360640190fd5b6007805460ff1916600117905560045460055460408051928352600160a060020a0390911660208301524382820152517fc326dcfb5d4e924f5e3e717c0f667b0eecb5abb73fc0b33236b6107c1fd48a579181900360600190a1600554600654604051600160a060020a039092169181156108fc0291906000818181858888f193505050501580156107d6573d6000803e3d6000fd5b50565b600554600160a060020a031681565b60035481565b60065481565b60015481565b600054600160a060020a0316331461085c576040805160e560020a62461bcd02815260206004820152601360248201527f73656e646572206973206e6f74206f776e657200000000000000000000000000604482015290519081900360640190fd5b6002544210156108dc576040805160e560020a62461bcd02815260206004820152602160248201527f49742773206e6f74207965742074696d6520666f72207468652061756374696f60448201527f6e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60075460ff1615610937576040805160e560020a62461bcd02815260206004820152601860248201527f61756374696f6e20697320616c726561647920656e6465640000000000000000604482015290519081900360640190fd5b6007805460ff1916600117905560055460005460065460045460408051600160a060020a03958616815293909416602084015282840191909152606082015243608082015290517f59ed408cbaf81c21289519772ca20bf3bf46445b7c20269c6a2adb188358241f9181900360a00190a160008054600654604051600160a060020a039092169281156108fc029290818181858888f193505050501580156107d6573d6000803e3d6000fd00a165627a7a72305820e9b29a58acbd564cffd2dd5baf0324a8f1a1d3c2b2676dfa4c22b280702d4ade0029a165627a7a7230582050663d5a67b028f4c0a99dddbe9cae309e54f9d1a14ac057d6b00748df982e990029";

    public static final String FUNC_ALLAUCTIONS = "allAuctions";

    public static final String FUNC_CREATEAUCTION = "createAuction";

    public static final String FUNC_AUCTIONS = "auctions";

    public static final Event NEWAUCTION_EVENT = new Event("NewAuction", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONCREATED_EVENT = new Event("AuctionCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}));
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
}
