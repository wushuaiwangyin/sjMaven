package com.krm.dbaudit.web.report.analysis.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.report.analysis.mapper.AnalysisMapper;
import com.krm.dbaudit.web.report.analysis.model.Analysis;
import com.krm.dbaudit.web.sys.model.SrcDataType;

@Service("analysisService")
public class AnalysisService extends ServiceMybatis<Analysis> {
	@Resource
	private AnalysisMapper analysisMapper;
	
	public List<Analysis> getAnalysisList(Map<String,Object> map){
		return analysisMapper.getAnalysisList(map);
	}

	public PageInfo<Analysis> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Analysis> list =  analysisMapper.findPageInfo(params);
		return new PageInfo<Analysis>(list);
	}

	public Analysis findById(Long id) {
		return analysisMapper.findById(id);
	}

	public int updateById(Analysis analysis) {
		return analysisMapper.updateById(analysis);
	}
}
