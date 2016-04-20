package com.krm.dbaudit.web.modeltool.oracle.dao;

import java.util.List;
import java.util.Map;

import com.krm.dbaudit.common.base.ExtDao;

public interface ToolShowTableData extends ExtDao{
	public abstract List<Map<String, Object>> getTableData(String szSql);
	public abstract int getTableDataCount(String szSql);
	public abstract void runSql(String szSql);
	public abstract int getTableCount(String tablename);
	public abstract void dropTable(String tablename);
	public abstract long getTableDataCount2(String tablename);
	public abstract void clearFlowTempTable(String serial);
}
