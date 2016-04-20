package com.krm.dbaudit.web.entitydocument.model;

import java.io.Serializable;

public class OtherThings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2376356301304948205L;
	
	/**
	 * 序号
	 */
	private long id;
	/**
	 * 字段1
	 */
	private String recd1;
	/**
	 * 字段2
	 */
	private String recd2;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRecd1() {
		return recd1;
	}
	public void setRecd1(String recd1) {
		this.recd1 = recd1;
	}
	public String getRecd2() {
		return recd2;
	}
	public void setRecd2(String recd2) {
		this.recd2 = recd2;
	}
	

}
