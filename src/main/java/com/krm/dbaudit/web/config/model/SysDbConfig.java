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
@Table(name="sys_db_config")
public class SysDbConfig extends BaseEntity {
	@SequenceGenerator(name="Any",sequenceName="sys_db_config_seq")
	private	Long	id;			//编号
	private	String	name;			//名称
	private	String	description;			//描述
	private	String	host;			//主机
	private	Long	port;			//端口号
	private	String	dbname;			//实例名称
	private	Integer	type;			//类型
	private	String	username;			//用户名
	private	String	pwd;			//密码
	private	String	url;			//驱动访问地址
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

	public void setHost(String host){
		this.set("host",host);
	}

	public String getHost(){
		return this.getString("host");
	}

	public void setPort(Long port){
		this.set("port",port);
	}

	public Long getPort(){
		return this.getLong("port");
	}

	public void setDbname(String dbname){
		this.set("dbname",dbname);
	}

	public String getDbname(){
		return this.getString("dbname");
	}

	public void setType(Integer type){
		this.set("type",type);
	}

	public Integer getType(){
		return this.getInteger("type");
	}

	public void setUsername(String username){
		this.set("username",username);
	}

	public String getUsername(){
		return this.getString("username");
	}

	public void setPwd(String pwd){
		this.set("pwd",pwd);
	}

	public String getPwd(){
		return this.getString("pwd");
	}

	public void setUrl(String url){
		this.set("url",url);
	}

	public String getUrl(){
		return this.getString("url");
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