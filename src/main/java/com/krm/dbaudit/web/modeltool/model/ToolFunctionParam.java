package com.krm.dbaudit.web.modeltool.model;

import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

@SuppressWarnings({ "unused"})
@Table(name="tool_def_function_params")
public class ToolFunctionParam extends BaseEntity{
	Long id;
	Long funId;
	String paramName;
	Long paramType;
	Long paramIndex;
	String paramMemo;
	public Long getId() {
		return this.getLong("id");
	}
	public void setId(Long id) {
		this.set("id",id);
	}
	public Long getFunId() {
		return this.getLong("funId");
	}
	public void setFunId(Long funId) {
		this.set("funId",funId);
	}
	public String getParamName() {
		return this.getString("paramName");
	}
	public void setParamName(String paramName) {
		this.set("paramName",paramName);
	}
	public Long getParamType() {
		return this.getLong("paramType");
	}
	public void setParamType(Long paramType) {
		this.set("paramType",paramType);
	}
	public Long getParamIndex() {
		return this.getLong("paramIndex");
	}
	public void setParamIndex(Long paramIndex) {
		this.set("paramIndex",paramIndex);
	}
	public String getParamMemo() {
		return this.getString("paramMemo");
	}
	public void setParamMemo(String paramMemo) {
		this.set("paramMemo",paramMemo);
	}
}
