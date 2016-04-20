package com.krm.dbaudit.web.sys.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

/**
 * 审计标准附件表
 * @author 
 *
 */
@SuppressWarnings({ "unused"})
@Table(name="sys_accessory")
public class SysAccessory extends BaseEntity{

	private static final long serialVersionUID = -6383258172019530907L;
	@SequenceGenerator(name="Any",sequenceName="seq_sys_accessory")
	@Id
	private Long docId;				//编号
	private String docTitle;		//标题
	private String docType;			//文件类型
	private String fileName;		//文件名
	private String randomName;		//
	private byte[] accessory;		//内容
	private String extName;			//后缀名
	private Long disporder;			//排序
	private String description;		//描叙
	private String status;			//状态
	private String creatorId;		//创建人
	private String createTime;		//创建时间
	private String editorId;		//编辑人
	private String editTime;		//编辑时间
	public Long getDocId()
	{
		return this.getLong("docId");
	}
	public void setDocId(Long docId)
	{
		this.set("docId",docId);
	}
	public String getDocTitle()
	{
		return this.getString("docTitle");
	}
	public void setDocTitle(String docTitle)
	{
		this.set("docTitle",docTitle);
	}
	public String getDocType()
	{
		return this.getString("docType");
	}
	public void setDocType(String docType)
	{
		this.set("docType",docType);
	}
	public String getFileName()
	{
		return this.getString("fileName");
	}
	public void setFileName(String fileName)
	{
		this.set("fileName",fileName);
	}
	public String getRandomName()
	{
		return this.getString("randomName");
	}
	public void setRandomName(String randomName)
	{
		this.set("randomName",randomName);
	}
	public byte[] getAccessory()
	{
		return this.getBytes("accessory");
	}
	public void setAccessory(byte[] accessory)
	{
		this.set("accessory",accessory);
	}
	public String getExtName()
	{
		return this.getString("extName");
	}
	public void setExtName(String extName)
	{
		this.set("extName",extName);
	}
	public Long getDisporder()
	{
		return this.getLong("disporder");
	}
	public void setDisporder(Long disporder)
	{
		this.set("disporder",disporder); 
	}
	public String getDescription()
	{
		return this.getString("description");
	}
	public void setDescription(String description)
	{
		this.set("description",description);
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