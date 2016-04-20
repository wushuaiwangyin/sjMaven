package com.krm.dbaudit.web.entitydocument.model;

import java.io.Serializable;

public class YearVsId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2577632658857633941L;
	
	private long id;
	private String year;
	private String colName;
	private String data;
	
	
	private JyzbInfo jyzbInfo;
	
	public JyzbInfo getJyzbInfo() {
		return jyzbInfo;
	}
	public void setJyzbInfo(JyzbInfo jyzbInfo) {
		this.jyzbInfo = jyzbInfo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

}
