package com.krm.dbaudit.web.model.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

@SuppressWarnings({ "unused"})
@Table(name="model_deal_log")
public class ModelDeal  extends BaseEntity{
	@Id
	@SequenceGenerator(name="Any",sequenceName="model_deal_seq")
	
	private	Long	id;			//﻿处理内容编号
	private	long	modelId;	//模型id
	private long	dealAction;	//操作编号
	private	long	modelStatus;	//模型状态
	private String	dealMemo;	//处理内容
	private String	dealBy;	//处理人
	private String	dealDate;	//处理日期
	private String	dealTime;	//处理时间
	
	private String	dealActionName;	//操作
	private String	modelStatusName;	//状态
	
	public Long getId() {
		return this.getLong("id");
	}
	public void setId(Long id) {
		this.set("id",id);
	}
	public long getModelId() {
		return this.getLong("modelId");
	}
	public void setModelId(long modelId) {
		this.set("modelId",modelId);
	}
	public long getDealAction() {
		return this.getLong("dealAction");
	}
	public void setDealAction(long dealAction) {
		this.set("dealAction",dealAction);
	}
	public long getModelStatus() {
		return this.getLong("modelStatus");
	}
	public void setModelStatus(long modelStatus) {
		this.set("modelStatus",modelStatus);
	}
	public String getDealMemo() {
		return this.getString("dealMemo");
	}
	public void setDealMemo(String dealMemo) {
		this.set("dealMemo",dealMemo);
	}
	public String getDealBy() {
		return this.getString("dealBy");
	}
	public void setDealBy(String dealBy) {
		this.set("dealBy",dealBy);
	}
	public String getDealDate() {
		return this.getString("dealDate");
	}
	public void setDealDate(String dealDate) {
		this.set("dealDate",dealDate);
	}
	public String getDealTime() {
		return this.getString("dealTime");
	}
	public void setDealTime(String dealTime) {
		this.set("dealTime",dealTime);
	}
	public String getDealActionName() {
		return this.getString("dealActionName");
	}
	public void setDealActionName(String dealActionName) {
		this.set("dealActionName",dealActionName);
	}
	public String getModelStatusName() {
		return this.getString("modelStatusName");
	}
	public void setModelStatusName(String modelStatusName) {
		this.set("modelStatusName",modelStatusName);
	}
}
