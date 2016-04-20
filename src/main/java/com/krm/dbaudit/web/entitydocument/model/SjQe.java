package com.krm.dbaudit.web.entitydocument.model;

import java.io.Serializable;

/**
 * @author cat
 *历年审计过程中发现的问题实体类
 */
public class SjQe  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9174518088029414529L;
	
	/**
	 * 序号
	 */
	private long id;
	/**
	 * 检查日期
	 */
	private String jcDate;
	
	/**
	 * 问题名称
	 */
	private String qe;
	/**
	 * 业务条线
	 */
	private String ywtx;
	/**
	 * 问题描述
	 */
	private String qems;
	/**
	 * 预计后果
	 */
	private String result;
	/**
	 * 风险分类
	 */
	private String fxfl;
	/**
	 * 风险详情
	 */
	private String flxq;
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
	public String getJcDate() {
		return jcDate;
	}
	public void setJcDate(String jcDate) {
		this.jcDate = jcDate;
	}
	public String getQe() {
		return qe;
	}
	public void setQe(String qe) {
		this.qe = qe;
	}
	public String getYwtx() {
		return ywtx;
	}
	public void setYwtx(String ywtx) {
		this.ywtx = ywtx;
	}
	public String getQems() {
		return qems;
	}
	public void setQems(String qems) {
		this.qems = qems;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFxfl() {
		return fxfl;
	}
	public void setFxfl(String fxfl) {
		this.fxfl = fxfl;
	}
	public String getFlxq() {
		return flxq;
	}
	public void setFlxq(String flxq) {
		this.flxq = flxq;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
