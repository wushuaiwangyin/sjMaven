package com.krm.dbaudit.web.sys.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

/**
 * 审计依据条目编码表
 * @author 
 *
 */
@SuppressWarnings("unused")
@Table(name="kb_std_entry")
public class KbStdEntry extends BaseEntity
{
	private static final long serialVersionUID = -955646827151321667L;
	@SequenceGenerator(name="Any",sequenceName="seq_kb_std_entry")
	@Id
	private Long entryId;		//条目标识
	private String stdCode;		//标准编码
	private String entryCode;	//审计依据条目编码
	private Long disporder;		//显示顺序
	private String keyword;		//内容关键字
	private String content;		//条目内容
	private String status;		//状态
	private String creatorId;	//创建人
	private String createTime;	//创建时间
	private String editorId;	//修改人
	private String editTime;	//修改时间
	public Long getEntryId()
	{
		return this.getLong("entryId");
	}
	public void setEntryId(Long entryId)
	{
		this.set("entryId",entryId);
	}
	public String getStdCode()
	{
		return this.getString("stdCode");
	}
	public void setStdCode(String stdCode)
	{
		this.set("stdCode",stdCode);
	}
	public String getEntryCode()
	{
		return this.getString("entryCode");
	}
	public void setEntryCode(String entryCode)
	{
		this.set("entryCode",entryCode);
	}
	public Long getDisporder()
	{
		return this.getLong("disporder");
	}
	public void setDisporder(Long disporder)
	{
		this.set("disporder",disporder);
	}
	public String getKeyword()
	{
		return this.getString("keyword");
	}
	public void setKeyword(String keyword)
	{
		this.set("keyword",keyword);
	}
	public String getContent()
	{
		return this.getString("content");
	}
	public void setContent(String content)
	{
		this.set("content",content);
	}
	public String getStatus()
	{
		return this.getString("status");
	}
	public void setStatus(String status)
	{
		this.set("status",status);
	}
	public String getCreatorId()
	{
		return this.getString("creatorId");
	}
	public void setCreatorId(String creatorId)
	{
		this.set("creatorId",creatorId);
	}
	public String getCreateTime()
	{
		return this.getString("createTime");
	}
	public void setCreateTime(String createTime)
	{
		this.set("createTime",createTime);
	}
	public String getEditorId()
	{
		return this.getString("editorId");
	}
	public void setEditorId(String editorId)
	{
		this.set("editorId",editorId);
	}
	public String getEditTime()
	{
		return this.getString("editTime");
	}
	public void setEditTime(String editTime)
	{
		this.set("editTime",editTime);
	}
	
	
}
