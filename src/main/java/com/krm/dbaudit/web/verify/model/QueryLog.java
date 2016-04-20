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
@Table(name="query_log")
public class QueryLog extends BaseEntity {
	
	@Id
	@SequenceGenerator(name="Any",sequenceName="query_log_seq")
	private	Long	id;			//编号
	private	String	cid;			//查询客户编号
	private	String	cname;			//查询客户名称
	private	String	createBy;			//创建人
	private	Date	createDate;			//创建时间
	private	String	updateBy;			//修改人
	private	Date	updateDate;			//修改时间
	private	String	delFlag;			//删除标识
	private	String	keyword;			//关键词

	public void setId(Long id){
		this.set("id",id);
	}

	public Long getId(){
		return this.getLong("id");
	}

	public void setCid(String cid){
		this.set("cid",cid);
	}

	public String getCid(){
		return this.getString("cid");
	}

	public void setCname(String cname){
		this.set("cname",cname);
	}

	public String getCname(){
		return this.getString("cname");
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

	public void setKeyword(String keyword){
		this.set("keyword",keyword);
	}

	public String getKeyword(){
		return this.getString("keyword");
	}

}