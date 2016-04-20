package com.krm.dbaudit.web.modeltool.mapper;

import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
* @author chenwei on 2015-11-19
* 分析工具流程部分
*/
public interface ToolFlowMapper {
	public String initTargetTableName();
	public String initFlowID();
	public String getTableNameByFlowID(String flowid);
	public String getFlowIDByTable(String tablename);
	public int getTableCountByAlias(String tablealias);
	public int writeTaretTableInfo(Map<String, Object> params);
	public String getFieldAlias(Map<String, Object> params);
	public int writeFieldInfo(Map<String, Object> params);
	public int writeGroupCountFieldInfo(Map<String, Object> params);
	public int writeFlowInfo(Map<String, Object> params);
	public int writeConditionInfo(Map<String, Object> params);
	public String getCreateTable(String tablename);
	public String getProcGetDataSql(Map<String, Object> params);
	public String getlFieldEntityType(String fieldid);
	public int writeGetRange(Map<String, Object> params);
	
	//虚拟列相关
	public int checkSameFieldAlias(Map<String, Object> params);
	public int getOrderByFieldId(String fieldid);
	public int getFieldCountByTable(String tablename);
	public int getMaxOrderByTable(String tablename);
	public int updateFieldOrder(Map<String, Object> params);
	public int insertVirtualField(Map<String, Object> params);
	public int getVirtualFieldUseCount(long fieldid);
	public int deleteVirtualField(String fieldid);
	
	//汇总相关
	public String getProcGroupDataSql(Map<String, Object> params);
	
	//数据合并
	public int writeRelationField(Map<String, Object> params);
	public String getProcMergeDataSql1(Map<String, Object> params);
	public String getProcMergeDataSql2(Map<String, Object> params);
	public String getProcMergeDataSql3(Map<String, Object> params);
	public String getProcMergeDataSql4(Map<String, Object> params);
	
	//数据去重
	public int writeDistinctField(Map<String, Object> params);
	public String getProcDistinctDataSql(Map<String, Object> params);
	
	//排序
	public int writeSortTableField(Map<String, Object> params);
	public int writeSortField(Map<String, Object> params);
	
	//叠加
	public int getCountOverlayTable(Map<String, Object> params);
	public int writeOverlayTableInfo(Map<String, Object> params);
	public int writeOverlayFieldInfo(Map<String, Object> params);
	public List<String> selectOverlayTableList(String flowid);
	public String getProcOverlayDataSql(Map<String, Object> params);
	
	
	//流程修改
	public List<Map<String,Object>> checkSelectFieldIsWrong(Map<String, Object> params); 
	public List<Map<String,Object>> checkSelectTableIsWrong(Map<String, Object> params);
	public int getTableCountByAlias2(Map<String, Object> params);
	public int updateTaretTableInfo(Map<String, Object> params);
	public int deleteFieldInfo(Map<String, Object> params);
	public int updateFieldInfo(Map<String, Object> params);
	public int deleteGetRange(Map<String, Object> params);
	public int deleteCondition(Map<String, Object> params);
	public int deleteSortInfo(Map<String, Object> params);
	public int deleteDistinctInfo(Map<String, Object> params);
	public int deleteMergeInfo(Map<String, Object> params);
	public int deleteOverlayInfo(Map<String, Object> params);
	public int deleteOverLayFieldInfo(Map<String, Object> params);
	public int editOverlayFieldInfo(Map<String, Object> params);
	public int updateFlowInfo(Map<String, Object> params);
	public int updateVirtualFieldOrder(Map<String, Object> params);
	
	//流程跑批
	public String getFlowChildTree(Map<String, Object> params);
}
