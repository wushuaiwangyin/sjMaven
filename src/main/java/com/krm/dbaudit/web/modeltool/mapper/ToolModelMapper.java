package com.krm.dbaudit.web.modeltool.mapper;

import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

public interface ToolModelMapper {
	public String getToolFlowTree(String tablename);
	public String intiModelId();
	public int deleteModelTableFlow(Map<String, Object> params);
	public int insertModelTableFlow(Map<String, Object> params);
	public int InsertModelInfo(Map<String, Object> params);
	public int relationModelField(String modelid);
}
