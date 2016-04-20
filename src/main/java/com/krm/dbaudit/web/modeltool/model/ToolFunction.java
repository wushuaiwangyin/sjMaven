package com.krm.dbaudit.web.modeltool.model;

import javax.persistence.Table;
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
@Table(name="tool_def_function")
public class ToolFunction extends BaseEntity{
	Long id;
	Long type;
	Long parentId;
	String funName;
	String funScript;
	String funMemo;
	String runScript;
	public Long getId() {
		return this.getLong("id");
	}
	public void setId(Long id) {
		this.set("id",id);
	}
	public Long getType() {
		return this.getLong("type");
	}
	public void setType(Long type) {
		this.set("type",type);
	}
	public Long getParentId() {
		return this.getLong("parentId");
	}
	public void setParentId(Long parentId) {
		this.set("parentId",parentId);
	}
	public String getFunName() {
		return this.getString("funName");
	}
	public void setFunName(String funName) {
		this.set("funName",funName);
	}
	public String getFunScript() {
		return this.getString("funScript");
	}
	public void setFunScript(String funScript) {
		this.set("funScript",funScript);
	}
	public String getFunMemo() {
		return this.getString("funMemo");
	}
	public void setFunMemo(String funMemo) {
		this.set("funMemo",funMemo);
	}
	public String getRunScript() {
		return this.getString("runScript");
	}
	public void setRunScript(String runScript) {
		this.set("runScript",runScript);
	}
}
