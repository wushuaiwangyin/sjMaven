package com.krm.dbaudit.web.model.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.model.model.ModelFieldRelation;
import com.krm.dbaudit.web.model.model.ModelFlow;
import com.krm.dbaudit.web.modeltool.model.ToolTableField;

import java.util.List;
import java.util.Map;

public interface ModelFlowMapper extends Mapper<ModelFlow>{
	public List<ModelFlow> findFlowById(long modelid);
	public ModelFlow findFlowByFlowId(long flowid);
	public List<String> selectOverlayTableList(long flowid);
	public String getCreateTable(Map<String, Object> params);		//获取创建表结构语句
	public String getFormalGetDataSql(Map<String, Object> params);	//获取提取数据流程执行语句
	public String getFormalGroupDataSql(Map<String, Object> params);	//获取汇总数据流程执行语句
	public String getFormalDistinctDataSql(Map<String, Object> params);	//获取汇总数据流程执行语句
	public String getFormalSortDataSql(Map<String, Object> params);	//排序操作
	public String getFormalOverlayDataSql(Map<String, Object> params);	//叠加操作
	public String getFormalMergeDataSql1(Map<String, Object> params);	//关联操作 1-完整匹配
	public String getFormalMergeDataSql2(Map<String, Object> params);	//关联操作 2-完整匹配
	public String getFormalMergeDataSql3(Map<String, Object> params);	//关联操作 3-完整匹配
	public String getFormalMergeDataSql4(Map<String, Object> params);	//关联操作 4-完整匹配
	
	public List<ModelFieldRelation> getFieldRelation(long modelid);	//导入结果表数据
	public int insertbatch(List<Map<String, Object>> params);
	
	public String getTransDataSql(Map<String, Object> params);
	
	//获取源表字段信息
	public List<ToolTableField> getSrcTableFieldInfo(Map<String, Object> params);
	//提取叠加表相关信息
	public List<Map<String,Object>> getOverlayTableInfo(Map<String, Object> params);
	//提取叠加表相关信息
	public List<ToolTableField> getOverlayTableFieldInfo(Map<String, Object> params);
	//提取数据范围
	public List<Map<String,Object>> getRangeInfo(Map<String, Object> params);
	//提取条件信息
	public String getConditionInfo(Map<String, Object> params);
	//提取排重信息
	public List<Map<String,Object>> getDistinctFieldInfo(Map<String, Object> params);
	//获取排序信息
	public List<Map<String,Object>> getSortFieldInfo(Map<String, Object> params);
	//获取合并关联字段信息
	public List<Map<String,Object>> getMergeFieldInfo(Map<String, Object> params);
	//获取源表字段信息
	public List<ToolTableField> getSrcTableFieldGroupInfo(Map<String, Object> params);
	//获取虚拟列信息
	public List<Map<String,Object>> getVirtualGroupInfo(Map<String, Object> params);
	
	//获取流程对应实体表字段信息
	public List<Map<String,Object>> getTableFieldByFlowID(long flowid);
	//获取流程对应的实体表名
	public String selectTableNameByFlowID(long flowid);
	//获取数据库查询语句
	public String getFormalShowTableSql(Map<String, Object> params);
}
