package com.krm.dbaudit.web.all360.model;

import java.io.Serializable;
import java.util.Map;

/**表名，列信息整合对象
 * @author cat
 *
 */
public class TabColsDetailModel implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -2580272395483914700L;
/**
 * 物理表名
 */
private String tableName;
/**
 * 列集合
 */
private Map<String, TableModelColumns> colsMap;
/**
 * 查询sql
 */
private String sql;
public String getTableName() {
	return tableName;
}
public void setTableName(String tableName) {
	this.tableName = tableName;
}
public Map<String, TableModelColumns> getColsMap() {
	return colsMap;
}
public void setColsMap(Map<String, TableModelColumns> colsMap) {
	this.colsMap = colsMap;
}
public String getSql() {
	return sql;
}
public void setSql(String sql) {
	this.sql = sql;
}

}
