package com.krm.dbaudit.web.model.model;

import java.util.Date;
import java.util.List;

import com.krm.dbaudit.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Table;

/**
* @author taosq on 2015-08-13
*/
@SuppressWarnings({ "unused"})
@Table(name="model_base")
public class ModelBase extends BaseEntity {
	@Id
	@SequenceGenerator(name="Any",sequenceName="model_base_seq")
	private	Long	id;			//﻿模型编号
	private	String	name;			//模型名称
	private	Long	property;			//业务属性
	private	String	buzLine;			//业务条线
	private	Long	subject;			//专题
	private	String	riskLevel;			//风险级别
	private	Integer	status;			//状态
	private	String	dbSrc;			//数据源
	private	String	runTime;			//跑批时间
	private	Integer	interval;			//频度
	private	Integer	runWay;			//跑批方式
	private	String	tname;			//结果表名
	private	String	cKey;			//主键字段
	private	String	cOrg;			//机构字段
	private	String	cCustomer;			//客户号字段
	private	String	description;			//模型描述
	private	String	createBy;			//创建人
	private	Date	createDate;			//创建时间
	private	String	updateBy;			//修改人
	private	Date	updateDate;			//修改时间
	private	String	delFlag;			//删除标识
	private	Date	lastRunDate;			//最后跑批时间
	private	Long	lastRunCount;			//最后跑批记录数
	private	Integer	lastRunStatus;			//最后跑批状态
	private String	code;	//模型编号

	public void setId(Long id){
		this.set("id",id);
	}

	public Long getId(){
		return this.getLong("id");
	}

	public void setName(String name){
		this.set("name",name);
	}

	public String getName(){
		return this.getString("name");
	}

	public void setProperty(Long property){
		this.set("property",property);
	}

	public Long getProperty(){
		return this.getLong("property");
	}

	public void setBuzLine(String buzLine){
		this.set("buzLine",buzLine);
	}

	public String getBuzLine(){
		return this.getString("buzLine");
	}

	public void setSubject(Long subject){
		this.set("subject",subject);
	}

	public Long getSubject(){
		return this.getLong("subject");
	}

	public void setRiskLevel(String riskLevel){
		this.set("riskLevel",riskLevel);
	}

	public String getRiskLevel(){
		return this.getString("riskLevel");
	}

	public void setStatus(Integer status){
		this.set("status",status);
	}

	public Integer getStatus(){
		return this.getInteger("status");
	}

	public void setDbSrc(String dbSrc){
		this.set("dbSrc",dbSrc);
	}

	public String getDbSrc(){
		return this.getString("dbSrc");
	}

	public void setRunTime(String runTime){
		this.set("runTime",runTime);
	}

	public String getRunTime(){
		return this.getString("runTime");
	}

	public void setInterval(Integer interval){
		this.set("interval",interval);
	}

	public Integer getInterval(){
		return this.getInteger("interval");
	}

	public void setRunWay(Integer runWay){
		this.set("runWay",runWay);
	}

	public Integer getRunWay(){
		return this.getInteger("runWay");
	}

	public void setTname(String tname){
		this.set("tname",tname);
	}

	public String getTname(){
		return this.getString("tname");
	}

	public void setCKey(String cKey){
		this.set("cKey",cKey);
	}

	public String getCKey(){
		return this.getString("cKey");
	}

	public void setCOrg(String cOrg){
		this.set("cOrg",cOrg);
	}

	public String getCOrg(){
		return this.getString("cOrg");
	}

	public void setCCustomer(String cCustomer){
		this.set("cCustomer",cCustomer);
	}

	public String getCCustomer(){
		return this.getString("cCustomer");
	}

	public void setDescription(String description){
		this.set("description",description);
	}

	public String getDescription(){
		return this.getString("description");
	}

	public void setCreateBy(String createBy){
		this.set("createBy",createBy);
	}

	public String getCreateBy(){
		return this.getString("createBy");
	}

	public void setCreateDate(Date createDate){
		this.set("createDate",createDate);
	}

	public Date getCreateDate(){
		return this.getDate("createDate");
	}

	public void setUpdateBy(String updateBy){
		this.set("updateBy",updateBy);
	}

	public String getUpdateBy(){
		return this.getString("updateBy");
	}

	public void setUpdateDate(Date updateDate){
		this.set("updateDate",updateDate);
	}

	public Date getUpdateDate(){
		return this.getDate("updateDate");
	}

	public void setDelFlag(String delFlag){
		this.set("delFlag",delFlag);
	}

	public String getDelFlag(){
		return this.getString("delFlag");
	}

	public void setLastRunDate(Date lastRunDate){
		this.set("lastRunDate",lastRunDate);
	}

	public Date getLastRunDate(){
		return this.getDate("lastRunDate");
	}

	public void setLastRunCount(Long lastRunCount){
		this.set("lastRunCount",lastRunCount);
	}

	public Long getLastRunCount(){
		return this.getLong("lastRunCount");
	}

	public void setLastRunStatus(Integer lastRunStatus){
		this.set("lastRunStatus",lastRunStatus);
	}

	public Integer getLastRunStatus(){
		return this.getInteger("lastRunStatus");
	}

	public String getCode() {
		return this.getString("code");
	}

	public void setCode(String code) {
		this.set("code",code);
	}

}