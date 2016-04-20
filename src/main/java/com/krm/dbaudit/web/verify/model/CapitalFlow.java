package com.krm.dbaudit.web.verify.model;

import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;
@SuppressWarnings({ "unused"})
@Table(name="SYSMONEY_FLOW_TEMP_1")
public class CapitalFlow extends BaseEntity{

	/**
	 * 客户资金流向基础数据
	 */
	private static final long serialVersionUID = 1L;
	private Long id;	//编号
	private String trandate; //交易日期
	private String acctno; //账号
	private String serseqno; //流水号
	private String name;	//客户名称
	private String acctno1;
	private String cusid;
	private Double tranamt;
	private String cdflag;
	private String cashtranflag;
	private String trancode;
	private String brc;
	private String teller;
	private Integer searchSeq;
	private Integer searchStep;
	private Integer searchFlow;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTrandate() {
		return trandate;
	}
	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getSerseqno() {
		return serseqno;
	}
	public void setSerseqno(String serseqno) {
		this.serseqno = serseqno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAcctno1() {
		return acctno1;
	}
	public void setAcctno1(String acctno1) {
		this.acctno1 = acctno1;
	}
	public String getCusid() {
		return cusid;
	}
	public void setCusid(String cusid) {
		this.cusid = cusid;
	}
	
	public Double getTranamt() {
		return tranamt;
	}
	public void setTranamt(Double tranamt) {
		this.tranamt = tranamt;
	}
	public String getCdflag() {
		return cdflag;
	}
	public void setCdflag(String cdflag) {
		this.cdflag = cdflag;
	}
	public String getCashtranflag() {
		return cashtranflag;
	}
	public void setCashtranflag(String cashtranflag) {
		this.cashtranflag = cashtranflag;
	}
	public String getTrancode() {
		return trancode;
	}
	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}
	public String getBrc() {
		return brc;
	}
	public void setBrc(String brc) {
		this.brc = brc;
	}
	public String getTeller() {
		return teller;
	}
	public void setTeller(String teller) {
		this.teller = teller;
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
	
	

}
