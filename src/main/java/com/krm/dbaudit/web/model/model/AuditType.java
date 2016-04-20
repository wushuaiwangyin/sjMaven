package com.krm.dbaudit.web.model.model;

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
@Table(name="audit_type")
public class AuditType extends BaseEntity {
	@Id
	@SequenceGenerator(name="Any",sequenceName="audit_type_seq")
	private	Long	id;			//编号
	private	String	name;			//名称
	private	Long	sort;			//序号
	private	Long	parentId;			//上一级分类
	private	String	description;			//描述
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

	public void setName(String name){
		this.set("name",name);
	}

	public String getName(){
		return this.getString("name");
	}

	public void setSort(Long sort){
		this.set("sort",sort);
	}

	public Long getSort(){
		return this.getLong("sort");
	}

	public void setParentId(Long parentId){
		this.set("parentId",parentId);
	}

	public Long getParentId(){
		return this.getLong("parentId");
	}

	public void setDescription(String description){
		this.set("description",description);
	}

	public String getDescription(){
		return this.getString("description");
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