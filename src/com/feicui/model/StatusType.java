package com.feicui.model;

import java.io.Serializable;

public class StatusType implements Serializable{

	private String imageUrl;
	private String fName;
	private String fStatus;
	private String num;
	private String id;
	private String foodNum;
	private boolean isCheck;
	

	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	public String getFoodNum() {
		return foodNum;
	}
	public void setFoodNum(String foodNum) {
		this.foodNum = foodNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getfStatus() {
		return fStatus;
	}

	public void setfStatus(String fStatus) {
		this.fStatus = fStatus;
	}

}
