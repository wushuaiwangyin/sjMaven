package com.krm.dbaudit.web.entitydocument.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class JyzbDatas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1639962429896938080L;
private String[] titles ;
private String[] cols;
private List<Map<String, String>> dataList;
public String[] getTitles() {
	return titles;
}
public void setTitles(String[] titles) {
	this.titles = titles;
}
public String[] getCols() {
	return cols;
}
public void setCols(String[] cols) {
	this.cols = cols;
}
public List<Map<String, String>> getDataList() {
	return dataList;
}
public void setDataList(List<Map<String, String>> dataList) {
	this.dataList = dataList;
}

}
