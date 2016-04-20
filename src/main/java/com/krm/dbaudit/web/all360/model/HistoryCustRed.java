package com.krm.dbaudit.web.all360.model;


import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;
/**
 * 查询历史
 */
@Table(name="SYS360_HS_CUST")
public class HistoryCustRed extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3199469984366380130L;
private long pkid;
private String custId;
private String hsDate;
private String isPublic;
public String getIsPublic() {
	return isPublic;
}
public void setIsPublic(String isPublic) {
	this.isPublic = isPublic;
}
public String getHsDate() {
	return hsDate;
}
public void setHsDate(String hsDate) {
	this.hsDate = hsDate;
}
private  String custName;
public String getCustName() {
	return custName;
}
public void setCustName(String custName) {
	this.custName = custName;
}
public long getPkid() {
	return pkid;
}
public void setPkid(long pkid) {
	this.pkid = pkid;
}
public String getCustId() {
	return custId;
}
public void setCustId(String custId) {
	this.custId = custId;
}


}
