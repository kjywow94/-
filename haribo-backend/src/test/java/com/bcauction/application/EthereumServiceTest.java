package com.bcauction.application;

import com.bcauction.Application;
import com.bcauction.domain.Address;
import com.bcauction.domain.wrapper.Block;
import com.bcauction.domain.wrapper.EthereumTransaction;
import com.bcauction.domain.wrapper.EthereumTransactionData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EthereumServiceTest
{
	@Autowired
	private IEthereumService ethereumService;

	@Autowired
	private Web3j web3j;
	
//	@Test
//	public void test블록조회(final boolean fullFetched) throws InterruptedException, ExecutionException {
//		EthBlock latestBlockResponse;
//		latestBlockResponse
//				= web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, fullFetched).sendAsync().get();
//		 
//		System.out.println(latestBlockResponse.getBlock());
//	}
	
	@Test
	public void test최근블록조회()
	{
		List<Block> blocks = this.ethereumService.최근블록조회();

		assert blocks != null;
		assert blocks.size() == 20;
		
	}

	@Test
	public void test최근트랜잭션조회()
	{
		List<EthereumTransaction> txs = this.ethereumService.최근트랜잭션조회();
		
		System.out.println(txs.size());
		for(int i=0;i<txs.size();i++) {
			System.out.println(txs.get(i).toString());
		}

		assert txs != null;
	}

	@Test
	public void test블록검색()
	{
		Block b = this.ethereumService.블록검색("17462");
		
		for(int i=0;i<b.getTrans().size();i++) {
			System.out.println(b.getTrans().get(i).toString());
		}

		assert b != null;
		assert b.getBlockNo().intValue() == 100;
	}

	@Test
	public void test트랜잭션검색()
	{
		EthereumTransaction tx = this.ethereumService.트랜잭션검색("0x346790d6f2541816386854e84bd44d971b1b166f327cfe0f587465d3dbee4110");

		assert tx != null;
		System.out.println(tx.toString());
		assert !tx.getBlockId().isEmpty();
		assert Integer.valueOf(tx.getBlockId()) > 0;
		System.out.println(tx.getFrom() + " --> " + tx.getTo() + " : " + tx.getAmount());
	}

	@Test
	public void test충전()
	{
		String 충전받을주소 = "0xef9b446E38Cb9982288439387faCEc7bFEc4A546";
		String txhash = this.ethereumService.충전(충전받을주소);

		System.out.println(txhash);
		assert txhash != null || !txhash.equals("");
	}	

	@Test
	public void test주소검색()
	{
		String 주소 = "0xef9b446E38Cb9982288439387faCEc7bFEc4A546";
		Address addr = this.ethereumService.주소검색(주소);

		assert addr != null;
	}
}