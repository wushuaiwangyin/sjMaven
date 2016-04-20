package com.krm.dbaudit.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.sys.model.SrcDataTable;
import com.krm.dbaudit.web.sys.model.SrcDataType;

public interface SrcDataMapper extends Mapper<SrcDataType>{

	public int deleteSrcDataTypeById(Long id);

	public List<SrcDataType> findPageInfo(Map<String, Object> params);

	public SrcDataTable findSrcTableByName(String srctalbename);

	public Integer saveSrcTable(SrcDataTable srcDataTable);

	public List<SrcDataType> findSrcTable(Map<String, Object> params);
	
	public int writeTableInfo(Map<String, Object> params);
	public int writeFieldInfo(Map<String, Object> params);
	
	public int synvTableInfo(String tablename);	//同步表数据
	public int synvTableFieldInfo(String tablename);	//同步表字段数据
	
	public int clearTable(String tablename);			//清除表数据
	public int clearTableField(String tablename);		//清楚表字段数据
	
	public int checkTableName(Map<String, Object> params);
	public int checkTableAlias(Map<String, Object> params);
}
