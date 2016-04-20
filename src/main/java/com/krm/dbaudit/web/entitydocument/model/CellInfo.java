package com.krm.dbaudit.web.entitydocument.model;

import java.io.Serializable;

/**
 * @author cat
 *把每个单元格作为一个类对象处理
 */
/**
 * @author cat
 *
 */
/**
 * @author cat
 *
 */
public class CellInfo  implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -4808821272161633151L;
/**
 * 数据库物理行序号
 */
private long id;
private String colName;
private String data;
private String dataDate;

public String getDataDate() {
	return dataDate;
}
public void setDataDate(String dataDate) {
	this.dataDate = dataDate;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getColName() {
	return colName;
}
public void setColName(String colName) {
	this.colName = colName;
}
public String getData() {
	return data;
}
public void setData(String data) {
	this.data = data;
}


}
