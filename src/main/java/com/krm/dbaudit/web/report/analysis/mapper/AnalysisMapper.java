package com.krm.dbaudit.web.report.analysis.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.report.analysis.model.Analysis;

public interface AnalysisMapper extends Mapper<Analysis>{
	public List<Analysis> getAnalysisList(Map<String,Object> map);

	public List<Analysis> findPageInfo(Map<String, Object> params);

	public Analysis findById(Long id);

	public int updateById(Analysis analysis);
}
