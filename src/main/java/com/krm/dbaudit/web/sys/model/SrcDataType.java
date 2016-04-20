package com.krm.dbaudit.web.sys.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;
@SuppressWarnings({ "unused"})
@Table(name="srcdata_type")
public class SrcDataType extends BaseEntity {

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
	private Long	typeFlag;			//分类模式，0-系统源表使用；1-用户自定义表，用于分析工具属性结构使用

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

	public Long getTypeFlag() {
		return this.getLong("typeFlag");
	}

	public void setTypeFlag(Long typeFlag) {
		this.set("typeFlag",typeFlag);
	}
}
