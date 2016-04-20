package com.krm.dbaudit.web.model.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

@SuppressWarnings({ "unused"})
@Table(name="model_def_field")
public class ModelFieldDef extends BaseEntity{
	@Id
	@SequenceGenerator(name="Any",sequenceName="model_def_field_seq")
	
	private Long id;
	private Long modelId;
	private String fieldAlias;
	private Long fieldColumn;
	private Long fieldOrder;
	private Long status;
	private Long fieldType;
	private String fieldLength;
	private String fieldDictype;
	private Long srcFieldId;
	
	public Long getId() {
		return this.getLong("id");
	}
	public void setId(Long id) {
		this.set("id",id);
	}
	public Long getModelId() {
		return this.getLong("modelId");
	}
	public void setModelId(Long modelId) {
		this.set("modelId",modelId);
	}
	public String getFieldAlias() {
		return this.getString("fieldAlias");
	}
	public void setFieldAlias(String fieldAlias) {
		this.set("fieldAlias",fieldAlias);
	}
	public Long getFieldColumn() {
		return this.getLong("fieldColumn");
	}
	public void setFieldColumn(Long fieldColumn) {
		this.set("fieldColumn",fieldColumn);
	}
	public Long getFieldOrder() {
		return this.getLong("fieldOrder");
	}
	public void setFieldOrder(Long fieldOrder) {
		this.set("fieldOrder",fieldOrder);
	}
	public Long getStatus() {
		return this.getLong("status");
	}
	public void setStatus(Long status) {
		this.set("status",status);
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
	public String getFieldDictype() {
		return this.getString("fieldDictype");
	}
	public void setFieldDictype(String fieldDictype) {
		this.set("fieldDictype",fieldDictype);
	}
	public Long getSrcFieldId() {
		return this.getLong("srcFieldId");
	}
	public void setSrcFieldId(Long srcFieldId) {
		this.set("srcFieldId",srcFieldId);
	}
}
