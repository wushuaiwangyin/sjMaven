package com.krm.dbaudit.web.verify.model;

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
@Table(name="subject_table")
public class SubjectTable extends BaseEntity {
	
	@Id
	@SequenceGenerator(name="Any",sequenceName="subject_table_seq")
	private	Long	id;			//编号
	private	Long	theme;			//主题集
	private	String	tableName;			//表名
	private	String	createBy;			//创建人
	private	Date	createDate;			//创建时间
	private	String	updateBy;			//修改人
	private	Date	updateDate;			//修改时间
	private	String	delFlag;			//删除标识

	public void setId(Long id){
		this.set("id",id);
	}

	public Long getId(){
		return this.getLong("id");
	}

	public void setTheme(Long theme){
		this.set("theme",theme);
	}

	public Long getTheme(){
		return this.getLong("theme");
	}

	public void setTableName(String tableName){
		this.set("tableName",tableName);
	}

	public String getTableName(){
		return this.getString("tableName");
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

}