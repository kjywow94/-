package com.bcauction.application.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import com.bcauction.application.IAuctionContractService;
import com.bcauction.application.IDigitalWorkService;
import com.bcauction.application.IMemberService;
import com.bcauction.domain.AuctionInfo;
import com.bcauction.domain.Wallet;
import com.bcauction.domain.repository.IWalletRepository;
import com.bcauction.domain.wrapper.AuctionContract;
import com.bcauction.domain.wrapper.AuctionFactoryContract;

/**
 * AuctionContractService 작성, 배포한 AuctionFactory.sol Auction.sol 스마트 컨트랙트를 이용한다.
 */
@Service
public class AuctionContractService implements IAuctionContractService {
	private static final Logger log = LoggerFactory.getLogger(AuctionContractService.class);

	@Value("${eth.auction.factory.contract}")
	private String AUCTION_FACTORY_CONTRACT;

	@Value("${eth.admin.address}")
	private String ADMIN_ADDRESS;

	@Value("${eth.admin.wallet.filename}")
	private String WALLET_RESOURCE;

	@Value("${eth.encrypted.password}")
	private String PASSWORD;

	private ContractGasProvider contractGasProvider = new DefaultGasProvider();
	private Credentials credentials;

	private IMemberService memberService;
	private IDigitalWorkService digitalWorkService;
	

	@Autowired
	private PushService pushService;

	@Autowired
	private Web3j web3j;

	private IWalletRepository walletRepository;

	final String bid = " 경매에 새로운 입찰이 있습니다.";
	final String cancel = " 경매가 취소되었습니다.";
	final String win = " 경매에 낙찰되었습니다";

	@Autowired
	public AuctionContractService(IWalletRepository walletRepository, IMemberService memberService,
			IDigitalWorkService digitalWorkService) {
		this.walletRepository = walletRepository;
		this.memberService = memberService;
		this.digitalWorkService = digitalWorkService;
	}

	/**
	 * 이더리움 컨트랙트 주소를 이용하여 경매 정보를 조회한다.
	 * 
	 * @param 컨트랙트주소
	 * @return AuctionInfo 1. web3j API를 이용하여 해당 컨트랙트주소의 스마트 컨트랙트를 로드(load)한다. 2.
	 *         info의 highestBidder의 정보를 가지고 최고입찰자 회원의 id를 찾아 3. AuctionInfo의 인스턴스를
	 *         생성하여 반환한다.
	 */
	@Override
	public AuctionInfo 경매정보조회(final String 컨트랙트주소) {
		
		// TODO
		try {
			credentials = WalletUtils.loadCredentials(PASSWORD, WALLET_RESOURCE);
		} catch (IOException | CipherException e) {
			e.printStackTrace();
		}
		AuctionContract auctionContract = AuctionContract.load(컨트랙트주소, web3j, credentials, contractGasProvider);
		AuctionInfo auctionInfo = new AuctionInfo();
		try {
			auctionInfo.set경매컨트랙트주소(컨트랙트주소);

			String 지갑주소 = auctionContract.highestBidder().sendAsync().get().getValue();
			Wallet user = walletRepository.searchWallet(지갑주소);
			if (user != null) {
				auctionInfo.set최고입찰자id(user.get소유자id());
			} else {
				Wallet owner = walletRepository.searchWallet(auctionContract.owner().sendAsync().get().getValue());
				auctionInfo.set최고입찰자id(owner.get소유자id());
			}
			Long response경매시작시간 = auctionContract.auctionStartTime().sendAsync().get().getValue().longValue();
			Long response경매종료시간 = auctionContract.auctionEndTime().sendAsync().get().getValue().longValue();
			LocalDateTime 경매시작시간 = LocalDateTime.ofInstant(Instant.ofEpochMilli(response경매시작시간),
					TimeZone.getDefault().toZoneId());
			LocalDateTime 경매종료시간 = LocalDateTime.ofInstant(Instant.ofEpochMilli(response경매종료시간),
					TimeZone.getDefault().toZoneId());

			auctionInfo.set최고입찰액(auctionContract.highestBid().sendAsync().get().getValue());
			auctionInfo.set작품id(auctionContract.digitalWorkId().sendAsync().get().getValue().longValue());
			auctionInfo.set경매시작시간(경매시작시간);
			auctionInfo.set경매종료시간(경매종료시간);
			auctionInfo.set최소금액(auctionContract.minValue().sendAsync().get().getValue());
			auctionInfo.set종료(auctionContract.ended().sendAsync().get().getValue());
		} catch (Exception e) {
			System.out.println("auctionContract error");
			e.printStackTrace();
		}
		return auctionInfo;
	}

	/**
	 * 이더리움 컨트랙트 주소를 이용하여 해당 경매의 현재 최고 입찰가를 조회한다.
	 * 
	 * @param 컨트랙트주소
	 * @return BigInteger 현재최고가
	 */
	@Override
	public BigInteger 현재최고가(final String 컨트랙트주소) {
		// TODO
		AuctionInfo auctionInfo = this.경매정보조회(컨트랙트주소);
		return auctionInfo.get최고입찰액();
	}

	/**
	 * 이더리움 컨트랙트 주소를 이용하여 해당 경매의 현재 최고 입찰 주소를 조회한다.
	 * 
	 * @param 컨트랙트주소
	 * @return String 최고 입찰한 이더리움 주소(EOA)
	 */
	@Override
	public String 현재최고입찰자주소(final String 컨트랙트주소) {
		// TODO
		AuctionInfo auctionInfo = this.경매정보조회(컨트랙트주소);
		return Long.toString(auctionInfo.get최고입찰자id());
	}

	/**
	 * 이더리움 컨트랙트 주소를 이용하여 생성된 모든 경매 컨트랙트의 주소 목록을 조회한다.
	 * 
	 * @return List<String> 경매 컨트랙트의 주소 리스트
	 */
	@Override
	public List<String> 경매컨트랙트주소리스트() {
		// TODO
		try {
			credentials = WalletUtils.loadCredentials(PASSWORD, WALLET_RESOURCE);
		} catch (IOException | CipherException e) {
			e.printStackTrace();
		}
		AuctionFactoryContract auctionFactoryContract = AuctionFactoryContract.load(AUCTION_FACTORY_CONTRACT, web3j,
				credentials, contractGasProvider);

		List<String> contractAddressList = new ArrayList<String>();

		try {
			List<org.web3j.abi.datatypes.Address> addressList = auctionFactoryContract.allAuctions().sendAsync().get()
					.getValue();

			for (int idx = 0; idx < addressList.size(); idx++) {
				contractAddressList.add(addressList.get(idx).getValue());
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contractAddressList;
	}
}
