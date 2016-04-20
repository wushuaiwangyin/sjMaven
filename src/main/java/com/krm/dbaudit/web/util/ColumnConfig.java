package com.krm.dbaudit.web.util;

import javax.persistence.Table;

@SuppressWarnings({ "unused"})
@Table(name="doc_cconfig")
public class ColumnConfig{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String col;
	private String cnName;
	private Long type;
	private String colType;
	private String remark;
	private String sysDef;
	private String validate;
	private String dic;
	private Boolean must;
	private Boolean isQuery;
	private Boolean isList;
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSysDef() {
		return sysDef;
	}
	public void setSysDef(String sysDef) {
		this.sysDef = sysDef;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getDic() {
		return dic;
	}
	public void setDic(String dic) {
		this.dic = dic;
	}
	public Boolean getMust() {
		return must;
	}
	public void setMust(Boolean must) {
		this.must = must;
	}
	public Boolean getIsQuery() {
		return isQuery;
	}
	public void setIsQuery(Boolean isQuery) {
		this.isQuery = isQuery;
	}
	public Boolean getIsList() {
		return isList;
	}
	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	
    
}
