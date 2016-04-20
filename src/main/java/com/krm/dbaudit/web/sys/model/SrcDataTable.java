package com.krm.dbaudit.web.sys.model;

public class SrcDataTable {
	private String tableName;
	private String tableAlias;
	private Long   tableTypeid;
	private String   typeName;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableAlias() {
		return tableAlias;
	}
	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}
	public Long getTableTypeid() {
		return tableTypeid;
	}
	public void setTableTypeid(Long tableTypeid) {
		this.tableTypeid = tableTypeid;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
}
