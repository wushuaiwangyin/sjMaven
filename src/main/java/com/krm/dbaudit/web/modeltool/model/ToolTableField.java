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
@Table(name="tool_def_tablefield")
public class ToolTableField  extends BaseEntity{
	@Id
	@SequenceGenerator(name="Any",sequenceName="tool_def_tablefield_sql")
	private Long id;
	private String tableName;
	private	String fieldName;
	private String fieldAlias;
	private Long fieldEntityType;
	private Long fieldType;
	private String fieldLength;
	private Long fromFieldid;
	private Long fieldOrder;
	private String fieldScript;
	private String fieldScriptName;
	
	public Long getId() {
		return this.getLong("id");
	}
	public void setId(Long id) {
		this.set("id",id);
	}
	public String getTableName() {
		return this.getString("tableName");
	}
	public void setTableName(String tableName) {
		this.set("tableName",tableName);
	}
	public String getFieldName() {
		return this.getString("fieldName");
	}
	public void setFieldName(String fieldName) {
		this.set("fieldName",fieldName);
	}
	public String getFieldAlias() {
		return this.getString("fieldAlias");
	}
	public void setFieldAlias(String fieldAlias) {
		this.set("fieldAlias",fieldAlias);
	}
	public Long getFieldEntityType() {
		return this.getLong("fieldEntityType");
	}
	public void setFieldEntityType(Long fieldEntityType) {
		this.set("fieldEntityType",fieldEntityType);
	}
	public Long getFieldType() {
		return this.getLong("fieldType");
	}
	public void setFieldType(Long fieldType) {
		this.set("fieldType",fieldType);
	}
	public String getFieldLength() {
		return this.getString("fieldLength");
	}
	public void setFieldLength(String fieldLength) {
		this.set("fieldLength",fieldLength);
	}
	public Long getFromFieldid() {
		return this.getLong("fromFieldid");
	}
	public void setFromFieldid(Long fromFieldid) {
		this.set("fieldAlias",fromFieldid);
	}
	public Long getFieldOrder() {
		return this.getLong("fieldOrder");
	}
	public void setFieldOrder(Long fieldOrder) {
		this.set("fieldOrder",fieldOrder);
	}
	public String getFieldScript() {
		return this.getString("fieldScript");
	}
	public void setFieldScript(String fieldScript) {
		this.set("fieldScript",fieldScript);
	}
	public String getFieldScriptName() {
		return this.getString("fieldScriptName");
	}
	public void setFieldScriptName(String fieldScriptName) {
		this.set("fieldScriptName",fieldScriptName);
	}
}
