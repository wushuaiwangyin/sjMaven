package com.krm.dbaudit.web.verify.model;

import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

@SuppressWarnings({ "unused"})
@Table(name="CUS_GUAR_LOAN_REL_TEMP_1")
public class CusGuarFlow extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 客户担保流向基础数据
	 */
	private Long id;	//编号
	private String contNo; //交易日期
	private String guarContNo; //交易日期
	private String borrowerNo; //交易日期
	private String borrowerName; //交易日期
	private String certCode; //交易日期
	private String guarNo; //交易日期
	private String guarName; //交易日期
	private String cerNo; //交易日期
	private String guarWay; //交易日期
	private String guarStartDate; //交易日期
	private String guarEndDate; //交易日期
	private String guarContState; //交易日期
	private Double guarAmt;
	private String finaBrId; //交易日期
	private String organname; //交易日期
	private String customerMgr; //交易日期
	private String actorname; //交易日期
	private String gageName; //交易日期
	private String rightCertNo; //交易日期
	private Double evalAmt;
	private Double bookAmt;
	private String billNo; //交易日期
	private Double loanAmount;
	private String loanStartDate; //交易日期
	private String loanEndDate; //交易日期
	private String accountStatus; //交易日期
	private Integer searchSeq;
	private Integer searchStep;
	private Integer searchFlow;
	private Integer searchUser;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContNo() {
		return contNo;
	}
	public void setContNo(String contNo) {
		this.contNo = contNo;
	}
	public String getGuarContNo() {
		return guarContNo;
	}
	public void setGuarContNo(String guarContNo) {
		this.guarContNo = guarContNo;
	}
	public String getBorrowerNo() {
		return borrowerNo;
	}
	public void setBorrowerNo(String borrowerNo) {
		this.borrowerNo = borrowerNo;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public String getCertCode() {
		return certCode;
	}
	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}
	public String getGuarNo() {
		return guarNo;
	}
	public void setGuarNo(String guarNo) {
		this.guarNo = guarNo;
	}
	public String getGuarName() {
		return guarName;
	}
	public void setGuarName(String guarName) {
		this.guarName = guarName;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	public String getGuarWay() {
		return guarWay;
	}
	public void setGuarWay(String guarWay) {
		this.guarWay = guarWay;
	}
	public String getGuarStartDate() {
		return guarStartDate;
	}
	public void setGuarStartDate(String guarStartDate) {
		this.guarStartDate = guarStartDate;
	}
	public String getGuarEndDate() {
		return guarEndDate;
	}
	public void setGuarEndDate(String guarEndDate) {
		this.guarEndDate = guarEndDate;
	}
	public String getGuarContState() {
		return guarContState;
	}
	public void setGuarContState(String guarContState) {
		this.guarContState = guarContState;
	}
	public Double getGuarAmt() {
		return guarAmt;
	}
	public void setGuarAmt(Double guarAmt) {
		this.guarAmt = guarAmt;
	}
	public String getFinaBrId() {
		return finaBrId;
	}
	public void setFinaBrId(String finaBrId) {
		this.finaBrId = finaBrId;
	}
	public String getOrganname() {
		return organname;
	}
	public void setOrganname(String organname) {
		this.organname = organname;
	}
	public String getCustomerMgr() {
		return customerMgr;
	}
	public void setCustomerMgr(String customerMgr) {
		this.customerMgr = customerMgr;
	}
	public String getActorname() {
		return actorname;
	}
	public void setActorname(String actorname) {
		this.actorname = actorname;
	}
	public String getGageName() {
		return gageName;
	}
	public void setGageName(String gageName) {
		this.gageName = gageName;
	}
	public String getRightCertNo() {
		return rightCertNo;
	}
	public void setRightCertNo(String rightCertNo) {
		this.rightCertNo = rightCertNo;
	}
	public Double getEvalAmt() {
		return evalAmt;
	}
	public void setEvalAmt(Double evalAmt) {
		this.evalAmt = evalAmt;
	}
	public Double getBookAmt() {
		return bookAmt;
	}
	public void setBookAmt(Double bookAmt) {
		this.bookAmt = bookAmt;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(String loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public String getLoanEndDate() {
		return loanEndDate;
	}
	public void setLoanEndDate(String loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Integer getSearchSeq() {
		return searchSeq;
	}
	public void setSearchSeq(Integer searchSeq) {
		this.searchSeq = searchSeq;
	}
	public Integer getSearchStep() {
		return searchStep;
	}
	public void setSearchStep(Integer searchStep) {
		this.searchStep = searchStep;
	}
	public Integer getSearchFlow() {
		return searchFlow;
	}
	public void setSearchFlow(Integer searchFlow) {
		this.searchFlow = searchFlow;
	}
	public Integer getSearchUser() {
		return searchUser;
	}
	public void setSearchUser(Integer searchUser) {
		this.searchUser = searchUser;
	}
	
	
	
	
	
}
