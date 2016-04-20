package com.krm.dbaudit.web.model.model;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

/**
* @author chenwei on 2015-11-06
*/
@SuppressWarnings({ "unused"})
@Table(name="model_def_flow")
public class ModelFlow extends BaseEntity{
	@Id
	@SequenceGenerator(name="Any",sequenceName="model_base_seq")
	
	private	Long	id;			//﻿模型编号
	private	long	modelId;	//模型id
	private String	flowName;	//流程名称
	private long	parentFlowId;	//上步流程id
	private String	actionMode;	//流程类型
	private String	targetTable;	//存储表
	private String	sourceTable1;	//源表1
	private String	sourceTable2;	//源表2
	private	String	procMode;		//流程操作细节模式
	
	private String	actionName;	//流程名称
	private String	targetTableName;	//目标表名称
	private String	sourceTableName1;	//源表1名称
	private String	sourceTableName2;	//源表2名称（数据叠加操作，可能为多个）
	
	//流程信息显示用
	private String dataArea;		//数据提取范围
	private String fieldInfo;		//字段信息
	private String conditionInfo;	//条件信息
	private String relationInfo;	//关联信息
	private String sortInfo;		//排序信息
	private String distinctInfo;	//去重信息
	private String virtualfieldInfo;	//虚拟字段信息
	private String firstTable;					//主表
	private String secondaryTable;	//次表(叠加数据为多个)
	private String procModeName;	//合并、排重操作模式
	
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
	public String getFlowName() {
		return this.getString("flowName");
	}
	public void setFlowName(String flowName) {
		this.set("flowName",flowName);
	}
	public long getParentFlowId() {
		return this.getLong("parentFlowId");
	}
	public void setParentFlowId(long parentFlowId) {
		this.set("parentFlowId",parentFlowId);
	}
	public String getActionMode() {
		return this.getString("actionMode");
	}
	public void setActionMode(String actionMode) {
		this.set("actionMode",actionMode);
	}
	public String getTargetTable() {
		return this.getString("targetTable");
	}
	public void setTargetTable(String targetTable) {
		this.set("targetTable",targetTable);
	}
	public String getSourceTable1() {
		return this.getString("sourceTable1");
	}
	public void setSourceTable1(String sourceTable1) {
		this.set("sourceTable1",sourceTable1);
	}
	public String getSourceTable2() {
		return this.getString("sourceTable2");
	}
	public void setSourceTable2(String sourceTable2) {
		this.set("sourceTable2",sourceTable2);
	}
	public String getProcMode() {
		return this.getString("procMode");
	}
	public void setProcMode(String procMode) {
		this.set("procMode",procMode);
	}
	public String getTargetTableName() {
		return this.getString("targetTableName");
	}
	public void setTargetTableName(String targetTableName) {
		this.set("targetTableName",targetTableName);
	}
	public String getSourceTableName1() {
		return this.getString("sourceTableName1");
	}
	public void setSourceTableName1(String sourceTableName1) {
		this.set("sourceTableName1",sourceTableName1);
	}
	public String getSourceTableName2() {
		return this.getString("sourceTableName2");
	}
	public void setSourceTableName2(String sourceTableName2) {
		this.set("sourceTableName2",sourceTableName2);
	}
	public String getFieldInfo() {
		return this.getString("fieldInfo");
	}
	public void setFieldInfo(String fieldInfo) {
		this.set("fieldInfo",fieldInfo);
	}
	public String getConditionInfo() {
		return this.getString("conditionInfo");
	}
	public void setConditionInfo(String conditionInfo) {
		this.set("conditionInfo",conditionInfo);
	}
	public String getRelationInfo() {
		return this.getString("relationInfo");
	}
	public void setRelationInfo(String relationInfo) {
		this.set("relationInfo",relationInfo);
	}
	public String getSortInfo() {
		return this.getString("sortInfo");
	}
	public void setSortInfo(String sortInfo) {
		this.set("sortInfo",sortInfo);
	}
	public String getDistinctInfo() {
		return this.getString("distinctInfo");
	}
	public void setDistinctInfo(String distinctInfo) {
		this.set("distinctInfo",distinctInfo);
	}
	public String getFirstTable() {
		return this.getString("firstTable");
	}
	public void setFirstTable(String firstTable) {
		this.set("firstTable",firstTable);
	}
	public String getSecondaryTable() {
		return this.getString("secondaryTable");
	}
	public void setSecondaryTable(String secondaryTable) {
		this.set("secondaryTable",secondaryTable);
	}
	public String getDataArea() {
		return this.getString("dataArea");
	}
	public void setDataArea(String dataArea) {
		this.set("dataArea",dataArea);
	}
	public String getProcModeName() {
		return this.getString("procModeName");
	}
	public void setProcModeName(String procModeName) {
		this.set("procModeName",procModeName);
	}
	public String getVirtualfieldInfo() {
		return this.getString("virtualfieldInfo");
	}
	public void setVirtualfieldInfo(String virtualfieldInfo) {
		this.set("virtualfieldInfo",virtualfieldInfo);
	}
}
