package com.krm.dbaudit.web.report.analysis.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;
/**
 * 报表类型配置
 * @author 
 *
 */
@SuppressWarnings({ "unused"})
@Table(name="report_type")
public class ReportType extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2802117936489563790L;
	@SequenceGenerator(name="Any",sequenceName="seq_report_type")
	@Id
	private Long id;		//报表类型编号
	private Long parentId;	//上级编号
	private String name;	//类型名称
	private Long sort;		//排序
	private String status;  //状态 0正常 9禁用
	
	public Long getId() {
		return this.getLong("id");
	}

	public void setId(Long id) {
		this.set("id",id);
	}
	
	public Long getParentId() {
		return this.getLong("parentId");
	}

	public void setParentId(Long parentId) {
		this.set("parentId",parentId);
	}
	
	public String getName() {
		return this.getString("name");
	}

	public void setName(String name) {
		this.set("name",name);
	}
	
	public Long getSort() {
		return this.getLong("sort");
	}

	public void setSort(Long sort) {
		this.set("sort",sort);
	}
	
	public String getStatus() {
		return this.getString("status");
	}

	public void setStatus(String status) {
		this.set("status",status);
	}
}
