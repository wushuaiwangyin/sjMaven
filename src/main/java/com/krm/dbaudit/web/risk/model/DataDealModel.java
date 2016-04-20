package com.krm.dbaudit.web.risk.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

@Table(name="model_data_deal")
@SuppressWarnings("unused")
public class DataDealModel extends BaseEntity
{
	private static final long serialVersionUID = 8625913737999566386L;
	@SequenceGenerator(name="Any",sequenceName="model_data_deal_seq")
	@Id
	private Long id;				//id
	private Integer dataId;			//数据id
	private Integer modelId;		//模型id
	private Long docId;				//附件id
	private String organId;			//交易机构
	private String organName;		//交易机构名称
	private String tranDate;		//交易日期（数据日期）
	private String dataSeq;			//预警序号
	private String reason;			//预警原因
	private String dealEscription;	//处理情况简述
	private String dealType;		//处理方式，现场核实或者非现场核实
	private String dealDetail;		//处理措施
	private String relative;		//联系人
	private String phoneOrMail;		//电话或邮件
	private String dealTime;		//处理时间
	private String dealUser;		//处理人
	private String tranType;		//交易类型
	private Integer dataStatus;		//处理状态
	private String custNo;			//客户号
	public Long getId()
	{
		return this.getLong("id");
	}
	public void setId(Long id)
	{
		this.set("id", id);
	}
	public Integer getDataId()
	{
		return this.getInteger("dataId");
	}
	public void setDataId(Integer dataId)
	{
		this.set("dataId", dataId);
	}
	public Integer getModelId()
	{
		return this.getInteger("modelId");
	}
	public void setModelId(Integer modelId)
	{
		this.set("modelId", modelId);
	}
	public Long getDocId()
	{
		return this.getLong("docId");
	}
	public void setDocId(Long docId)
	{
		this.set("docId", docId);
	}
	public String getOrganId()
	{
		return this.getString("organId");
	}
	public void setOrganId(String organId)
	{
		this.set("organId", organId);
	}
	public String getTranDate()
	{
		return this.getString("tranDate");
	}
	public void setTranDate(String tranDate)
	{
		this.set("tranDate", tranDate);
	}
	public String getDataSeq()
	{
		return this.getString("dataSeq");
	}
	public void setDataSeq(String dataSeq)
	{
		this.set("dataSeq", dataSeq);
	}
	public String getDealEscription()
	{
		return this.getString("dealEscription");
	}
	public void setDealEscription(String dealEscription)
	{
		this.set("dealEscription", dealEscription);
	}
	public String getDealType()
	{
		return this.getString("dealType");
	}
	public void setDealType(String dealType)
	{
		this.set("dealType", dealType);
	}
	public String getDealDetail()
	{
		return this.getString("dealDetail");
	}
	public void setDealDetail(String dealDetail)
	{
		this.set("dealDetail", dealDetail);
	}
	public String getRelative()
	{
		return this.getString("relative");
	}
	public void setRelative(String relative)
	{
		this.set("relative", relative);
	}
	public String getPhoneOrMail()
	{
		return this.getString("phoneOrMail");
	}
	public void setPhoneOrMail(String phoneOrMail)
	{
		this.set("phoneOrMail", phoneOrMail);
	}
	public String getDealTime()
	{
		return this.getString("dealTime");
	}
	public void setDealTime(String dealTime)
	{
		this.set("dealTime", dealTime);
	}
	public String getDealUser()
	{
		return this.getString("dealUser");
	}
	public void setDealUser(String dealUser)
	{
		this.set("dealUser", dealUser);
	}
	public String getTranType()
	{
		return this.getString("tranType");
	}
	public void setTranType(String tranType)
	{
		this.set("tranType", tranType);
	}
	public String getReason()
	{
		return this.getString("reason");
	}
	public void setReason(String reason)
	{
		this.set("reason", reason);
	}
	public String getOrganName()
	{
		return this.getString("organName");
	}
	public void setOrganName(String organName)
	{
		this.set("organName", organName);
	}
	public Integer getDataStatus()
	{
		return this.getInteger("dataStatus");
	}
	public void setDataStatus(Integer dataStatus)
	{
		this.set("dataStatus", dataStatus);
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
