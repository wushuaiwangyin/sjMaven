package com.krm.dbaudit.web.modeltool.model;

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
* @author chenwei on 2015-11-13
*/
@SuppressWarnings({ "unused"})
@Table(name="tool_def_flow")
public class ToolFlow  extends BaseEntity {
	Long id;
	String flowName;
	Long parentFlowId;
	String procScript;
	String targetTable;
	String sourceTable1;
	String sourceTable2;
	String procMode;
	String webAction;
	String sourceTable1Name;
	String sourceTable2Name;
	
	public Long getId() {
		return this.getLong("id");
	}
	public void setId(Long id) {
		this.set("id",id);
	}
	public String getFlowName() {
		return this.getString("flowName");
	}
	public void setFlowName(String flowName) {
		this.set("flowName",flowName);
	}
	public Long getParentFlowId() {
		return this.getLong("parentFlowId");
	}
	public void setParentFlowId(Long parentFlowId) {
		this.set("parentFlowId",parentFlowId);
	}
	public String getProcScript() {
		return this.getString("procScript");
	}
	public void setProcScript(String procScript) {
		this.set("procScript",procScript);
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
	public String getWebAction() {
		return this.getString("webAction");
	}
	public void setWebAction(String webAction) {
		this.set("webAction",webAction);
	}
	public String getSourceTable1Name() {
		return this.getString("sourceTable1Name");
	}
	public void setSourceTable1Name(String sourceTable1Name) {
		this.set("sourceTable1Name",sourceTable1Name);
	}
	public String getSourceTable2Name() {
		return this.getString("sourceTable2Name");
	}
	public void setSourceTable2Name(String sourceTable2Name) {
		this.set("sourceTable2Name",sourceTable2Name);
	}
}
