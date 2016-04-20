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
@Table(name="model_approve")
public class ModelApprove extends BaseEntity {
	@Id
	@SequenceGenerator(name="Any",sequenceName="model_approve_seq")
	private	Long	id;			//﻿编号
	private	String	approveUser;			//审批人
	private	Integer	status;			//状态
	private	String	cause;			//意见/原因
	private	Date	approveTime;			//审批时间
	private	Long	model;			//所属模型
	private	Date	createDate;			//创建时间
	private	String	updateBy;			//修改人
	private	Date	updateDate;			//修改时间
	private	Integer	srot;			//顺序号
	private	String	delFlag;			//删除标识

	public void setId(Long id){
		this.set("id",id);
	}

	public Long getId(){
		return this.getLong("id");
	}

	public void setApproveUser(String approveUser){
		this.set("approveUser",approveUser);
	}

	public String getApproveUser(){
		return this.getString("approveUser");
	}

	public void setStatus(Integer status){
		this.set("status",status);
	}

	public Integer getStatus(){
		return this.getInteger("status");
	}

	public void setCause(String cause){
		this.set("cause",cause);
	}

	public String getCause(){
		return this.getString("cause");
	}

	public void setApproveTime(Date approveTime){
		this.set("approveTime",approveTime);
	}

	public Date getApproveTime(){
		return this.getDate("approveTime");
	}

	public void setModel(Long model){
		this.set("model",model);
	}

	public Long getModel(){
		return this.getLong("model");
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

	public void setSrot(Integer srot){
		this.set("srot",srot);
	}

	public Integer getSrot(){
		return this.getInteger("srot");
	}

	public void setDelFlag(String delFlag){
		this.set("delFlag",delFlag);
	}

	public String getDelFlag(){
		return this.getString("delFlag");
	}

}