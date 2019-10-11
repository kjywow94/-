package com.bcauction.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bcauction.application.IAuctionContractService;
import com.bcauction.application.IAuctionService;
import com.bcauction.domain.Auction;
import com.bcauction.domain.AuctionInfo;
import com.bcauction.domain.AuctionWithImg;
import com.bcauction.domain.Bid;
import com.bcauction.domain.exception.ApplicationException;
import com.bcauction.domain.exception.EmptyListException;
import com.bcauction.domain.exception.NotFoundException;











@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AuctionController {
	public static final Logger logger = LoggerFactory.getLogger(AuctionController.class);

	private IAuctionService auctionService;
	private IAuctionContractService auctionContractService;

	@Autowired
	public AuctionController(IAuctionService auctionService, IAuctionContractService auctionContractService) {
		Assert.notNull(auctionService, "auctionService 개체가 반드시 필요!");
		Assert.notNull(auctionContractService, "auctionContractService 개체가 반드시 필요!");

		this.auctionService = auctionService;
		this.auctionContractService = auctionContractService;
	}
	@RequestMapping(value = "/auctions", method = RequestMethod.POST)
	public Auction 생성(@RequestBody Auction auction) {
		Auction 경매 = auctionService.생성(auction);
		if (경매 == null)
			throw new ApplicationException("경매 정보를 입력할 수 없습니다!");

		return 경매;
	}

	@RequestMapping(value = "/auctions", method = RequestMethod.GET)
	public List<AuctionWithImg> 목록조회() {
		List<Auction> 목록 = auctionService.경매목록조회();

		if (목록 == null || 목록.isEmpty())
			throw new EmptyListException("NO DATA");

		return auctionService.auctionList(목록);
	}

	@RequestMapping(value = "/auctions/{id}", method = RequestMethod.GET)
	public AuctionInfo 조회(@PathVariable long id) {
		Auction 경매 = this.auctionService.조회(id);
		if (경매 == null) {
			logger.error("NOT FOUND AUCTION: ", id);
			throw new NotFoundException(id + " 해당 경매를 찾을 수 없습니다.");
		}
		AuctionInfo 경매정보 = this.auctionContractService.경매정보조회(경매.get컨트랙트주소());
		if (경매정보 == null) {
			throw new NotFoundException(id + " 해당 경매 컨트랙트를 찾을 수 없습니다.");
		}
		경매정보.set경매시작시간(경매.get시작일시());
		경매정보.set경매종료시간(경매.get종료일시());

		return 경매정보;
	}
	
	@RequestMapping(value = "/auctions/{id}/status/{status}", method = RequestMethod.GET)
	public boolean 조회(@PathVariable long id, @PathVariable String status) {
		Auction 경매 = this.auctionService.조회(id, status);
		if (경매 == null) {
			return true;
		}

		return false;
	}

	@RequestMapping(value = "/auctions/{aid}/by/{mid}", method = RequestMethod.DELETE)
	public Auction 경매취소(@PathVariable long aid, @PathVariable long mid) {
		return auctionService.경매취소(aid, mid);
	}

	@RequestMapping(value = "/auctions/{aid}/by/{mid}", method = RequestMethod.PUT)
	public Auction 경매종료(@PathVariable long aid, @PathVariable long mid) { // mid = 최고가 입찰자 id
		return this.auctionService.경매종료(aid, mid);
	}

	@RequestMapping(value = "/auctions/bid", method = RequestMethod.PUT)
	public Bid 입찰(@RequestBody Bid bid) {
		return auctionService.입찰(bid);
	}
	@RequestMapping(value = "/auctions/bid/{id}", method = RequestMethod.GET)
	public long 입찰횟수(@PathVariable long id) {
		return auctionService.입찰횟수(id);
	}

	/**
	 * 협업과제 협업과제 week. 4-7 mission. 3 Req. 1-2
	 */
	@RequestMapping(value = "/auctions/owner/{id}", method = RequestMethod.GET)
	public List<AuctionWithImg> 사용자경매목록조회(@PathVariable int id) {
		// TODO
		List<Auction> result = new ArrayList<Auction>();
		for (Auction a : auctionService.경매목록조회())
			if (a.get경매생성자id() == id)
				result.add(a);

		return auctionService.auctionList(result);
	}
}
