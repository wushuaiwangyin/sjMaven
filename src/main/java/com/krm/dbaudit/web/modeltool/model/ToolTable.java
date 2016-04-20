package com.krm.dbaudit.web.modeltool.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

/**
* @author chenwei on 2015-11-13
*/
@SuppressWarnings({ "unused"})
@Table(name="tool_def_table")
public class ToolTable extends BaseEntity{
	@Id
	@SequenceGenerator(name="Any")
	private String tableName;
	private String tableAlias;
	private String tableParent;
	private Long tableFlag;
	private Long tableTypeId;
	private Long userid;
	private String actionid;
	
	public String getTableName() {
		return this.getString("tableName");
	}
	public void setTableName(String tableName) {
		this.set("tableName",tableName);
	}
	public String getTableAlias() {
		return this.getString("tableAlias");
	}
	public void setTableAlias(String tableAlias) {
		this.set("tableAlias",tableAlias);
	}
	public String getTableParent() {
		return this.getString("tableParent");
	}
	public void setTableParent(String tableParent) {
		this.set("tableParent",tableParent);
	}
	public Long getTableFlag() {
		return this.getLong("tableFlag");
	}
	public void setTableFlag(Long tableFlag) {
		this.set("tableFlag",tableFlag);
	}
	public Long getTableTypeId() {
		return this.getLong("tableTypeId");
	}
	public void setTableTypeId(Long tableTypeId) {
		this.set("tableTypeId",tableTypeId);
	}
	public Long getUserid() {
		return this.getLong("userid");
	}
	public void setUserid(Long userid) {
		this.set("userid",userid);
	}
	public String getActionid() {
		return this.getString("actionid");
	}
	public void setActionid(String actionid) {
		this.set("actionid",actionid);
	}
}
