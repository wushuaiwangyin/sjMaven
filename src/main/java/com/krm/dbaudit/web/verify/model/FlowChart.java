package com.krm.dbaudit.web.verify.model;

import java.util.Date;
import java.util.List;

import com.krm.dbaudit.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Table;

/**
* @author taosq on 2015-08-13
*/
@SuppressWarnings({ "unused"})
@Table(name="flow_chart")
public class FlowChart extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="Any",sequenceName="flow_chart_seq")
	private	Long	id;			//编号
	private	String	name;			//名称
	private	String	description;			//描述
	private	String	xml;			//XML
	private	Long	type;			//所属分类
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

	public void setDescription(String description){
		this.set("description",description);
	}

	public String getDescription(){
		return this.getString("description");
	}

	public void setXml(String xml){
		this.set("xml",xml);
	}

	public String getXml(){
		return this.getString("xml");
	}

	public void setType(Long type){
		this.set("type",type);
	}

	public Long getType(){
		return this.getLong("type");
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