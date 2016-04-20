package com.krm.dbaudit.web.report.analysis.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.report.analysis.mapper.ReportTypeMapper;
import com.krm.dbaudit.web.report.analysis.model.ReportType;
@Service("reportTypeService")
public class ReportTypeService extends ServiceMybatis<ReportType> {
	@Resource
	private ReportTypeMapper reportTypeMapper;

	public List<ReportType> findAllReportType() {
		return reportTypeMapper.findAllReportType();
	}

	public Integer saveReportType(ReportType reportType) {
		int count = 0;
		if(reportType.getId() == null){
			count = this.insertSelective(reportType);
		}else{
			count = this.updateByPrimaryKeySelective(reportType);
		}
		return count;
	}
	
	public ReportType findById(Long id){
		return this.selectByPrimaryKey(id);
	}

	public List<ReportType> findByStartWithId(Long id) {
		
		return reportTypeMapper.findByStartWithId(id);
	}
}
