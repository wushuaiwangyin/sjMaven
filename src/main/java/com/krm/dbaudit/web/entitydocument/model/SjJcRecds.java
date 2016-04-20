package com.krm.dbaudit.web.entitydocument.model;

import java.io.Serializable;

/**
 * @author cat
 *审计检查记录实体类
 */
public class SjJcRecds implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8829692862692148203L;
/**
 * 序号
 */
private long id;
/**
 * 审计时间
 */
private String sjDate;

/**
 * 审计类型
 *  */
private String sjType;

/**
 * 项目名称
 */
private String xmmc;

/**
 * 主审人
 */
private String zsr;
/**
 * 项目编号
 */
private String xmbh;
/**
 * 备注
 */
private String remark;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getSjDate() {
	return sjDate;
}
public void setSjDate(String sjDate) {
	this.sjDate = sjDate;
}
public String getSjType() {
	return sjType;
}
public void setSjType(String sjType) {
	this.sjType = sjType;
}
public String getXmmc() {
	return xmmc;
}
public void setXmmc(String xmmc) {
	this.xmmc = xmmc;
}
public String getZsr() {
	return zsr;
}
public void setZsr(String zsr) {
	this.zsr = zsr;
}
public String getXmbh() {
	return xmbh;
}
public void setXmbh(String xmbh) {
	this.xmbh = xmbh;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}

}
