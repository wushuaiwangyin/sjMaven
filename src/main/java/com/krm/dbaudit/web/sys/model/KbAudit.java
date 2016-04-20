package com.krm.dbaudit.web.sys.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.krm.dbaudit.common.base.BaseEntity;

@SuppressWarnings("unused")
public class KbAudit extends BaseEntity
{
	private static final long serialVersionUID = -5788327706589100703L;
	@SequenceGenerator(name="Any",sequenceName="seq_kb_audit")
	@Id
	private Long auditId;			//主键
	private String auditCode;		//编码
	private String auditTitle;		//标题名称
	private String auditType;		//审计类型
	private String auditObject;		//审计对象
	private String keyword;			//关键字
	private String auditDesc;		//描述
	private Long disporder;			//显示顺序
	private Long docId;				//关联系统的附件表的序列号
	private String status;			//状态，默认1
	private String creatorId;		//创建人
	private String createTime;		//创建时间
	private String editorId;		//修改人
	private String editTime;		//修改时间
	public Long getAuditId()
	{
		return this.getLong("auditId");
	}
	public void setAuditId(Long auditId)
	{
		this.set("auditId", auditId);
	}
	public String getAuditCode()
	{
		return this.getString("auditCode");
	}
	public void setAuditCode(String auditCode)
	{
		this.set("auditCode", auditCode);
	}
	public String getAuditTitle()
	{
		return this.getString("auditTitle");
	}
	public void setAuditTitle(String auditTitle)
	{
		this.set("auditTitle", auditTitle);
	}
	public String getAuditType()
	{
		return this.getString("auditType");
	}
	public void setAuditType(String auditType)
	{
		this.set("auditType", auditType);
	}
	public String getAuditObject()
	{
		return this.getString("auditObject");
	}
	public void setAuditObject(String auditObject)
	{
		this.set("auditObject", auditObject);
	}
	public String getKeyword()
	{
		return this.getString("keyword");
	}
	public void setKeyword(String keyword)
	{
		this.set("keyword", keyword);
	}
	public String getAuditDesc()
	{
		return this.getString("auditDesc");
	}
	public void setAuditDesc(String auditDesc)
	{
		this.set("auditDesc", auditDesc);
	}
	public Long getDisporder()
	{
		return this.getLong("disporder");
	}
	public void setDisporder(Long disporder)
	{
		this.set("disporder", disporder);
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
