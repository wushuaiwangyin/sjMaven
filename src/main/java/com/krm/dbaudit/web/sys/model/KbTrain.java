package com.krm.dbaudit.web.sys.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

@SuppressWarnings("unused")
@Table(name="kb_train")
public class KbTrain extends BaseEntity
{
	private static final long serialVersionUID = 2876547383440712961L;
	@SequenceGenerator(name="Any",sequenceName="seq_kb_train")
	@Id
	private Long trainId;			//主键
	private String trainCode;		//编码
	private String trainTitle;		//标题名称
	private String keyword;			//关键字
	private String trainSrc;		//作者出处
	private String publicFlag;		//发布标志
	private String publicDate;		//发布日期
	private String trainDesc;		//培训学习描述
	private Long disporder;			//显示顺序
	private String memo;			//备注
	private Long docId;				//关联系统的附件表的序列号
	private String status;			//状态，默认1
	private String creatorId;		//创建人
	private String createTime;		//创建时间
	private String editorId;		//修改人
	private String editTime;		//修改时间
	public void setTrainId(Long trainId){
		this.set("trainId",trainId);
	}
	public Long getTrainId(){
		return this.getLong("trainId");
	}
	public String getTrainCode()
	{
		return this.getString("trainCode");
	}
	public void setTrainCode(String trainCode)
	{
		this.set("trainCode", trainCode);
	}
	public String getTrainTitle()
	{
		return this.getString("trainTitle");
	}
	public void setTrainTitle(String trainTitle)
	{
		this.set("trainTitle", trainTitle);
	}
	public String getKeyword()
	{
		return this.getString("keyword");
	}
	public void setKeyword(String keyword)
	{
		this.set("keyword", keyword);
	}
	public String getTrainSrc()
	{
		return this.getString("trainSrc");
	}
	public void setTrainSrc(String trainSrc)
	{
		this.set("trainSrc", trainSrc);
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
	public String getTrainDesc()
	{
		return this.getString("trainDesc");
	}
	public void setTrainDesc(String trainDesc)
	{
		this.set("trainDesc", trainDesc);
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
