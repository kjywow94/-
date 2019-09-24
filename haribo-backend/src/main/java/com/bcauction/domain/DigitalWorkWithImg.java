package com.bcauction.domain;

public class DigitalWorkWithImg extends DigitalWork {

	private String imgData;

	public DigitalWorkWithImg(String imgData) {
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
