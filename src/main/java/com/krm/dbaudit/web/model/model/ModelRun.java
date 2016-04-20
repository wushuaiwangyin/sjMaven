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
@Table(name="model_run_log")
public class ModelRun extends BaseEntity {
	@Id
	@SequenceGenerator(name="Any",sequenceName="model_run_log_seq")
	private	Long	id;			//编号
	private	Long	modelId;			//模型编号
	private	Date	runDate;			//开始时间
	private	Long	runTime;			//结束时间
	private	Integer	runStatus;			//状态
	private String  createBy;		//运行账号
	private Long	runResultCount;	//结果数据数量
	
	private String runyearstr;	//运行时间
	private String runtimestr;	//运行时间

	public void setId(Long id){
		this.set("id",id);
	}

	public Long getId(){
		return this.getLong("id");
	}

	public Long getModelId() {
		return this.getLong("modelId");
	}

	public void setModelId(Long modelId) {
		this.set("modelId",modelId);
	}

	public Date getRunDate() {
		return this.getDate("runDate");
	}

	public void setRunDate(Date runDate) {
		this.set("runDate",runDate);
	}

	public Long getRunTime() {
		return this.getLong("runTime");
	}

	public void setRunTime(Long runTime) {
		this.set("runTime",runTime);
	}

	public Integer getRunStatus() {
		return this.getInteger("runStatus");
	}

	public void setRunStatus(Integer runStatus) {
		this.set("runStatus",runStatus);
	}

	public String getCreateBy() {
		return this.getString("createBy");
	}

	public void setCreateBy(String createBy) {
		this.set("createBy",createBy);
	}

	public String getRunyearstr() {
		return this.getString("runyearstr");
	}

	public void setRunyearstr(String runyearstr) {
		this.set("runyearstr",runyearstr);
	}

	public String getRuntimestr() {
		return this.getString("runtimestr");
	}

	public void setRuntimestr(String runtimestr) {
		this.set("runtimestr",runtimestr);
	}

	public Long getRunResultCount() {
		return this.getLong("runResultCount");
	}

	public void setRunResultCount(Long runResultCount) {
		this.set("runResultCount",runResultCount);
	}
}