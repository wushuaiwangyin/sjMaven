package com.krm.dbaudit.web.report.analysis.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;
/**
 * 统计分析配置
 * @author 
 *
 */
@SuppressWarnings({ "unused"})
@Table(name="report_analysis_conf")
public class Analysis extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8940544233343215549L;
	@SequenceGenerator(name="Any",sequenceName="seq_report_analysis")
	@Id
	private Long id;
	private Long reportId;			//报表编号
	private String reportName;		//报表名称
	private String reportUrl;		//报表对应的BI访问地址
	private Long sort;				//报表排序
	private String status;			//报表状态 0生产 9禁用
	
	private String name;
	private Long reportType;		//报表类型
	private String typeName;		//报表类型名称
	
	public Long getReportId() {
		return this.getLong("reportId");
	}

	public void setReportId(Long reportId) {
		this.set("reportId",reportId);
	}
	
	public String getReportName() {
		return this.getString("reportName");
	}

	public void setReportName(String reportName) {
		this.set("reportName",reportName);
	}
	
	public String getReportUrl() {
		return this.getString("reportUrl");
	}

	public void setReportUrl(String reportUrl) {
		this.set("reportUrl",reportUrl);
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
	
	public String getName() {
		return this.getString("name");
	}

	public void setName(String name) {
		this.set("name",name);
	}
	
	
	public Long getId() {
		return this.getLong("id");
	}

	public void setId(Long id) {
		this.set("id",id);
	}
	
	public Long getReportType() {
		return this.getLong("reportType");
	}

	public void setReportType(Long reportType) {
		this.set("reportType",reportType);
	}
	
	
	public String getTypeName() {
		return this.getString("typeName");
	}

	public void setTypeName(String typeName) {
		this.set("typeName",typeName);
	}
	
	
	
}
