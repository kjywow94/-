package com.bcauction.application.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcauction.application.IAuctionContractService;
import com.bcauction.application.IAuctionService;
import com.bcauction.application.IDigitalWorkService;
import com.bcauction.application.IFabricService;
import com.bcauction.application.IMemberService;
import com.bcauction.domain.Auction;
import com.bcauction.domain.AuctionWithImg;
import com.bcauction.domain.Bid;
import com.bcauction.domain.DigitalWork;
import com.bcauction.domain.Token;
import com.bcauction.domain.repository.IAuctionRepository;
import com.bcauction.domain.repository.IBidRepository;

@Service
public class AuctionService implements IAuctionService {
	public static final Logger logger = LoggerFactory.getLogger(AuctionService.class);

	private IAuctionContractService auctionContractService;
	private IFabricService fabricService;
	private IAuctionRepository auctionRepository;
	private IBidRepository bidRepository;

	@Autowired
	private PushService pushService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IDigitalWorkService digitarWorkService;

	final String bid = " 경매에 새로운 입찰이 있습니다.";
	final String cancel = " 경매가 취소되었습니다.";
	final String win = " 경매에 낙찰되었습니다";

	@Autowired
	public AuctionService(IFabricService fabricService, IAuctionRepository auctionRepository,
			AuctionContractService auctionContractService, IBidRepository bidRepository) {
		this.auctionContractService = auctionContractService;
		this.fabricService = fabricService;
		this.auctionRepository = auctionRepository;
		this.bidRepository = bidRepository;
	}

	@Override
	public List<Auction> 경매목록조회() {
		return this.auctionRepository.목록조회();
	}

	@Override
	public Auction 조회(final long 경매id) {
		return this.auctionRepository.조회(경매id);
	}

	@Override
	public Auction 조회(final String 컨트랙트주소) {
		return this.auctionRepository.조회(컨트랙트주소);
	}

	@Override
	public Auction 조회(final long 경매id, final String 상태) {
		return this.auctionRepository.조회(경매id, 상태);
	}

	@Override
	public Auction 생성(final Auction 경매) {
		if (경매.get시작일시() == null)
			return null;
		if (경매.get종료일시() == null)
			return null;
		if (경매.get경매생성자id() == 0)
			return null;
		if (경매.get경매작품id() == 0)
			return null;
		if (경매.get컨트랙트주소() == null)
			return null;
		if (경매.get최저가() == null)
			return null;

		경매.set생성일시(LocalDateTime.now());
		long id = this.auctionRepository.생성(경매);
		return this.auctionRepository.조회(id);
	}

	@Override
	public Bid 입찰(Bid 입찰) {
		Long auctionId = 입찰.get경매id();
		Long workId = auctionRepository.조회(auctionId).get경매작품id();
		DigitalWork work = digitarWorkService.조회(workId);
		String workName = work.get이름();

		Long ownerId = work.get회원id();
		Bid lastBid = null;
		lastBid = bidRepository.최고입잘조회(auctionId);
		if (lastBid != null) {
			Long lastBidderId = lastBid.get경매참여자id();

			Token token = memberService.selectToken(lastBidderId);
			try {
				pushService.MessageSend(workName, token.getToken(), bid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Token ownerTokens = memberService.selectToken(ownerId);
		try {
			pushService.MessageSend(workName, ownerTokens.getToken(), bid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long id = this.bidRepository.생성(입찰);
		return this.bidRepository.조회(id);
	}

	@Override
	public long 입찰횟수(final long id) {
		return this.bidRepository.입찰횟수(id);
	}

	@Override
	public Bid 낙찰(final long 경매id, final long 낙찰자id, final BigInteger 입찰최고가) {
		int affected = this.bidRepository.수정(경매id, 낙찰자id, 입찰최고가);
		if (affected == 0)
			return null;
		return this.bidRepository.조회(경매id, 낙찰자id, 입찰최고가);
	}

	/**
	 * 프론트엔드에서 스마트 컨트랙트의 경매종료(endAuction) 함수 직접 호출 후 백엔드에 경매 상태 동기화를 위해 호출되는 메소드
	 * 
	 * @param 경매id
	 * @param 회원id
	 * @return Auction 1. 해당 경매의 상태가 E(ended)로 바뀌고, 2. 입찰 정보 중 최고 입찰 정보를 '낙찰'로
	 *         업데이트해야 한다. 3. 데이터베이스의 소유권정보를 업데이트 한다. 4. 패브릭 상에도 소유권 이전 정보가 추가되어야 한다.
	 *         5. 업데이트 된 경매 정보를 반환한다.
	 */
	@Override
	public Auction 경매종료(final long 경매id, final long 회원id) {
		// TODO

		Auction auction = this.auctionRepository.조회(경매id);
		DigitalWork work = digitarWorkService.조회(auction.get경매작품id());
		Token token = memberService.selectToken(회원id);
		try {
			pushService.MessageSend(work.get이름(), token.getToken(), win);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Bid bid = this.bidRepository.최고입잘조회(경매id);
		this.bidRepository.수정(경매id, bid.get경매참여자id(), bid.get입찰금액().toBigInteger());

		auction.set상태("E");
		this.auctionRepository.수정(auction);
		fabricService.소유권이전(auction.get경매생성자id(), bid.get경매참여자id(), auction.get경매작품id());
		return auction;
	}

	/**
	 * 프론트엔드에서 스마트 컨트랙트의 경매취소(cancelAuction) 함수 직접 호출 후 백엔드에 경매 상태 동기화를 위해 호출되는 메소드
	 * 
	 * @param 경매id
	 * @param 회원id
	 * @return Auction 1. 해당 경매의 상태와(C,canceled) 종료일시를 업데이트 한다. 2. 입찰 정보 중 최고 입찰 정보를
	 *         '낙찰'로 업데이트해야 한다. 3. 업데이트 된 경매 정보를 반환한다.
	 */
	@Override
	public Auction 경매취소(final long 경매id, final long 회원id) {
		// TODO
		Auction auction = this.auctionRepository.조회(경매id);

		DigitalWork work = digitarWorkService.조회(auction.get경매작품id());
		if (회원id != 0) {
			Token token = memberService.selectToken(회원id);
			try {
				pushService.MessageSend(work.get이름(), token.getToken(), cancel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		auction.set상태("C");
		auction.set종료일시(LocalDateTime.now());
		this.auctionRepository.수정(auction);

		return auction;
	}

	@Override
	public List<AuctionWithImg> auctionList(List<Auction> 목록) {
		List<AuctionWithImg> listWithImg = new ArrayList<>();

		for (Auction auction : 목록) {

			try {
				File f = new File("worksImage/" + auction.get경매작품id());
				FileInputStream fis;
				try {
					fis = new FileInputStream(f);
				} catch (FileNotFoundException e) {
					f = new File("worksImage/artwork1.jpg");
					fis = new FileInputStream(f);
				}
				byte byteArray[] = new byte[(int) f.length()];
				fis.read(byteArray);
				String encodeImg = "data:image/" + auction.getId() + ";base64, "
						+ Base64.getEncoder().encodeToString(byteArray);
				AuctionWithImg awi = new AuctionWithImg(encodeImg);
				fis.close();
				awi.setId(auction.getId());
				awi.set경매생성자id(auction.get경매생성자id());
				awi.set경매작품id(auction.get경매작품id());
				awi.set상태(auction.get상태());
				awi.set생성일시(auction.get생성일시());
				awi.set시작일시(auction.get시작일시());
				awi.set종료일시(auction.get종료일시());
				awi.set최저가(auction.get최저가());
				awi.set컨트랙트주소(auction.get컨트랙트주소());
				listWithImg.add(awi);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listWithImg;
	}
}
