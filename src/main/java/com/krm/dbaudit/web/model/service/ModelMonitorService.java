package com.krm.dbaudit.web.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.krm.dbaudit.web.model.mapper.ModelMonitorMapper;


@Service("modelMonitorService")
public class ModelMonitorService {
	@Resource
	private ModelMonitorMapper modelMonitorMapper;
	
	public List<Map<String, Object>> getModelStatusData(){
		return modelMonitorMapper.getModelStatusData();
	}
	
	public List<Map<String, Object>> getModelDealData(){
		return modelMonitorMapper.getModelDealData();
	}
	
	public List<Map<String, Object>> getModelRunError(){
		return modelMonitorMapper.getModelRunError();
	}
}
