package com.krm.dbaudit.web.model.mapper;

import java.util.List;
import java.util.Map;

public interface ModelMonitorMapper {
	public List<Map<String, Object>> getModelStatusData();
	public List<Map<String, Object>> getModelDealData();
	public List<Map<String, Object>> getModelRunError();
}
