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
@Table(name="gold_flow")
public class GoldFlow extends BaseEntity {
	
	@Id
	@SequenceGenerator(name="Any",sequenceName="gold_flow_seq")
	private	Long	id;			//编号
	private	String	cid;			//客户编号
	private	String	cname;			//客户名称
	private	String	card;			//卡号
	private	Long	amount;			//金额
	private	String	item;			//操作项
	private	String	oid;			//对方编号
	private	String	oname;			//对方名称
	private	String	ocard;			//对方卡号
	private	Date	operateTime;			//操作时间

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

	public void setCard(String card){
		this.set("card",card);
	}

	public String getCard(){
		return this.getString("card");
	}

	public void setAmount(Long amount){
		this.set("amount",amount);
	}

	public Long getAmount(){
		return this.getLong("amount");
	}

	public void setItem(String item){
		this.set("item",item);
	}

	public String getItem(){
		return this.getString("item");
	}

	public void setOid(String oid){
		this.set("oid",oid);
	}

	public String getOid(){
		return this.getString("oid");
	}

	public void setOname(String oname){
		this.set("oname",oname);
	}

	public String getOname(){
		return this.getString("oname");
	}

	public void setOcard(String ocard){
		this.set("ocard",ocard);
	}

	public String getOcard(){
		return this.getString("ocard");
	}

	public void setOperateTime(Date operateTime){
		this.set("operateTime",operateTime);
	}

	public Date getOperateTime(){
		return this.getDate("operateTime");
	}

}