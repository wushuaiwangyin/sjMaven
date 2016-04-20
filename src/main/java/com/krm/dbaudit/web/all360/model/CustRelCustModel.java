package com.krm.dbaudit.web.all360.model;

import java.io.Serializable;


public class CustRelCustModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1007783980393009841L;
	private String custId;
	private String relName;
	private String idNo;
	private String idType;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getRelName() {
		return relName;
	}
	public void setRelName(String relName) {
		this.relName = relName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	

}
