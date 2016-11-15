package com.feicui.model;

import java.io.Serializable;

public class FoodType implements Serializable{

	private String fType;
	private String fName;
	private String fPrice;
	private String fUnits;
	private String fPic;
	private String fVersion;
	private String fRemark;
	private String fID;
	private boolean isCheck;
	private int count=1;
	private float allPrice;
	private String userRemark="ÎÞ±¸×¢";
	

	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	public float getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(float allPrice) {
		this.allPrice = allPrice;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getfType() {
		return fType;
	}
	public void setfType(String fType) {
		this.fType = fType;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getfPrice() {
		return fPrice;
	}
	public void setfPrice(String fPrice) {
		this.fPrice = fPrice;
	}
	public String getfUnits() {
		return fUnits;
	}
	public void setfUnits(String fUnits) {
		this.fUnits = fUnits;
	}
	public String getfPic() {
		return fPic;
	}
	public void setfPic(String fPic) {
		this.fPic = fPic;
	}
	public String getfVersion() {
		return fVersion;
	}
	public void setfVersion(String fVersion) {
		this.fVersion = fVersion;
	}
	public String getfRemark() {
		return fRemark;
	}
	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}
	public String getfID() {
		return fID;
	}
	public void setfID(String fID) {
		this.fID = fID;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
}
