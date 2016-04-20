package com.krm.dbaudit.web.risk.model;

public class ModelData {
	private Integer id;
	private Integer noticeStatus;
	private Integer dealStatus;
	private Integer modelId;
	private String organId;
	private String organName;
	private String dataDate;
	private String custNo;
	private String teller;
	private String custManager;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNoticeStatus() {
		return noticeStatus;
	}
	public void setNoticeStatus(Integer noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	public Integer getDealStatus()
	{
		return dealStatus;
	}
	public void setDealStatus(Integer dealStatus)
	{
		this.dealStatus = dealStatus;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getDataDate() {
		return dataDate;
	}
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getTeller() {
		return teller;
	}
	public void setTeller(String teller) {
		this.teller = teller;
	}
	public String getCustManager() {
		return custManager;
	}
	public void setCustManager(String custManager) {
		this.custManager = custManager;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	
	
	
}
