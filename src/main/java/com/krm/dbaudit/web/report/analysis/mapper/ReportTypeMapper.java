package com.krm.dbaudit.web.report.analysis.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.report.analysis.model.ReportType;

public interface ReportTypeMapper extends Mapper<ReportType> {

	public List<ReportType> findAllReportType();

	public List<ReportType> findByStartWithId(Long id);

}
