package com.krm.dbaudit.web.entitydocument.model;

import java.io.Serializable;

public class LinkManInfo implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -6721147531576244977L;
/**
 * 序号（主键）
 */
private long id;
/**
 * 项目/联系人
 */
private String titleId;

/**
 * 姓名
 */
private String name;
/**
 * 任职期
 */
private String rzq;
/**
 * 部门
 */
private String apart;
/**
 * 移动电话
 */
private String moveTellNo;
/**
 * 固定电话
 */
private String perTellNo;
/**
 * 备注
 */
private String remark;
public String getTitleId() {
	return titleId;
}
public void setTitleId(String titleId) {
	this.titleId = titleId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getRzq() {
	return rzq;
}
public void setRzq(String rzq) {
	this.rzq = rzq;
}
public String getApart() {
	return apart;
}
public void setApart(String apart) {
	this.apart = apart;
}
public String getMoveTellNo() {
	return moveTellNo;
}
public void setMoveTellNo(String moveTellNo) {
	this.moveTellNo = moveTellNo;
}
public String getPerTellNo() {
	return perTellNo;
}
public void setPerTellNo(String perTellNo) {
	this.perTellNo = perTellNo;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}

}
