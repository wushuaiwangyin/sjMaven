package com.krm.dbaudit.web.model.model;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.persistence.Id;
import com.krm.dbaudit.common.base.BaseEntity;

@SuppressWarnings({ "unused"})
@Table(name="MODEL_DEF_FIELD")
public class ModelFieldRelation extends BaseEntity{
	private String fieldColumn;
	private Long fieldType;
	private String tableName;
	private String fieldName;
	
	public String getFieldColumn() {
		return this.getString("fieldColumn");
	}
	public void setFieldColumn(String fieldColumn) {
		this.set("fieldColumn",fieldColumn);
	}
	public Long getFieldType() {
		return this.getLong("fieldType");
	}
	public void setFieldType(Long fieldType) {
		this.set("fieldType",fieldType);
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
}
