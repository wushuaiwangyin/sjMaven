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
@Table(name="model_step")
public class ModelStep extends BaseEntity {
	@Id
	@SequenceGenerator(name="Any",sequenceName="model_step_seq")
	private	Long	id;			//﻿步骤编号
	private	String	name;			//步骤名称
	private	String	talbleName;			//中间表名
	private	Integer	status;			//状态
	private	String	step;			//步骤描述
	private	String	sqlCode;			//SQL脚本
	private	Long	model;			//所属模型
	private	String	createBy;			//创建人
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

	public void setName(String name){
		this.set("name",name);
	}

	public String getName(){
		return this.getString("name");
	}

	public void setTalbleName(String talbleName){
		this.set("talbleName",talbleName);
	}

	public String getTalbleName(){
		return this.getString("talbleName");
	}

	public void setStatus(Integer status){
		this.set("status",status);
	}

	public Integer getStatus(){
		return this.getInteger("status");
	}

	public void setStepDesc(String stepDesc){
		this.set("stepDesc",stepDesc);
	}

	public String getStepDesc(){
		return this.getString("stepDesc");
	}

	public void setSqlCode(String sqlCode){
		this.set("sqlCode",sqlCode);
	}

	public String getSqlCode(){
		return this.getString("sqlCode");
	}

	public void setModel(Long model){
		this.set("model",model);
	}

	public Long getModel(){
		return this.getLong("model");
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