package com.bcauction.domain;

public class AuctionWithImg extends Auction {

	private String imgData;

	public AuctionWithImg(String imgData) {
		super();
		this.imgData = imgData;
	}

	public String getImgData() {
		return imgData;
	}

	public void setImgData(String imgData) {
		this.imgData = imgData;
	}

}
