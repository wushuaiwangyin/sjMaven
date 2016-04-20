package com.krm.dbaudit.web.all360.model;

import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

/**客户公用基本信息表
 * @author cat 2015-10-27
 */
@Table(name="ciscustomerinfo")
public class CustInfoModel extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8281928762213822681L;
private String brc;
private String custId;
private String idtType;
private String idNo;
private String lostDate;
private String custType;
private String custName;
private String custShortName;
private String custEngName;
private String custMngNo;
private String custSubType;
private String salesNo;
private String linkName;
private String linkTel;
private int serverLevel;
private String visaDate;
private String visaAddress;
private String groupId;
private String avaiDate;
private String email;
private String nationCode;
private String shareHolder;
private String varifyType;
private String pswc;
private String openbrc;
private String tellerCode;
private String openDate;
private String modiBrc;
private String modiTeller;
private String authteller;
private String modiDate;
private String ctrlCode;
private String custStatus;
private String reason;
private String issOrgName;
private String issAreaCode;
private String issAreaName;
private String creditFlag;
private String lastModiDate;
private String custValFlag;
private String unAreaCode;
private String sex;
private String jurperName;
private String jurperType;
private String jurperCode;
private String corpCode;
private String remark;

public String getBrc() {
	return brc;
}
public void setBrc(String brc) {
	this.brc = brc;
}
public String getCustId() {
	return custId;
}
public void setCustId(String custId) {
	this.custId = custId;
}
public String getIdtType() {
	return idtType;
}
public void setIdtType(String idtType) {
	this.idtType = idtType;
}
public String getIdNo() {
	return idNo;
}
public void setIdNo(String idNo) {
	this.idNo = idNo;
}
public String getLostDate() {
	return lostDate;
}
public void setLostDate(String lostDate) {
	this.lostDate = lostDate;
}
public String getCustType() {
	return custType;
}
public void setCustType(String custType) {
	this.custType = custType;
}
public String getCustName() {
	return custName;
}
public void setCustName(String custName) {
	this.custName = custName;
}
public String getCustShortName() {
	return custShortName;
}
public void setCustShortName(String custShortName) {
	this.custShortName = custShortName;
}
public String getCustEngName() {
	return custEngName;
}
public void setCustEngName(String custEngName) {
	this.custEngName = custEngName;
}
public String getCustMngNo() {
	return custMngNo;
}
public void setCustMngNo(String custMngNo) {
	this.custMngNo = custMngNo;
}
public String getCustSubType() {
	return custSubType;
}
public void setCustSubType(String custSubType) {
	this.custSubType = custSubType;
}
public String getSalesNo() {
	return salesNo;
}
public void setSalesNo(String salesNo) {
	this.salesNo = salesNo;
}
public String getLinkName() {
	return linkName;
}
public void setLinkName(String linkName) {
	this.linkName = linkName;
}
public String getLinkTel() {
	return linkTel;
}
public void setLinkTel(String linkTel) {
	this.linkTel = linkTel;
}
public int getServerLevel() {
	return serverLevel;
}
public void setServerLevel(int serverLevel) {
	this.serverLevel = serverLevel;
}
public String getVisaDate() {
	return visaDate;
}
public void setVisaDate(String visaDate) {
	this.visaDate = visaDate;
}
public String getVisaAddress() {
	return visaAddress;
}
public void setVisaAddress(String visaAddress) {
	this.visaAddress = visaAddress;
}
public String getGroupId() {
	return groupId;
}
public void setGroupId(String groupId) {
	this.groupId = groupId;
}
public String getAvaiDate() {
	return avaiDate;
}
public void setAvaiDate(String avaiDate) {
	this.avaiDate = avaiDate;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getNationCode() {
	return nationCode;
}
public void setNationCode(String nationCode) {
	this.nationCode = nationCode;
}
public String getShareHolder() {
	return shareHolder;
}
public void setShareHolder(String shareHolder) {
	this.shareHolder = shareHolder;
}
public String getVarifyType() {
	return varifyType;
}
public void setVarifyType(String varifyType) {
	this.varifyType = varifyType;
}
public String getPswc() {
	return pswc;
}
public void setPswc(String pswc) {
	this.pswc = pswc;
}
public String getOpenbrc() {
	return openbrc;
}
public void setOpenbrc(String openbrc) {
	this.openbrc = openbrc;
}
public String getTellerCode() {
	return tellerCode;
}
public void setTellerCode(String tellerCode) {
	this.tellerCode = tellerCode;
}
public String getOpenDate() {
	return openDate;
}
public void setOpenDate(String openDate) {
	this.openDate = openDate;
}
public String getModiBrc() {
	return modiBrc;
}
public void setModiBrc(String modiBrc) {
	this.modiBrc = modiBrc;
}
public String getModiTeller() {
	return modiTeller;
}
public void setModiTeller(String modiTeller) {
	this.modiTeller = modiTeller;
}
public String getAuthteller() {
	return authteller;
}
public void setAuthteller(String authteller) {
	this.authteller = authteller;
}
public String getModiDate() {
	return modiDate;
}
public void setModiDate(String modiDate) {
	this.modiDate = modiDate;
}
public String getCtrlCode() {
	return ctrlCode;
}
public void setCtrlCode(String ctrlCode) {
	this.ctrlCode = ctrlCode;
}
public String getCustStatus() {
	return custStatus;
}
public void setCustStatus(String custStatus) {
	this.custStatus = custStatus;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
public String getIssOrgName() {
	return issOrgName;
}
public void setIssOrgName(String issOrgName) {
	this.issOrgName = issOrgName;
}
public String getIssAreaCode() {
	return issAreaCode;
}
public void setIssAreaCode(String issAreaCode) {
	this.issAreaCode = issAreaCode;
}
public String getIssAreaName() {
	return issAreaName;
}
public void setIssAreaName(String issAreaName) {
	this.issAreaName = issAreaName;
}
public String getCreditFlag() {
	return creditFlag;
}
public void setCreditFlag(String creditFlag) {
	this.creditFlag = creditFlag;
}
public String getLastModiDate() {
	return lastModiDate;
}
public void setLastModiDate(String lastModiDate) {
	this.lastModiDate = lastModiDate;
}
public String getCustValFlag() {
	return custValFlag;
}
public void setCustValFlag(String custValFlag) {
	this.custValFlag = custValFlag;
}
public String getUnAreaCode() {
	return unAreaCode;
}
public void setUnAreaCode(String unAreaCode) {
	this.unAreaCode = unAreaCode;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getJurperName() {
	return jurperName;
}
public void setJurperName(String jurperName) {
	this.jurperName = jurperName;
}
public String getJurperType() {
	return jurperType;
}
public void setJurperType(String jurperType) {
	this.jurperType = jurperType;
}
public String getJurperCode() {
	return jurperCode;
}
public void setJurperCode(String jurperCode) {
	this.jurperCode = jurperCode;
}
public String getCorpCode() {
	return corpCode;
}
public void setCorpCode(String corpCode) {
	this.corpCode = corpCode;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}



}
