package com.krm.dbaudit.web.config.model;

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
* @author taosq on 2015-08-13
*/
@SuppressWarnings({ "unused"})
@Table(name="column_def")
public class ColumnDef extends BaseEntity {
	@SequenceGenerator(name="Any",sequenceName="column_def_seq")
	private	Long	id;			//编号
	private	Long	tableId;			//表编号
	private	Long	tableName;			//表名
	private	String	column;			//列名
	private	String	znName;			//中文名
	private	String	createBy;			//创建人
	private	Date	createDate;			//创建时间
	private	String	updateBy;			//修改人
	private	Date	updateDate;			//修改时间
	private	String	delFlag;			//删除标识
	private	String	type;			//类型
	private	Integer	isQuery;			//是否用于查询
	private	Integer	isList;			//是否展示
	private	Integer	valueType;			//值类型
	private	String	valueRef;			//值/码表

	public void setId(Long id){
		this.set("id",id);
	}

	public Long getId(){
		return this.getLong("id");
	}

	public void setTableId(Long tableId){
		this.set("tableId",tableId);
	}

	public Long getTableId(){
		return this.getLong("tableId");
	}

	public void setTableName(Long tableName){
		this.set("tableName",tableName);
	}

	public Long getTableName(){
		return this.getLong("tableName");
	}

	public void setColumn(String column){
		this.set("column",column);
	}

	public String getColumn(){
		return this.getString("column");
	}

	public void setZnName(String znName){
		this.set("znName",znName);
	}

	public String getZnName(){
		return this.getString("znName");
	}

	public void setCreateBy(String createBy){
		this.set("createBy",createBy);
	}

	public String getCreateBy(){
		return this.getString("createBy");
	}

	public void setCreateDate(Date createDate){
		this.set("createDate",createDate);
	}

	public Date getCreateDate(){
		return this.getDate("createDate");
	}

	public void setUpdateBy(String updateBy){
		this.set("updateBy",updateBy);
	}

	public String getUpdateBy(){
		return this.getString("updateBy");
	}

	public void setUpdateDate(Date updateDate){
		this.set("updateDate",updateDate);
	}

	public Date getUpdateDate(){
		return this.getDate("updateDate");
	}

	public void setDelFlag(String delFlag){
		this.set("delFlag",delFlag);
	}

	public String getDelFlag(){
		return this.getString("delFlag");
	}

	public void setType(String type){
		this.set("type",type);
	}

	public String getType(){
		return this.getString("type");
	}

	public void setIsQuery(Integer isQuery){
		this.set("isQuery",isQuery);
	}

	public Integer getIsQuery(){
		return this.getInteger("isQuery");
	}

	public void setIsList(Integer isList){
		this.set("isList",isList);
	}

	public Integer getIsList(){
		return this.getInteger("isList");
	}

	public void setValueType(Integer valueType){
		this.set("valueType",valueType);
	}

	public Integer getValueType(){
		return this.getInteger("valueType");
	}

	public void setValueRef(String valueRef){
		this.set("valueRef",valueRef);
	}

	public String getValueRef(){
		return this.getString("valueRef");
	}

}