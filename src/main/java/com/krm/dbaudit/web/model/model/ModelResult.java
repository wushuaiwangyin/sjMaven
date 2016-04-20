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
@Table(name="model_result")
public class ModelResult extends BaseEntity {
	@Id
	@SequenceGenerator(name="Any",sequenceName="model_result_seq")
	private	Long	id;			//编号
	private	Long	model;			//模型编号
	private	Date	startTime;			//运行时间
	private	Date	endTime;			//关联主键
	private	String	cid;			//客户号
	private	String	org;			//机构
	private	Integer	dealWay;			//处理方式
	private	Integer	dealStatus;			//处理状态
	private	Date	dealDate;			//处理时间
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

	public void setModel(Long model){
		this.set("model",model);
	}

	public Long getModel(){
		return this.getLong("model");
	}

	public void setStartTime(Date startTime){
		this.set("startTime",startTime);
	}

	public Date getStartTime(){
		return this.getDate("startTime");
	}

	public void setEndTime(Date endTime){
		this.set("endTime",endTime);
	}

	public Date getEndTime(){
		return this.getDate("endTime");
	}

	public void setCid(String cid){
		this.set("cid",cid);
	}

	public String getCid(){
		return this.getString("cid");
	}

	public void setOrg(String org){
		this.set("org",org);
	}

	public String getOrg(){
		return this.getString("org");
	}

	public void setDealWay(Integer dealWay){
		this.set("dealWay",dealWay);
	}

	public Integer getDealWay(){
		return this.getInteger("dealWay");
	}

	public void setDealStatus(Integer dealStatus){
		this.set("dealStatus",dealStatus);
	}

	public Integer getDealStatus(){
		return this.getInteger("dealStatus");
	}

	public void setDealDate(Date dealDate){
		this.set("dealDate",dealDate);
	}

	public Date getDealDate(){
		return this.getDate("dealDate");
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