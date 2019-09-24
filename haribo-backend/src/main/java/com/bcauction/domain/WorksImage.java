package com.bcauction.domain;

public class WorksImage {
	private String imgName;
	private String imgData;
	private String extension;

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgData() {
		return imgData;
	}

	public void setImgData(String imgData) {
		this.imgData = imgData;
	}

	public WorksImage(String imgName, String extension, String imgData) {
		super();
		this.imgName = imgName;
		this.extension = extension;
		this.imgData = imgData;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "WorksImage [imgName=" + imgName + ", imgData=" + imgData + ", extension=" + extension + "]";
	}
}
