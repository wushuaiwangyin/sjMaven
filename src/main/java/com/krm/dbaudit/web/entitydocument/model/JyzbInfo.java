package com.krm.dbaudit.web.entitydocument.model;

import java.io.Serializable;

/**经营指标情况
 * @author cat
 *
 */
public class JyzbInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9064151110372807853L;

	/**
	 * 序号
	 */
	private long id;
	/**
	 * 项目
	 */
	private String proName;
	
	/**
	 * 一月
	 */
	private String january;
	/**
	 * 二月
	 */
	private String february;
	/**
	 * 三月
	 */
	private String march;
	/**
	 * 四月
	 */
	private String april;
	/**
	 * 五月
	 */
	private String may;
	/**
	 * 六月
	 */
	private String june;
	/**
	 * 七月
	 */
	private String july;
	/**
	 * 八月
	 */
	private String aujust;
	/**
	 * 九月
	 */
	private String september;
	/**
	 * 十月
	 */
	private String october;
	/**
	 * 十一月
	 */
	private String november;
	/**
	 * 十二月
	 */
	private String december;
	
	/**
	 * 数据日期
	 */
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
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getJanuary() {
		return january;
	}
	public void setJanuary(String january) {
		this.january = january;
	}
	public String getFebruary() {
		return february;
	}
	public void setFebruary(String february) {
		this.february = february;
	}
	public String getMarch() {
		return march;
	}
	public void setMarch(String march) {
		this.march = march;
	}
	public String getApril() {
		return april;
	}
	public void setApril(String april) {
		this.april = april;
	}
	public String getMay() {
		return may;
	}
	public void setMay(String may) {
		this.may = may;
	}
	public String getJune() {
		return june;
	}
	public void setJune(String june) {
		this.june = june;
	}
	public String getJuly() {
		return july;
	}
	public void setJuly(String july) {
		this.july = july;
	}
	public String getAujust() {
		return aujust;
	}
	public void setAujust(String aujust) {
		this.aujust = aujust;
	}
	public String getSeptember() {
		return september;
	}
	public void setSeptember(String september) {
		this.september = september;
	}
	public String getOctober() {
		return october;
	}
	public void setOctober(String october) {
		this.october = october;
	}
	public String getNovember() {
		return november;
	}
	public void setNovember(String november) {
		this.november = november;
	}
	public String getDecember() {
		return december;
	}
	public void setDecember(String december) {
		this.december = december;
	}
	
	
	
}
