package com.krm.dbaudit.web.entitydocument.model;

import java.io.Serializable;

public class UserOrgBaseInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5675967924992989018L;
/**
 * 业务单位
 */
private String busOrgan;
/**
 * 编制人
 */
private String bzr;
/**
 * 审批人
 */
private String spr;
/**
 * 报告日期
 */
private String repDate;

/**
 * 机构名称
 * 
 * */
private String organName;

/**
 * 开户日期
 */
private  String openDate;


/**
 * 机构地址
 */
private String address;

/**
 * 邮编
 */
private String postCode;


public String getBusOrgan() {
	return busOrgan;
}


public void setBusOrgan(String busOrgan) {
	this.busOrgan = busOrgan;
}


public String getBzr() {
	return bzr;
}


public void setBzr(String bzr) {
	this.bzr = bzr;
}


public String getSpr() {
	return spr;
}


public void setSpr(String spr) {
	this.spr = spr;
}


public String getRepDate() {
	return repDate;
}


public void setRepDate(String repDate) {
	this.repDate = repDate;
}


public String getOrganName() {
	return organName;
}


public void setOrganName(String organName) {
	this.organName = organName;
}


public String getOpenDate() {
	return openDate;
}


public void setOpenDate(String openDate) {
	this.openDate = openDate;
}


public String getAddress() {
	return address;
}


public void setAddress(String address) {
	this.address = address;
}


public String getPostCode() {
	return postCode;
}


public void setPostCode(String postCode) {
	this.postCode = postCode;
}


public String getMoveTelNo() {
	return moveTelNo;
}


public void setMoveTelNo(String moveTelNo) {
	this.moveTelNo = moveTelNo;
}


/**
 * 移动电话 	
 */
private String moveTelNo;


@Override
public String toString() {
	return "UserOrgBaseInfo [busOrgan=" + busOrgan + ", bzr=" + bzr + ", spr="
			+ spr + ", repDate=" + repDate + ", organName=" + organName
			+ ", openDate=" + openDate + ", address=" + address + ", postCode="
			+ postCode + ", moveTelNo=" + moveTelNo + "]";
}

}
