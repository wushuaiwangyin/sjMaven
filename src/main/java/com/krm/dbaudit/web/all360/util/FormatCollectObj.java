package com.krm.dbaudit.web.all360.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.krm.dbaudit.web.all360.model.TabColsDetailModel;
import com.krm.dbaudit.web.all360.model.TableModelColumns;

public class FormatCollectObj {
public static String genConditons(List<Map<String, Object>> list) {
	StringBuffer sb=new StringBuffer("SELECT ");
	if(list.size()==0){
		return "";
	}
	String tableName=list.get(0).get("tablename").toString();
	String key="";
	String dicStr="";
	TableModelColumns tab=null;
	for (int i=0;i<list.size();i++) {
		tab=new TableModelColumns();
		for(Iterator iterator=list.get(i).keySet().iterator();iterator.hasNext();){
			key=iterator.next().toString().trim();
			if("columnname".toUpperCase().equals(key)){
				tab.setColName(list.get(i).get(key).toString().trim());
			}
			if("dicid".toUpperCase().equals(key)){
				tab.setDicid(Long.valueOf(list.get(i).get(key).toString().trim()));
			}
			if("alisename".toUpperCase().equals(key)){
				tab.setAlise(list.get(i).get(key).toString().trim());
			}
		}
		
		
		if(i==list.size()-1){
			if(tab.getDicid()>-100){
				dicStr="(select trim(dicname) from SYS_BUSINESS_DICTIONARY where trim(PARENTID)='"+tab.getDicid()
						+"' and trim(DICID)=trim("+tab.getColName()+"))";
				sb.append(dicStr+ " as "+tab.getAlise()+" ");
			}else{
			sb.append(tab.getColName()+" as "+tab.getAlise()+" ");	
			}
		}else{
			if(tab.getDicid()>-100){
				dicStr="(select trim(dicname) from SYS_BUSINESS_DICTIONARY where trim(PARENTID)='"+tab.getDicid()
						+"' and trim(DICID)=trim("+tab.getColName()+"))";
				sb.append(dicStr+ " as "+tab.getAlise()+" ,");
			}else{
			sb.append(tab.getColName()+" as "+tab.getAlise()+" ,");	
			}
		}
	}
	

	
	sb.append("  from "+tableName);
	sb.append(" where 1=1 ");
	return sb.toString();
	
}
public static String genEnConditons(List<Map<String, Object>> list) {
	StringBuffer sb=new StringBuffer("SELECT ");
	if(list.size()==0 ){
		return "";
	}
	String tableName=list.get(0).get("tablename").toString();
	TableModelColumns tab=null;
	String dicStr="";
	String key="";
	for (int i=0;i<list.size();i++) {
		tab=new TableModelColumns();
		for(Iterator iterator=list.get(i).keySet().iterator();iterator.hasNext();){
			key=iterator.next().toString().trim();
			if("columnname".toUpperCase().equals(key)){
				tab.setColName(list.get(i).get(key).toString().trim());
			}
			if("dicid".toUpperCase().equals(key)){
				tab.setDicid(Long.valueOf(list.get(i).get(key).toString().trim()));
			}
		}
		
		
		if(i==list.size()-1){
			if(tab.getDicid()>-100){
				dicStr="(select trim(dicname) from SYS_BUSINESS_DICTIONARY where trim(PARENTID)='"+tab.getDicid()
						+"' and trim(DICID)=trim("+tab.getColName()+"))";
				sb.append(dicStr+ " as "+tab.getColName()+" ");
			}else{
			sb.append(tab.getColName()+" ");	
			}
		}else{
			if(tab.getDicid()>-100){
				dicStr="(select trim(dicname) from SYS_BUSINESS_DICTIONARY where trim(PARENTID)='"+tab.getDicid()
						+"' and trim(DICID)=trim("+tab.getColName()+"))";
				sb.append(dicStr+ " as "+tab.getColName()+" ,");
			}else{
			sb.append(tab.getColName()+" ,");	
			}
		}
	}
	sb.append("  from "+tableName);
	sb.append(" where 1=1 ");
	return sb.toString();
}

/**列名中英文对照
 * @param list
 * @return
 */
public static Map<String, String> commonConditions(List<Map<String, Object>> list) {
	Map<String, String> map=new HashMap<String, String>();
	for (int i=0;i<list.size();i++) {
		map.put(list.get(i).get("columnname").toString().trim().toLowerCase(), list.get(i).get("alisename").toString());
	}
	
	return map;
}

/**整合列，表名，查询语句形成对象
 * @param list（查询模型的动态列信息）
 * @return
 */
public static TabColsDetailModel getTabForJdbcTemplet(List<Map<String, Object>> list) {
	String sqlString=FormatCollectObj.genEnConditons(list);
	TabColsDetailModel tab=new TabColsDetailModel();
	Map<String,TableModelColumns > map=new HashMap<String, TableModelColumns>();
	String key="";
	String colName="";
	String tabName="";
	TableModelColumns tc=null;
	if(list.size()>0){
		//整合列信息
		for(Map<String, Object> mm:list){
			tc=new TableModelColumns();
			//组装一个tc
			for(Iterator iterator=mm.keySet().iterator();iterator.hasNext();){
				key=iterator.next().toString().trim().toUpperCase();
				if("tableName".toUpperCase().equals(key)){
					tabName=mm.get(key).toString().toUpperCase();
				}
				if("columnName".toUpperCase().equals(key)){
					tc.setColName(mm.get(key).toString().toUpperCase());
					colName=mm.get(key).toString().toUpperCase();
				}
				if("aliseName".toUpperCase().equals(key)){
					tc.setAlise(mm.get(key).toString());
				}
				if("dataType".toUpperCase().equals(key)){
					tc.setDataType(mm.get(key).toString());
				}
				if("dicid".toUpperCase().equals(key)){
					tc.setDicid(Long.valueOf(mm.get(key).toString()));
				}
			}
			//将一个tc放入map，key=colName，value=tc对象
			map.put(colName, tc);
		}
		//生成整合后的对象
		tab.setSql(sqlString);//查询用的sql
		tab.setTableName(tabName);
		tab.setColsMap(map);
		
	}
	
	return tab;
}

  
}
