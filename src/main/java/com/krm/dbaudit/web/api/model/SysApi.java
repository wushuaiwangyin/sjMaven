package com.krm.dbaudit.web.api.model;

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
@Table(name="sys_api")
public class SysApi extends BaseEntity {
	@SequenceGenerator(name="Any",sequenceName="sys_api_seq")
	private	Long	id;			//编号
	private	String	name;			//名称
	private	String	url;			//访问地址
	private	String	fnDes;			//功能描述
	private	String	inDes;			//输入描述
	private	String	outFormat;			//输出格式
	private	String	outDes;			//输出描述
	private	String	exapmle;			//调用实例
	private	Long	runCount;			//调用次数
	private	String	type;			//分类
	private	Integer	way;			//接口方式

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

	public void setUrl(String url){
		this.set("url",url);
	}

	public String getUrl(){
		return this.getString("url");
	}

	public void setFnDes(String fnDes){
		this.set("fnDes",fnDes);
	}

	public String getFnDes(){
		return this.getString("fnDes");
	}

	public void setInDes(String inDes){
		this.set("inDes",inDes);
	}

	public String getInDes(){
		return this.getString("inDes");
	}

	public void setOutFormat(String outFormat){
		this.set("outFormat",outFormat);
	}

	public String getOutFormat(){
		return this.getString("outFormat");
	}

	public void setOutDes(String outDes){
		this.set("outDes",outDes);
	}

	public String getOutDes(){
		return this.getString("outDes");
	}

	public void setExapmle(String exapmle){
		this.set("exapmle",exapmle);
	}

	public String getExapmle(){
		return this.getString("exapmle");
	}

	public void setRunCount(Long runCount){
		this.set("runCount",runCount);
	}

	public Long getRunCount(){
		return this.getLong("runCount");
	}

	public void setType(String type){
		this.set("type",type);
	}

	public String getType(){
		return this.getString("type");
	}

	public void setWay(Integer way){
		this.set("way",way);
	}

	public Integer getWay(){
		return this.getInteger("way");
	}

}