package com.krm.dbaudit.web.sys.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

/**
 * 审计标准依据表
 * @author 
 *
 */
@SuppressWarnings({ "unused"})
@Table(name="kb_std")
public class KbStd extends BaseEntity {

	private static final long serialVersionUID = -4979972193838423621L;
	@SequenceGenerator(name="Any",sequenceName="seq_kb_std")
	@Id
	private Long stdId;					//主键id
	private String stdCode;				//编码
	private String stdType;				//字典项
	private String fileNo;				//标准文号
	private String fileName;			//文件名称
	private String fileType;			//文件类型 ----字典维护－规章制度 操作规范 暂行办法 法律法规 通知
	private String fileSrc;				//文件出处作者
	private String publisher;			//出版单位
	private String fileDate;			//出版日期
	private String keyword;				//关键字
	private String fileVersion;			//版次
	private Long disporder;				//显示顺序
	private String publicFlag;			//发布标志
	private String publicDate;			//发布日期
	private String startDate;			//启用日期
	private String endDate;				//截至日期
	private String contact;				//文件联系人
	private String telephone;			//联系人电话
	private String memo;				//备注
	private Long docId;					//关联系统的附件表的序列号
	private String status;				//状态
	private String creatorId;			//创建人
	private String createTime;			//创建时间
	private String editorId;			//修改人
	private String editTime;			//修改时间
	private String stdTypeName;			//
	private String fileTypeName;		//			
	private String busType;				//字典维护－业务类型
	private String content;				//


	public Long getStdId() {
		return this.getLong("stdId");
	}

	public void setStdId(Long stdId) {
		this.set("stdId",stdId);
	}

	public String getStdCode() {
		return this.getString("stdCode");
	}

	public void setStdCode(String stdCode) {
		this.set("stdCode",stdCode);
	}

	public String getStdType() {
		return this.getString("stdType");
	}

	public void setStdType(String stdType) {
		this.set("stdType",stdType);
	}

	public String getFileNo() {
		return this.getString("fileNo");
	}

	public void setFileNo(String fileNo) {
		this.set("fileNo",fileNo);
	}

	public String getFileName() {
		return this.getString("fileName");
	}

	public void setFileName(String fileName) {
		this.set("fileName",fileName);
	}

	public String getFileType() {
		return this.getString("fileType");
	}

	public void setFileType(String fileType) {
		this.set("fileType",fileType);
	}

	public String getFileSrc() {
		return this.getString("fileSrc");
	}

	public void setFileSrc(String fileSrc) {
		this.set("fileSrc",fileSrc);
	}

	public String getPublisher() {
		return this.getString("publisher");
	}

	public void setPublisher(String publisher) {
		this.set("publisher",publisher);
	}

	public String getFileDate() {
		return this.getString("fileDate");
	}

	public void setFileDate(String fileDate) {
		this.set("fileDate",fileDate);
	}

	public String getKeyword() {
		return this.getString("keyword");
	}

	public void setKeyword(String keyword) {
		this.set("keyword",keyword);
	}

	public String getFileVersion() {
		return this.getString("fileVersion");
	}

	public void setFileVersion(String fileVersion) {
		this.set("fileVersion",fileVersion);
	}

	public Long getDisporder() {
		return this.getLong("disporder");
	}

	public void setDisporder(Long disporder) {
		this.set("disporder",disporder);
	}

	public String getPublicFlag() {
		return this.getString("publicFlag");
	}

	public void setPublicFlag(String publicFlag) {
		this.set("publicFlag",publicFlag);
	}

	public String getPublicDate() {
		return this.getString("publicDate");
	}

	public void setPublicDate(String publicDate) {
		this.set("publicDate",publicDate);
	}

	public String getStartDate() {
		return this.getString("startDate");
	}

	public void setStartDate(String startDate) {
		this.set("startDate",startDate);
	}

	public String getEndDate() {
		return this.getString("endDate");
	}

	public void setEndDate(String endDate) {
		this.set("endDate",endDate);
	}

	public String getContact() {
		return this.getString("contact");
	}

	public void setContact(String contact) {
		this.set("contact",contact);
	}

	public String getTelephone() {
		return this.getString("telephone");
	}

	public void setTelephone(String telephone) {
		this.set("telephone",telephone);
	}

	public String getMemo() {
		return this.getString("memo");
	}

	public void setMemo(String memo) {
		this.set("memo",memo);
	}

	public Long getDocId() {
		return this.getLong("docId");
	}

	public void setDocId(Long docId) {
		this.set("docId",docId);
	}

	public String getStatus() {
		return this.getString("status");
	}

	public void setStatus(String status) {
		this.set("status",status);
	}

	public String getCreatorId() {
		return this.getString("creatorId");
	}

	public void setCreatorId(String creatorId) {
		this.set("creatorId",creatorId);
	}

	public String getCreateTime() {
		return this.getString("createTime");
	}

	public void setCreateTime(String createTime) {
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

	public String getEditTime() {
		return this.getString("editTime");
	}

	public void setEditTime(String editTime) {
		this.set("editTime",editTime); 
	}

	public String getStdTypeName() {
		return this.getString("stdTypeName");
	}

	public void setStdTypeName(String stdTypeName) {
		this.set("stdTypeName",stdTypeName); 
	}

	public String getFileTypeName() {
		return this.getString("fileTypeName");
	}

	public void setFileTypeName(String fileTypeName) {
		this.set("fileTypeName",fileTypeName); 
	}

	public String getBusType() {
		return this.getString("busType");
	}

	public void setBusType(String busType) {
		this.set("busType",busType); 
	}

	public String getContent() {
		return this.getString("content");
	}

	public void setContent(String content) {
		this.set("content",content);
	}
	
	

}