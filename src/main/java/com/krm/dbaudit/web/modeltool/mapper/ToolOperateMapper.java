package com.krm.dbaudit.web.modeltool.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.modeltool.model.ToolDataSql;
import com.krm.dbaudit.web.modeltool.model.ToolFlow;
import com.krm.dbaudit.web.modeltool.model.ToolTable;
import com.krm.dbaudit.web.modeltool.model.ToolTableField;

import java.util.List;
import java.util.Map;

/**
* @author chenwei on 2015-11-13
* 分析工具操作接口
*/
public interface ToolOperateMapper {
	public List<ToolTable> getTableTree(Map<String, Object> params);
	public List<ToolTableField> getTableField(Map<String, Object> params);
	public List<Map<String,Object>> getGroupCubeTableFields(Map<String, Object> params);
	public String getParentGroupTablename(Map<String, Object> params);
	public String getTableShowDataSql(Map<String, Object> params);
	public String getTableShowDataCountSql(Map<String, Object> params);
	public List<ToolTableField> getTableFieldByZhTableName(Map<String, Object> params);
	public ToolTable getTableNameByZhTableName(Map<String, Object> map);
	
	public int getChildTableCount(String tablename);
	public int deleteTable(String tablename);
	
	public int updateTableName(Map<String, Object> params);
	public int updateFlowName(Map<String, Object> params);
	public int updateFieldOrder(Map<String, Object> params);
	
	public ToolFlow getFlowById(Map<String, Object> map);
	public ToolFlow getFlowByTableName(Map<String, Object> map);
	
	public List<Map<String,Object>> getToolDeffieldInfo(Map<String, Object> map);
	public String getToolDefFlowConditionInfo(Map<String, Object> map);
	public List<Map<String,Object>> getToolGetRangeInfo(Map<String, Object> map);
	public List<Map<String,Object>> getToolGroupFieldInfo(Map<String, Object> map);
	public List<Map<String,Object>> getToolCollectFieldInfo(Map<String, Object> map);
	public List<Map<String,Object>> getToolDistinctFieldInfo(Map<String, Object> map);
	public List<Map<String,Object>> getToolSortFieldInfo(Map<String, Object> map);
	public List<Map<String,Object>> getToolMergeFieldInfo(Map<String, Object> map);
	public List<Map<String,Object>> getToolOverlayFieldInfo(Map<String, Object> map);
	
	//获取流程所有下级相关流程
	public String getFlowChildTree(Map<String, Object> params);
}
