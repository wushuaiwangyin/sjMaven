package com.krm.dbaudit.web.risk.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

@SuppressWarnings("unused")
@Table(name="model_data_file")
public class ModelDataFile extends BaseEntity
{
	private static final long serialVersionUID = 8692665364224729512L;
	@SequenceGenerator(name="Any",sequenceName="model_data_file_seq")
	@Id
	private Long id;
	private Integer dataId;
	private String fileName;
	private String extName;
	private byte[] fileContent;
	
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
	public String getFileName()
	{
		return this.getString("fileName");
	}
	public void setFileName(String fileName)
	{
		this.set("fileName", fileName);
	}
	public String getExtName()
	{
		return this.getString("extName");
	}
	public void setExtName(String extName)
	{
		this.set("extName", extName);
	}
	public byte[] getFileContent()
	{
		return this.getBytes("fileContent");
	}
	public void setFileContent(byte[] fileContent)
	{
		this.set("fileContent", fileContent);
	}
	
	
}
