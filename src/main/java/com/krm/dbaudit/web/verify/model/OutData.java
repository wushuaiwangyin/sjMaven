package com.krm.dbaudit.web.verify.model;

import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

@Table(name="VERIFY_OUT_DATA")
public class OutData extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String eventDesc;	//事件
	private String eventUser;	//事件人
	private String eventDate;	//事件时间
	private String source;	//网站来源
	private String busType;	//行业性质
	private String province;	//所属省份
	private String idNum;	//事件人证件号码
	private String importDate;	//导入日期
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public String getEventUser() {
		return eventUser;
	}
	public void setEventUser(String eventUser) {
		this.eventUser = eventUser;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getImportDate() {
		return importDate;
	}
	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}
	
	
}
