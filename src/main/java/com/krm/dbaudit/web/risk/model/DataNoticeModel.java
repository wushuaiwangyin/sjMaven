package com.krm.dbaudit.web.risk.model;


import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

@SuppressWarnings({ "unused"})
@Table(name="model_data_notice")
public class DataNoticeModel extends BaseEntity
{
	private static final long serialVersionUID = 1210981277262492052L;
	@Id
	@SequenceGenerator(name="Any",sequenceName="model_data_notice_seq")
	private Long id;				//主键
	private Integer dataId;			//数据id
	private Integer modelId;		//模型id
	private Long docId;				//附件id
	private String dealStatus;		//数据处理状态
	private String noticeStatus;	//通知书状态
	private String noticeOrganId;		//通知机构id（下发机构）
	private String noticeOrganName;		//通知机构名称（下发机构）
	private String bnoticeOrganId;		//交易机构id
	private String bnoticeOrganName;		//交易机构名称
	private String noticeStartTime;		//通知书下发时间
	private String noticeColseTime;		//通知书截止时间
	private String noticeEndTime;		//通知书完成时间
	private String noticeDealRequire;		//通知书处理要求-省级要求
	private String noticeDealRequire2;		//通知书处理要求-办事处要求2
	private String tranDate;		//预警时间
	private String noticeSender;		//通知书下发者
	private String noticeDealer;		//通知书处理者--办事处
	private String noticeSender2;		//通知书下发者
	private String noticeDealer2;		//通知书处理者--支行分社
	private String noticeOrganId2;		//被通知机构id
	private String noticeOrganName2;		//被通知机构名称
	private String custNo;			//客户号
	
	public void setId(Long id){
		this.set("id",id);
	}

	public Long getId(){
		return this.getLong("id");
	}
	
	public void setDataId(Integer dataId){
		this.set("dataId",dataId);
	}

	public Integer getDataId(){
		return this.getInteger("dataId");
	}
	
	
	public void setModelId(Integer modelId){
		this.set("modelId",modelId);
	}
	
	public Integer getModelId(){
		return this.getInteger("modelId");
	}
	public Long getDocId()
	{
		return this.getLong("docId");
	}
	public void setDocId(Long docId)
	{
		this.set("docId", docId);
	}
	public void setDealStatus(String dealStatus){
		this.set("dealStatus",dealStatus);
	}

	public String getDealStatus(){
		return this.getString("dealStatus");
	}
	
	
	public void setNoticeStatus(String noticeStatus){
		this.set("noticeStatus",noticeStatus);
	}

	public String getNoticeStatus(){
		return this.getString("noticeStatus");
	}
	
	public void setNoticeOrganId(String noticeOrganId){
		this.set("noticeOrganId",noticeOrganId);
	}

	public String getNoticeOrganId(){
		return this.getString("noticeOrganId");
	}
	
	public void setNoticeOrganName(String noticeOrganName){
		this.set("noticeOrganName",noticeOrganName);
	}

	public String getNoticeOrganName(){
		return this.getString("noticeOrganName");
	}
	
	public void setBnoticeOrganId(String bnoticeOrganId){
		this.set("bnoticeOrganId",bnoticeOrganId);
	}

	public String getBnoticeOrganId(){
		return this.getString("bnoticeOrganId");
	}
	
	public void setBnoticeOrganName(String bnoticeOrganName){
		this.set("bnoticeOrganName",bnoticeOrganName);
	}

	public String getBnoticeOrganName(){
		return this.getString("bnoticeOrganName");
	}
	
	public void setNoticeStartTime(String noticeStartTime){
		this.set("noticeStartTime",noticeStartTime);
	}

	public String getNoticeStartTime(){
		return this.getString("noticeStartTime");
	}
	
	
	public void setNoticeColseTime(String noticeColseTime){
		this.set("noticeColseTime",noticeColseTime);
	}

	public String getNoticeColseTime(){
		return this.getString("noticeColseTime");
	}
	
	
	public void setNoticeEndTime(String noticeEndTime){
		this.set("noticeEndTime",noticeEndTime);
	}

	public String getNoticeEndTime(){
		return this.getString("noticeEndTime");
	}
	
	
	public void setNoticeDealRequire(String noticeDealRequire){
		this.set("noticeDealRequire",noticeDealRequire);
	}

	public String getNoticeDealRequire(){
		return this.getString("noticeDealRequire");
	}
	
	public void setNoticeDealRequire2(String noticeDealRequire2){
		this.set("noticeDealRequire2",noticeDealRequire2);
	}

	public String getNoticeDealRequire2(){
		return this.getString("noticeDealRequire2");
	}
	
	
	public void setTranDate(String tranDate){
		this.set("tranDate",tranDate);
	}

	public String getTranDate(){
		return this.getString("tranDate");
	}
	
	public void setNoticeSender(String noticeSender){
		this.set("noticeSender",noticeSender);
	}

	public String getNoticeSender(){
		return this.getString("noticeSender");
	}
	
	public void setNoticeDealer(String noticeDealer){
		this.set("noticeDealer",noticeDealer);
	}

	public String getNoticeDealer(){
		return this.getString("noticeDealer");
	}
	
	
	public void setNoticeSender2(String noticeSender2){
		this.set("noticeSender2",noticeSender2);
	}

	public String getNoticeSender2(){
		return this.getString("noticeSender2");
	}
	
	public void setNoticeDealer2(String noticeDealer2){
		this.set("noticeDealer2",noticeDealer2);
	}

	public String getNoticeDealer2(){
		return this.getString("noticeDealer2");
	}
	
	public void setNoticeOrganId2(String noticeOrganId2){
		this.set("noticeOrganId2",noticeOrganId2);
	}

	public String getNoticeOrganId2(){
		return this.getString("noticeOrganId2");
	}
	
	
	public void setNoticeOrganName2(String noticeOrganName2){
		this.set("noticeOrganName2",noticeOrganName2);
	}

	public String getNoticeOrganName2(){
		return this.getString("noticeOrganName2");
	}
	
	public String getCustNo()
	{
		return this.getString("custNo");
	}
	
	public void setCustNo(String custNo)
	{
		this.set("custNo", custNo);
	}
	
}
