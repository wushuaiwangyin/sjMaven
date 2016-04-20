package com.krm.dbaudit.web.modeltool.oracle.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.krm.dbaudit.common.base.ExtDaoImpl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
* @author chenwei on 2015-11-19
* 无实体类查询，大数据库查询
*/

@Repository("toolShowTableData")
public class ToolShowTableDataImpl extends ExtDaoImpl implements ToolShowTableData{
	
	public List<Map<String, Object>> getTableData(String szSql)
	{
		System.out.println(szSql);
		return queryBySQLText(szSql);
	}
	
	/**
	 * 分析工具，获取查询结果数量
	* @param params
	* @return
	 */
	public int getTableDataCount(String szSql)
	{
		System.out.println(szSql);
		return queryForInt(szSql);
	}
	
	/**
	 * 获取表数据数量
	* @param params
	* @return
	 */
	public long getTableDataCount2(String tablename)
	{
		return queryForLong("select count(1) from "+tablename);
	}
	
	public void runSql(String szSql)
	{
		System.out.println(szSql);
		executeBySQLText(szSql);
	}
	
	public int getTableCount(String tablename)
	{
		/*
		//行云版本，获取表数量比较特殊
<<<<<<< .mine
//		String szSql = "select * from v$user_tables where table_name='"+tablename.toUpperCase()+"'";
//		List<Map<String, Object>> list = queryBySQLText(szSql);
//		return list.size();
=======
		String szSql = "select * from v$user_tables where table_name='"+tablename.toUpperCase()+"'";
		List<Map<String, Object>> list = queryBySQLText(szSql);
		return list.size();
		*/

		
		//Oracle 版本
		String szSql = "select count(1) from tab where tname='"+tablename.toUpperCase()+"'";
		return queryForInt(szSql);
	}
	
	public void dropTable(String tablename)
	{
		//如果目标表不存在，允许容错。
		try{
		System.out.println("drop table "+tablename);
		executeBySQLText("drop table "+tablename);
		}catch(Exception e){
		}
	}
	
	//获取临时表
	public List<Map<String, Object>> selectFlowTempTable(String serial)
	{
		//行云版本
//		String szSql = "select table_name from v$user_tables";
//		List<Map<String, Object>> list = queryBySQLText(szSql);
//		List<Map<String, Object>> rtnlist = new ArrayList();
//		for(int i=0;i<list.size();i++)
//		{
//			Map<String, Object> map = list.get(i);
//			String tname = map.get("table_name").toString();
//			if(tname.length()>3)
//				if(tname.substring(0,3).equals("TD_") && tname.substring(tname.length()-5).equals("_"+serial))
//					rtnlist.add(map);
//		}
//		return rtnlist;
		
		//oracle版本
		String szSql = "select tname from tab where substr(tname,1,3)='TD_' and substr(tname,length(tname)-4,5)='_"+serial+"'";
		return queryBySQLText(szSql);
	}
	
	public void clearFlowTempTable(String serial)
	{
		//行云版本
//		String szSql = "select table_name from v$user_tables";
//		List<Map<String, Object>> list = queryBySQLText(szSql);
//		for(int i=0;i<list.size();i++)
//		{
//			Map<String, Object> map = list.get(i);
//			String tname = map.get("table_name").toString();
//			if(tname.length()>3)
//				if(tname.substring(0,3).equals("TD_") && tname.substring(tname.length()-5).equals("_"+serial))
//					dropTable(tname);
//		}
		
		//oracle版本
		String szSql = "select tname from tab where substr(tname,1,3)='TD_' and substr(tname,length(tname)-4,5)='_"+serial+"'";
		List<Map<String, Object>> list = queryBySQLText(szSql);
		for(int i=0;i<list.size();i++)
		{
			Map map = list.get(i);
			String s = map.get("tname").toString();
			dropTable(s);
		}
	}
}
