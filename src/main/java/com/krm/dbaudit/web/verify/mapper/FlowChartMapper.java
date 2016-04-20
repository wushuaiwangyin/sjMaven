package com.krm.dbaudit.web.verify.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.verify.model.FlowChart;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface FlowChartMapper extends Mapper<FlowChart>{

	public List<FlowChart> findPageInfo(Map<String, Object> params); 
	
	public Long insertFlowChart(FlowChart flow);
	
	public FlowChart getById(Long id);

	public List<FlowChart> findAllFlowChart();
}
