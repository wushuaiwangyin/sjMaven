package com.krm.dbaudit.web.all360.model;

import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;


public class TableModelColumns  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2782399609047059896L;
	private long id;//columnid
	private long tab_id;
	private String alise;
	private String colName;
	private String isShow;
	private String dataType;
	private long dicid;
	private String dicName;
	private String tabName;
	private long modelId;
	private String modelName;
	
	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public long getModelId() {
		return modelId;
	}

	public void setModelId(long modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTab_id() {
		return tab_id;
	}

	public void setTab_id(long tab_id) {
		this.tab_id = tab_id;
	}

	public String getAlise() {
		return alise;
	}

	public void setAlise(String alise) {
		this.alise = alise;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public long getDicid() {
		return dicid;
	}

	public void setDicid(long dicid) {
		this.dicid = dicid;
	}

}
