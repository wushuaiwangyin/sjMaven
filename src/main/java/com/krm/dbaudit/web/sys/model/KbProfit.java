package com.krm.dbaudit.web.sys.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.krm.dbaudit.common.base.BaseEntity;

@SuppressWarnings("unused")
public class KbProfit extends BaseEntity
{
	private static final long serialVersionUID = -5788327706589100703L;
	@SequenceGenerator(name="Any",sequenceName="seq_kb_profit")
	@Id
	private Long profitId;			//主键
	private String profitCode;		//成果编码
	private String profitTitle;		//成果标题名称
	private String profitType;		//字典维护－典型案例 审计报告精选 审计方案模板 审计发现
	private String keyword;			//关键字
	private String profitSrc;		//成果作者出处
	private String publicFlag;		//发布标志
	private String publicDate;		//发布日期
	private String profitDesc;		//成果描述
	private Long disporder;			//显示顺序
	private String memo;			//备注
	private Long docId;				//关联系统的附件表的序列号
	private String status;			//状态，默认1
	private String creatorId;		//创建人
	private String createTime;		//创建时间
	private String editorId;		//修改人
	private String editTime;		//修改时间
	public Long getProfitId()
	{
		return this.getLong("profitId");
	}
	public void setProfitId(Long profitId)
	{
		this.set("profitId", profitId);
	}
	public String getProfitCode()
	{
		return this.getString("profitCode");
	}
	public void setProfitCode(String profitCode)
	{
		this.set("profitCode", profitCode);
	}
	public String getProfitTitle()
	{
		return this.getString("profitTitle");
	}
	public void setProfitTitle(String profitTitle)
	{
		this.set("profitTitle", profitTitle);
	}
	public String getProfitType()
	{
		return this.getString("profitType");
	}
	public void setProfitType(String profitType)
	{
		this.set("profitType", profitType);
	}
	public String getKeyword()
	{
		return this.getString("keyword");
	}
	public void setKeyword(String keyword)
	{
		this.set("keyword", keyword);
	}
	public String getProfitSrc()
	{
		return this.getString("profitSrc");
	}
	public void setProfitSrc(String profitSrc)
	{
		this.set("profitSrc", profitSrc);
	}
	public String getPublicFlag()
	{
		return this.getString("publicFlag");
	}
	public void setPublicFlag(String publicFlag)
	{
		this.set("publicFlag", publicFlag);
	}
	public String getPublicDate()
	{
		return this.getString("publicDate");
	}
	public void setPublicDate(String publicDate)
	{
		this.set("publicDate", publicDate);
	}
	public String getProfitDesc()
	{
		return this.getString("profitDesc");
	}
	public void setProfitDesc(String profitDesc)
	{
		this.set("profitDesc", profitDesc);
	}
	public Long getDisporder()
	{
		return this.getLong("disporder");
	}
	public void setDisporder(Long disporder)
	{
		this.set("disporder", disporder);
	}
	public String getMemo()
	{
		return this.getString("memo");
	}
	public void setMemo(String memo)
	{
		this.set("memo", memo);
	}
	public Long getDocId()
	{
		return this.getLong("docId");
	}
	public void setDocId(Long docId)
	{
		this.set("docId", docId);
	}
	public String getStatus()
	{
		return this.getString("status");
	}
	public void setStatus(String status)
	{
		this.set("status", status);
	}
	public String getCreatorId()
	{
		return this.getString("creatorId");
	}
	public void setCreatorId(String creatorId)
	{
		this.set("creatorId", creatorId);
	}
	public String getCreateTime()
	{
		return this.getString("createTime");
	}
	public void setCreateTime(String createTime)
	{
		this.set("createTime", createTime);
	}
	public String getEditorId()
	{
		return this.getString("editorId");
	}
	public void setEditorId(String editorId)
	{
		this.set("editorId", editorId);
	}
	public String getEditTime()
	{
		return this.getString("editTime");
	}
	public void setEditTime(String editTime)
	{
		this.set("editTime", editTime);
	}
	
	
}
