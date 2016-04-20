package com.krm.dbaudit.web.risk.mapper;

import java.util.List;
import java.util.Map;


public interface SecondRiskModelMapper
{
	public List<Map<String, Object>> findResultByMonth(Map<String, Object> params);
	
	public List<Map<String, Object>> findResultByDay(Map<String, Object> params);
	
	public List<Map<String, Object>> findDetails(Map<String, Object> params);

	public int save(List<Map<String, Object>> list);
}
