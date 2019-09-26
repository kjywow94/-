package com.bcauction.application.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.omg.IOP.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionHash;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import com.bcauction.application.IAuctionContractService;
import com.bcauction.application.IEthereumService;
import com.bcauction.domain.Address;
import com.bcauction.domain.exception.ApplicationException;
import com.bcauction.domain.repository.ITransactionRepository;
import com.bcauction.domain.wrapper.AuctionContract;
import com.bcauction.domain.wrapper.Block;
import com.bcauction.domain.wrapper.EthereumTransaction;
import com.bcauction.domain.wrapper.EthereumTransactionData;

@Service
public class EthereumService implements IEthereumService {

	static final String chargeAmount = "5";
	private static final Logger log = LoggerFactory.getLogger(EthereumService.class);

	public static final BigInteger GAS_PRICE = BigInteger.valueOf(1L);
	public static final BigInteger GAS_LIMIT = BigInteger.valueOf(21_000L);

	@Value("${eth.admin.address}")
	private String ADMIN_ADDRESS;
	@Value("${eth.encrypted.password}")
	private String PASSWORD;
	@Value("${eth.admin.wallet.filename}")
	private String ADMIN_WALLET_FILE;

	private ITransactionRepository transactionRepository;

	@Autowired
	private Web3j web3j;

	@Autowired
	private Admin admin = Admin.build(new HttpService());

	@Autowired
	public EthereumService(ITransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	private EthBlock.Block 최근블록(final boolean fullFetched) {
		try {
			EthBlock latestBlockResponse;
			latestBlockResponse = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, fullFetched).sendAsync()
					.get();

			return latestBlockResponse.getBlock();
		} catch (ExecutionException | InterruptedException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	/**
	 * 최근 블록 조회 예) 최근 20개의 블록 조회
	 * 
	 * @return List<Block>
	 */
	@Override
	public List<Block> 최근블록조회() {
		List<Block> blocks = new ArrayList<Block>();

		try {
			EthBlock latestBlockResponse;
			latestBlockResponse = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).sendAsync().get();

			blocks.clear();

			int last_num = Integer.parseInt(latestBlockResponse.getBlock().getNumber().toString());
			for (int i = 0; i < 20; i++) {
				BigInteger biginteger = new BigInteger(Integer.toString(last_num - i));

				DefaultBlockParameterNumber targetId = new DefaultBlockParameterNumber(biginteger);
				latestBlockResponse = web3j.ethGetBlockByNumber(targetId, true).sendAsync().get();
				blocks.add(Block.fromOriginalBlock(latestBlockResponse.getBlock()));
			}

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return blocks;
	}

	/**
	 * 최근 생성된 블록에 포함된 트랜잭션 조회 이더리움 트랜잭션을 EthereumTransaction으로 변환해야 한다.
	 * 
	 * @return List<EthereumTransaction>
	 */
	@Override
	public List<EthereumTransaction> 최근트랜잭션조회() {
		List<EthereumTransaction> transactionList = new ArrayList<EthereumTransaction>();
		try {
			EthBlock latestBlockResponse;
			latestBlockResponse = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).sendAsync().get();
			List<TransactionResult> transactions = latestBlockResponse.getBlock().getTransactions();

			transactionList = EthereumTransaction.getEthereumTransactionList(transactions,
					latestBlockResponse.getBlock().getTimestamp(), true);

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return transactionList;
	}

	/**
	 * 특정 블록 검색 조회한 블록을 Block으로 변환해야 한다.
	 * 
	 * @param 블록No
	 * @return Block
	 */
	@Override
	public Block 블록검색(String 블록No) {
		Block block = new Block();
		try {
			EthBlock latestBlockResponse;
			BigInteger bigInteger = new BigInteger(블록No);
			DefaultBlockParameterNumber targetNum = new DefaultBlockParameterNumber(bigInteger);
			latestBlockResponse = web3j.ethGetBlockByNumber(targetNum, true).sendAsync().get();

			EthBlock.Block currentBlock = latestBlockResponse.getBlock();

			block = Block.fromOriginalBlock(currentBlock);

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return block;
	}

	/**
	 * 특정 hash 값을 갖는 트랜잭션 검색 조회한 트랜잭션을 EthereumTransaction으로 변환해야 한다.
	 * 
	 * @param 트랜잭션Hash
	 * @return EthereumTransaction
	 */
	@Override
	public EthereumTransaction 트랜잭션검색(String 트랜잭션Hash) {
		EthereumTransaction tx = new EthereumTransaction();

		try {
			EthTransaction data = web3j.ethGetTransactionByHash(트랜잭션Hash).sendAsync().get();

			EthBlock.TransactionObject hash = new EthBlock.TransactionObject(data.getResult().getHash(),
					data.getResult().getNonceRaw(), data.getResult().getBlockHash(),
					data.getResult().getBlockNumberRaw(), data.getResult().getTransactionIndexRaw(),
					data.getResult().getFrom(), data.getResult().getTo(), data.getResult().getValueRaw(),
					data.getResult().getGasPriceRaw(), data.getResult().getGasRaw(), data.getResult().getInput(),
					data.getResult().getCreates(), data.getResult().getPublicKey(), data.getResult().getRaw(),
					data.getResult().getR(), data.getResult().getS(), (int) data.getResult().getV());
			Block block = this.블록검색(data.getResult().getBlockNumber().toString());

			tx = EthereumTransaction.getEthereumTransaction(hash, block.getTimeStamp(), true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tx;
	}

	/**
	 * 이더리움으로부터 해당 주소의 잔액을 조회하고 동기화한 트랜잭션 테이블로부터 Address 정보의 trans 필드를 완성하여 정보를
	 * 반환한다.
	 * 
	 * @param 주소
	 * @return Address
	 */
	@Override
	public Address 주소검색(String 주소) {
		Address addr = new Address();

		try {
			EthGetBalance ethBal = web3j.ethGetBalance(주소, DefaultBlockParameterName.LATEST).sendAsync().get();
			EthGetTransactionCount counts = web3j.ethGetTransactionCount(주소, DefaultBlockParameterName.LATEST)
					.sendAsync().get();
			addr.setId(주소);
			addr.setBalance(ethBal.getBalance());
			addr.setTxCount(counts.getTransactionCount());
			List<com.bcauction.domain.Transaction> trans = this.transactionRepository.조회By주소(주소);
			List<EthereumTransaction> tarnsactions = new ArrayList<EthereumTransaction>();
			for (int idx = 0; idx < trans.size(); idx++) {
				EthereumTransaction ethTran = EthereumTransaction.convertTransaction(trans.get(idx));
				tarnsactions.add(ethTran);
			}
			addr.setTrans(tarnsactions);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO
		return addr;
	}

	/**
	 * [주소]로 시스템에서 정한 양 만큼 이더를 송금한다. 이더를 송금하는 트랜잭션을 생성, 전송한 후 결과인 String형의 트랜잭션 hash
	 * 값을 반환한다.
	 * 
	 * @param 주소
	 * @return String 생성된 트랜잭션의 hash 반환 (참고, TransactionReceipt)
	 */
	@Override
	public String 충전(final String 주소) // 특정 주소로 테스트 특정 양(5Eth) 만큼 충전해준다.
	{
		// TODO
		// unlock
		PersonalUnlockAccount unlockResponse = null;
		try {
			unlockResponse = admin.personalUnlockAccount(ADMIN_ADDRESS, PASSWORD).send();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String txHash = null;
		if (unlockResponse.accountUnlocked()) {
			BigDecimal amountWei = Convert.toWei(chargeAmount, Convert.Unit.ETHER);
			Transaction transaction = Transaction.createEtherTransaction(ADMIN_ADDRESS, null,
					DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, 주소, amountWei.toBigInteger());
			EthSendTransaction ethSendTransaction;
			try {
				ethSendTransaction = web3j.ethSendTransaction(transaction).sendAsync().get();
				txHash = ethSendTransaction.getTransactionHash();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch bloc
				e.printStackTrace();
			}
		}
		return txHash;
	}

	@Override
	public BigInteger getBalance(final String address) {
		BigInteger result = null;
		try {
			EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync()
					.get();
			BigInteger wei = ethGetBalance.getBalance();
			BigDecimal ethFromWei = Convert.fromWei(new BigDecimal(wei), Convert.Unit.ETHER);
			result = ethFromWei.toBigInteger();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void updateTransactions(String 블록No) {
		Block block = this.블록검색(블록No);
		List<EthereumTransaction> trans = block.getTrans();
		for (int idx = 0; idx < trans.size(); idx++) {
			com.bcauction.domain.Transaction 트랜잭션 = new com.bcauction.domain.Transaction();
			트랜잭션.setBlockNumber(trans.get(idx).getBlockNumber());
			트랜잭션.setHash(trans.get(idx).getTxHash());
			trans.get(idx).getStatus();
			trans.get(idx).getBlockId();
			trans.get(idx).getTimestamp();
			트랜잭션.setFrom(trans.get(idx).getFrom());
			트랜잭션.setTo(trans.get(idx).getTo());
	        String value = "0";
	        if(trans.get(idx).getAmount()!=null) {
	            value = String.valueOf(trans.get(idx).getAmount());   
	        }
	        트랜잭션.setValue(value);
			트랜잭션.setGas(trans.get(idx).getGas());
			트랜잭션.setGasPrice(trans.get(idx).getGasPrice());
			this.transactionRepository.추가(트랜잭션);
		}
	}
	
	
	/**
	 * 임의생성 메소드
	 * @return 최근트랜잭션10개뿌리기
	 */
	@Override
	public List<EthereumTransaction> 최근트랜잭션뿌리기() {
		List<com.bcauction.domain.Transaction> trans = this.transactionRepository.최근목록조회();
		List<EthereumTransaction> tarnsactions = new ArrayList<EthereumTransaction>();
		for (int idx = 0; idx < trans.size(); idx++) {
			EthereumTransaction ethTran = EthereumTransaction.convertTransaction(trans.get(idx));
			tarnsactions.add(ethTran);
		}
		return tarnsactions;
	}
}
