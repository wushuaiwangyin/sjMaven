package com.krm.dbaudit.web.entitydocument.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author cat
 *
 */
public class RowInfo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -676485570635532825L;
/**
 * 数据库物理行序号
 */
private long id;
private String dataDate;
private List<CellInfo> cList;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getDataDate() {
	return dataDate;
}
public void setDataDate(String dataDate) {
	this.dataDate = dataDate;
}
public List<CellInfo> getcList() {
	return cList;
}
public void setcList(List<CellInfo> cList) {
	this.cList = cList;
}

}
