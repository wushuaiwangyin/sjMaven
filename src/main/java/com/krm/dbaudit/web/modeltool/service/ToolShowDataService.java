package com.krm.dbaudit.web.modeltool.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Service;

import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.modeltool.mapper.ToolOperateMapper;
import com.krm.dbaudit.web.modeltool.model.ToolDataSql;
import com.krm.dbaudit.web.modeltool.model.ToolTable;
import com.krm.dbaudit.web.modeltool.oracle.dao.ToolShowTableData;
import com.krm.dbaudit.web.verify.model.OutData;

@Service("toolShowDataService")
public class ToolShowDataService {
	@Resource
	private ToolOperateMapper toolOperateMapper;

	@Resource
	private ToolShowTableData toolShowTableData;
	
	
	public ToolShowTableData getToolShowTableData() {
		return toolShowTableData;
	}

	public void setToolShowTableData(ToolShowTableData toolShowTableData) {
		this.toolShowTableData = toolShowTableData;
	}

	public String getShowTableData(Map<String, Object> params)
	{
		String sql = toolOperateMapper.getTableShowDataSql(params);		
		String countsql = toolOperateMapper.getTableShowDataCountSql(params);
		int ncount = toolShowTableData.getTableDataCount(countsql);
	
		List<Map<String, Object>> datalist = toolShowTableData.getTableData(sql);
		JSONArray jadata = new JSONArray();
		for(int i=0;i<datalist.size();i++)
		{
			JSONObject jddata = new JSONObject();
			Map map = datalist.get(i);
			Iterator it=map.keySet().iterator();    
			while(it.hasNext()){    
			     String key;    
			     String value = "";    
			     key=it.next().toString();    
			     if(map.get(key)!=null)
			    	 value=map.get(key).toString();
			     jddata.put(key, value);
			}
			jadata.add(jddata);
		}
		String s = "{\"total\":\""+ncount+"\",\"rows\":"+jadata.toString()+"}";
		jadata.clear();
		System.out.println(s);
		return s;
	}
	
	/**
	 * 数据钻取，Group向下钻取数据使用
	* @param 
	* @return json
	 */
	public String getShowTableCubeData(Map<String, Object> params){
		
		//获取上一级表对应字段
		ArrayList checkfieldlist = new ArrayList();
		Iterator it=params.keySet().iterator();    
		while(it.hasNext()){    
		     String key;    
		     String value = "";    
		     key=it.next().toString();    
		     if(params.get(key)!=null)
		     {
		    	 if(key.length()>9)
		    	 {
			    	 //识别是不是专区的字段
			    	 if(key.substring(0,9).toUpperCase().equals("AUTOFIELD"))
			    		 checkfieldlist.add(key);
		    	 }
		     }
		}
		params.put("fieldnames", checkfieldlist);
		List<Map<String,Object>> fieldlist = toolOperateMapper.getGroupCubeTableFields(params);
		String condition = "";
		String srctable = "";
		for(int i=0;i<fieldlist.size();i++)
		{
			Map<String,Object> map = fieldlist.get(i);
			String fieldname1 = map.get("fieldname1").toString();
			String fieldname2 = map.get("fieldname2").toString();	//源表对应字段
			String fieldtype = map.get("fieldtype").toString();
			srctable = map.get("tablename").toString();
			String value = params.get(fieldname1).toString();
			if(!value.equals(""))
			{
				String s = "";
				if((fieldtype.equals("3"))||(fieldtype.equals("4")))
					s = fieldname2+"="+value;
				else
					s = fieldname2+"='"+value+"'";
				
				if(i==0)
					condition = s;
				else
					condition = condition+" and "+s;
			}
		}
		params.put("tablename",srctable);
		params.put("condition",condition);
		
		return getShowTableData(params);
	}

	public String getTableNameByZhTableName(Map<String, Object> map) {
		ToolTable td = toolOperateMapper.getTableNameByZhTableName(map);
		return td.getTableName();
	}

	public List findPageTableData(Map<String, Object> map) {
		String tablename = map.get("tablename").toString();
		String sql = "select * from "+tablename + " where 1=1 ";
		
		String codition = "";
		Object convalueobj = map.get("convalue");
		if(null!=convalueobj && !"".equals(convalueobj.toString())){

			String property = map.get("property").toString();
			String columnname = map.get("columnname").toString();
			if(!property.equals("between") && !property.equals("in") 
					&& !property.equals("not in ")){
				codition += " and "+columnname+" "+property+" \'"+convalueobj.toString()+"\'";
			}
			sql += codition;
		}
		
		sql += " and rownum<=50 ";
		List<Map<String, Object>> datalist = toolShowTableData.getTableData(sql);
		return datalist;
	}
}
