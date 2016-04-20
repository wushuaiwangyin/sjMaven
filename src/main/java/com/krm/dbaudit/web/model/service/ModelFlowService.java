package com.krm.dbaudit.web.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.model.mapper.ModelFlowMapper;
import com.krm.dbaudit.web.model.model.ModelFieldRelation;
import com.krm.dbaudit.web.model.model.ModelFlow;
import com.krm.dbaudit.web.modeltool.model.ToolTableField;
import com.krm.dbaudit.web.modeltool.oracle.dao.ToolShowTableData;
import com.krm.dbaudit.web.modeltool.service.ToolFlowService.ActionType;
import com.krm.dbaudit.web.modeltool.oracle.dao.ToolShowTableData;

/**
* @author chenwei on 2015-11-06
*/

@Service("modelFlowService")
public class ModelFlowService extends ServiceMybatis<ModelFlow>{
	@Resource
	private ModelFlowMapper modelFlowMapper;
	
	@Resource
	private ToolShowTableData toolShowTableData;
	
	@Autowired
	private SqlMapper sqlMapper;
	
	/**
	 * 处理流程类型枚举
	*/
	public enum ActionType {  
		proc_getdata,proc_groupdata,proc_mergedata,proc_sortdata,proc_distinctdata,proc_overlaydata
	}
	
	/**
	 * 处理流程类型枚举
	*/
	public enum groupType {  
		sum,avg,max,min
	}
	
	public ToolShowTableData getToolShowTableData() {
		return toolShowTableData;
	}

	public void setToolShowTableData(ToolShowTableData toolShowTableData) {
		this.toolShowTableData = toolShowTableData;
	}
	
	/**
	 * 提取模型整体流程信息
	*/
	public List<ModelFlow> findFlowById(Long modelid){
		List<ModelFlow> list = modelFlowMapper.findFlowById(modelid);
		for(int i=0;i<list.size();i++)
		{
			ModelFlow flow = list.get(i);
			//设置提取字段信息
			if(flow.getActionMode().toLowerCase().equals("proc_overlaydata")){
				list.get(i).setFieldInfo(getOverlayTableFieldString(""+flow.getId()));		
			}else if(flow.getActionMode().toLowerCase().equals("proc_mergedata")){
				String info1 = flow.getSourceTableName1()+" : "+findSrcTableFieldInfo(flow.getTargetTable(),flow.getSourceTable1());
				String info2 = flow.getSourceTableName2()+" : "+findSrcTableFieldInfo(flow.getTargetTable(),flow.getSourceTable2());
				list.get(i).setFieldInfo(info1+"<br>"+info2);
			}
			else if(flow.getActionMode().toLowerCase().equals("proc_groupdata"))
				list.get(i).setFieldInfo(findSrcTableFieldGroupInfo(flow.getTargetTable(),flow.getSourceTable1()));
			else
				list.get(i).setFieldInfo(flow.getSourceTableName1()+" : "+findSrcTableFieldInfo(flow.getTargetTable(),flow.getSourceTable1()));
			//设置主表、附表信息
			list.get(i).setFirstTable(flow.getSourceTableName1()+"("+flow.getSourceTable1()+")");
			if(flow.getActionMode().toLowerCase().equals("proc_overlaydata")){
				String srctable = getOverlayTableString(""+flow.getId());
				if(srctable!=null)
					list.get(i).setSecondaryTable(srctable);
			}
			else if(flow.getActionMode().toLowerCase().equals("proc_mergedata"))
				list.get(i).setSecondaryTable(flow.getSourceTableName2()+"("+flow.getSourceTable2()+")");
			
			String conditon = getConditionInfo(""+flow.getId());
			if(conditon!=null)
				list.get(i).setConditionInfo(conditon);
			
			String range = getRangeInfo(""+flow.getId());
			if(range!=null)
				list.get(i).setDataArea(range);
			
			if(flow.getActionMode().toLowerCase().equals("proc_distinctdata")){
				String distinct = getDistinctFieldString(""+flow.getId());
				if(distinct!=null)
					list.get(i).setDistinctInfo(distinct);
			}
			
			if(flow.getActionMode().toLowerCase().equals("proc_sortdata")){
				String sort = getSortFieldString(""+flow.getId());
				if(sort!=null)
					list.get(i).setSortInfo(sort);
			}
			
			if(flow.getActionMode().toLowerCase().equals("proc_mergedata")){
				String merge = getMergeFieldString(""+flow.getId());
				if(merge!=null)
					list.get(i).setRelationInfo(merge);
			}
			
			String virtual = getvirtualfieldString(flow.getTargetTable());
			if(virtual!=null)
				list.get(i).setVirtualfieldInfo(virtual);
		}
		return list;
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取源表字段信息
	*/
	public String findSrcTableFieldInfo(String targettable,String srctable){
		String fieldinfo = "";
		List<ToolTableField> list = findSrcTableFieldList(targettable,srctable);
		for(int i=0;i<list.size();i++)
			fieldinfo = fieldinfo + list.get(i).getFieldAlias()+"\n";
		if(fieldinfo.equals(""))
			return "";
		return fieldinfo.substring(0,fieldinfo.length()-1);
	}
	
	
	
	/**
	 * 提取单步流程附属信息
	 * 提取源表group字段信息
	*/
	public String findSrcTableFieldGroupInfo(String targettable,String srctable){
		String collectinfo = "";
		String groupinfo = "";
		List<ToolTableField> list = findSrcTableFieldGroupList(targettable,srctable);
		for(int i=0;i<list.size();i++)
		{
			ToolTableField item = list.get(i);
			System.out.println(item);
			//汇总字段
			if(item.getFieldScriptName()==null)
				groupinfo = groupinfo + item.getFieldAlias()+"\n";
			else
				collectinfo = collectinfo + item.getFieldAlias()+" ("+item.getFieldScriptName()+")"+"\n";
		}
		if(groupinfo.equals(""))
			return "";
		if(collectinfo.equals(""))
			return "";
		return "分组字段:"+groupinfo.substring(0,groupinfo.length()-1)+"<br>"+"汇总字段:"+collectinfo.substring(0,collectinfo.length()-1);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取源表字段信息List
	*/
	public List<ToolTableField> findSrcTableFieldList(String targettable,String srctable){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("targettable", targettable);
		params.put("srctable", srctable);
		List<ToolTableField> list = modelFlowMapper.getSrcTableFieldInfo(params);
		return list;
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取源表字段信息List
	*/
	public List<ToolTableField> findSrcTableFieldGroupList(String targettable,String srctable){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("targettable", targettable);
		params.put("srctable", srctable);
		List<ToolTableField> list = modelFlowMapper.getSrcTableFieldGroupInfo(params);
		return list;
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取叠加操作源表信息
	*/
	public String getvirtualfieldString(String targettable){
		String fieldinfo = "";
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("targettable", targettable);
		List<Map<String,Object>> list = modelFlowMapper.getVirtualGroupInfo(params);
		for(int i=0;i<list.size();i++)
		{
			fieldinfo = fieldinfo + list.get(i).get("fieldAlias")+"("+list.get(i).get("fieldScript")+")<br>";
		}
		if(fieldinfo.equals(""))
			return null;
		return fieldinfo.substring(0,fieldinfo.length()-4);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取叠加操作源表信息
	*/
	public String getOverlayTableString(String flowid){
		String fieldinfo = "";
		List<Map<String,Object>> list = getOverlayTableInfo(flowid);
		for(int i=1;i<list.size();i++)
			fieldinfo = fieldinfo + list.get(i).get("tableAlias")+"("+list.get(i).get("srcTable")+"),";
		if(fieldinfo.equals(""))
			return null;
		return fieldinfo.substring(0,fieldinfo.length()-1);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取叠加操作源表List
	*/
	public List<Map<String,Object>> getOverlayTableInfo(String flowid){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		return modelFlowMapper.getOverlayTableInfo(params);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取叠加操作源表字段信息List
	*/
	public String getOverlayTableFieldString(String flowid){
		String fieldinfo = "";
		
		List<Map<String,Object>> list = getOverlayTableInfo(flowid);
		for(int i=0;i<list.size();i++)
		{
			List<ToolTableField> fieldlist = getOverlayTableFieldInfo(list.get(i).get("srcTable").toString());
			fieldinfo = fieldinfo +list.get(i).get("tableAlias")+" : ";
			for(int j=0;j<fieldlist.size();j++)
			{
				fieldinfo = fieldinfo + fieldlist.get(j).getFieldAlias()+"\n";
			}
			fieldinfo = fieldinfo + "<br>";
		}

		if(fieldinfo.equals(""))
			return "";
		
		return fieldinfo.substring(0,fieldinfo.length()-4);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取叠加操作源表字段信息List
	*/
	public List<ToolTableField> getOverlayTableFieldInfo(String srctable){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("srctable", srctable);
		List<ToolTableField> list = modelFlowMapper.getOverlayTableFieldInfo(params);
		return list;
	}
	
	public String getRangeInfo(String flowid){
		String s = "";
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		List<Map<String,Object>> list = modelFlowMapper.getRangeInfo(params);
		if(list.size()>0)
		{
			String beginnum = list.get(0).get("beginLine").toString();
			String endnum = list.get(0).get("endLine").toString();
			beginnum = beginnum.equals("0")?"1":beginnum;
			endnum = endnum.equals("0")?"最后":endnum;
			return "起始行: "+beginnum+"<br>"+"终止行: "+endnum;
		}
		else
			return null;
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取主表条件信息
	*/
	public String getConditionInfo(String flowid){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		return modelFlowMapper.getConditionInfo(params);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取排重字段信息
	*/
	public String getDistinctFieldString(String flowid){
		String fieldinfo = "";
		List<Map<String,Object>> list = getDistinctFieldInfo(flowid);
		for(int i=0;i<list.size();i++)
			fieldinfo = fieldinfo + list.get(i).get("fieldAlias")+",";
		if(fieldinfo.equals(""))
			return null;
		return fieldinfo.substring(0,fieldinfo.length()-1);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取排重List
	*/
	public List<Map<String,Object>> getDistinctFieldInfo(String flowid){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		return modelFlowMapper.getDistinctFieldInfo(params);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取排重字段信息
	*/
	public String getSortFieldString(String flowid){
		String fieldinfo = "";
		List<Map<String,Object>> list = getSortFieldInfo(flowid);
		for(int i=0;i<list.size();i++)
		{
			String sorttype = (String)list.get(i).get("sortType");
			if(sorttype.toLowerCase().equals("asc"))
				sorttype = "升序";
			else
				sorttype = "降序";
			fieldinfo = fieldinfo + list.get(i).get("fieldAlias")+"("+sorttype+")"+",";
		}
		if(fieldinfo.equals(""))
			return null;
		return fieldinfo.substring(0,fieldinfo.length()-1);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取排重List
	*/
	public List<Map<String,Object>> getSortFieldInfo(String flowid){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		return modelFlowMapper.getSortFieldInfo(params);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取合并关联字段信息
	*/
	public String getMergeFieldString(String flowid){
		String fieldinfo = "";
		List<Map<String,Object>> list = getMergeFieldInfo(flowid);
		for(int i=0;i<list.size();i++)
		{
			fieldinfo = fieldinfo + "主表-"+list.get(i).get("fieldAlias1") + ":副表-"+list.get(i).get("fieldAlias2")+",";
		}
		if(fieldinfo.equals(""))
			return null;
		return fieldinfo.substring(0,fieldinfo.length()-1);
	}
	
	/**
	 * 提取单步流程附属信息
	 * 提取合并List
	*/
	public List<Map<String,Object>> getMergeFieldInfo(String flowid){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		return modelFlowMapper.getMergeFieldInfo(params);
	}
	
	public String getFlowById(Long modelid){
		List<ModelFlow> list = modelFlowMapper.findFlowById(modelid);
		JSONArray ja = new JSONArray();
		for(int i=0;i<list.size();i++)
		{
			JSONObject jd = new JSONObject();
			ModelFlow flow = list.get(i);
			jd.put("id", flow.getId());
			jd.put("model", flow.getModelId());
			jd.put("index", i);
			jd.put("name", flow.getFlowName());
			jd.put("test", true);
			jd.put("remark", "");
			jd.put("sql", "");
			ja.add(jd);
		}
		return ja.toString();
	}
	
	//正式库执行flow
	//serial 序列串，用以区分不同流程跑批请求
	public boolean doformalflow(long flowid,String serial){
		try{
			boolean result = true;
			ModelFlow flow = (ModelFlow) modelFlowMapper.findFlowByFlowId(flowid);
			String actionid = flow.getActionMode();
			ActionType actionType = ActionType.valueOf(actionid);
			
			switch(actionType)
			{
				case proc_getdata:	//提取数据
					result = flowGetData(flow,serial);
					break;
				case proc_groupdata:	//汇总数据
					result = flowGroupData(flow,serial);
					break;
				case proc_distinctdata:	//去重
					result = flowDistinctData(flow,serial);
					break;
				case proc_sortdata:	//排序
					result = flowSortData(flow,serial);
					break;
				case proc_overlaydata:	//叠加
					result = flowOverlayData(flow,serial);
					break;
				case proc_mergedata:
					result = flowMergeData(flow,serial);
					break;
			}
			return result;
		}catch(Exception e){
			return false;
		}
	}
	
	public Long getModelDataCount(long modelid,String serial){
		List<ModelFieldRelation> list = modelFlowMapper.getFieldRelation(modelid);
		String fromtable = list.get(0).getTableName();
		return toolShowTableData.getTableDataCount2(fromtable+"_"+serial);
	}
	
	public boolean transResultData(long modelid,String serial){
		try{
		//	String readsql = "";
			String fromtable = "";
			List<ModelFieldRelation> list = modelFlowMapper.getFieldRelation(modelid);
			fromtable = list.get(0).getTableName();
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("modelid", modelid);
			params.put("serial", serial);
			String transsql = modelFlowMapper.getTransDataSql(params);
			
			System.out.println(transsql);
			sqlMapper.delete("delete from MODEL_DATA where model_id="+modelid);
			sqlMapper.insert(transsql);
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean clearTempTable(String serial){
		toolShowTableData.clearFlowTempTable(serial);
		return true;
	}
	
	/*
	public boolean transResultData(long modelid,String serial){
		try{
			//加工数据读取列，数据写入列等sql
			String readsql = "";
			String fromtable = "";
			List<ModelFieldRelation> list = modelFlowMapper.getFieldRelation(modelid);
			for(int i=0;i<list.size();i++)
			{
				fromtable = list.get(i).getTableName();
				readsql = readsql+list.get(i).getFieldName()+",";
			}
			readsql = readsql + "ROWIDTOCHAR(RowId) as rid";
			readsql = "select "+readsql+" from "+fromtable+"_"+serial+" where rownum<=1000";
			
			//循环读取数据，批量写入
			String tempsql = readsql;
			List<Map<String, Object>> datalist = null;
			
			sqlMapper.delete("delete from MODEL_DATA where model_id="+modelid);
		
			while((datalist = toolShowTableData.getTableData(tempsql))!=null)
			{
				if(datalist.size()==0)
					break;
				
				String rid = "";
				for(int i=0;i<datalist.size();i++)
				{
					int nindex = 0;
					String insertval = "";
					String insertfield = "";
					//map遍历生成insert
					Map map = datalist.get(i);
					Iterator it=map.keySet().iterator();    
					while(it.hasNext()){    
					     String key;    
					     String value = "";    
					     key=it.next().toString();    
					     if(map.get(key)!=null)
					    	 value=map.get(key).toString();
					     if(nindex<list.size())
					     {
					    	 if((list.get(nindex).getFieldType()!=3)||(value.equals("")))
					    		 insertval = insertval+"'"+value+"',";
					    	 else
					    		 insertval = insertval+value+",";
					    	 
					    	 insertfield = insertfield+"itemvalue"+list.get(nindex).getFieldColumn()+",";
					     }
					     nindex++;
					}
					insertfield = insertfield+"model_id,pkid,NOTICE_STATUS,DEAL_STATUS,ORGAN_ID,DATA_DATE";
					insertval = insertval+modelid+",MODEL_DATA_SEQ.nextval,1,1,'1',sysdate-1";
					
					sqlMapper.insert("insert into MODEL_DATA("+insertfield+") values("+insertval+")");
					rid = map.get("rid").toString();
				}
			//	modelFlowMapper.insertbatch(datalist);
				tempsql = readsql+" and rowid>'"+rid+"'";		
			}
			
		}catch(Exception e){
				System.out.println(e.toString());
				return false;
		}
		return true;
	}
	*/
	
	/*
	public boolean transResultData(long modelid,String serial){
		try{
			//加工数据读取列，数据写入列等sql
			String readsql = "";
			String fromtable = "";
			List<ModelFieldRelation> list = modelFlowMapper.getFieldRelation(modelid);
			for(int i=0;i<list.size();i++)
			{
				fromtable = list.get(i).getTableName();
				readsql = readsql+list.get(i).getFieldName()+",";
			}
			readsql = readsql + "ROWIDTOCHAR(RowId) as rid";
			readsql = "select "+readsql+" from "+fromtable+"_"+serial+" where rownum<=1000";
			
			//循环读取数据，批量写入
			String tempsql = readsql;
			List<Map<String, Object>> datalist = null;
			
			sqlMapper.delete("delete from MODEL_DATA where model_id="+modelid);
		
			while((datalist = toolShowTableData.getTableData(tempsql))!=null)
			{
				String rid = "";
				for(int i=0;i<datalist.size();i++)
				{
					datalist.get(i).put("modelid", modelid);
					datalist.get(i).put("noticestatus", 1);
					datalist.get(i).put("dealstatus", 1);
					datalist.get(i).put("organid", "1");
					Map map = datalist.get(i);

					rid = map.get("rid").toString();
					
				}
				modelFlowMapper.insertbatch(datalist);
				tempsql = readsql+" and rowid>'"+rid+"'";		
			}
			
		}catch(Exception e){
				System.out.println(e.toString());
				return false;
		}
		return true;
	}
	*/
	
	private boolean flowGetData(ModelFlow flow,String serial)
	{
		try{
			//判断表是否存在，存在则先删除	
			checkTable(flow.getTargetTable()+"_"+serial);
			createTable(flow.getTargetTable(),serial);
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", flow.getTargetTable());
			params.put("serial", serial);
			params.put("flowid", flow.getId());
			params.put("srctable", flow.getSourceTable1());
			String getdatasql = modelFlowMapper.getFormalGetDataSql(params);
			getdatasql = getdatasql.replace("''", "'");
			toolShowTableData.runSql(getdatasql);
			
		}catch(Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean flowGroupData(ModelFlow flow,String serial)
	{
		try{	
			//判断表是否存在，存在则先删除	
			checkTable(flow.getTargetTable()+"_"+serial);	
			createTable(flow.getTargetTable(),serial);
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", flow.getTargetTable());
			params.put("serial", serial);
			params.put("flowid", flow.getId());
			params.put("srctable", flow.getSourceTable1());
			String groupdatasql = modelFlowMapper.getFormalGroupDataSql(params);
			groupdatasql = groupdatasql.replace("''", "'");
			toolShowTableData.runSql(groupdatasql);
			
		}catch(Exception e){
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
	
	private boolean flowDistinctData(ModelFlow flow,String serial)
	{
		try{	
			//判断表是否存在，存在则先删除	
			checkTable(flow.getTargetTable()+"_"+serial);
			createTable(flow.getTargetTable(),serial);
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", flow.getTargetTable());
			params.put("serial", serial);
			params.put("flowid", flow.getId());
			params.put("srctable", flow.getSourceTable1());
			params.put("mode", flow.getProcMode());
			String distinctdatasql = modelFlowMapper.getFormalDistinctDataSql(params);
			distinctdatasql = distinctdatasql.replace("''", "'");
			toolShowTableData.runSql(distinctdatasql);
			
		}catch(Exception e){
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
	
	private boolean flowSortData(ModelFlow flow,String serial)
	{
		try{
			//判断表是否存在，存在则先删除	
			checkTable(flow.getTargetTable()+"_"+serial);
			createTable(flow.getTargetTable(),serial);
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", flow.getTargetTable());
			params.put("serial", serial);
			params.put("flowid", flow.getId());
			params.put("srctable", flow.getSourceTable1());
			String sortdatasql = modelFlowMapper.getFormalSortDataSql(params);
			sortdatasql = sortdatasql.replace("''", "'");
			toolShowTableData.runSql(sortdatasql);
						
		}catch(Exception e){
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
	
	private boolean flowOverlayData(ModelFlow flow,String serial)
	{
		try{
			//判断表是否存在，存在则先删除	
			checkTable(flow.getTargetTable()+"_"+serial);
			createTable(flow.getTargetTable(),serial);
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", flow.getTargetTable());
			params.put("serial", serial);
			params.put("flowid", flow.getId());
			params.put("srctable", flow.getSourceTable1());
			List<String> srctablelist = modelFlowMapper.selectOverlayTableList(flow.getId());
			for(int i=0;i<srctablelist.size();i++)
			{
				String curtable = srctablelist.get(i);
				params.put("curtable", curtable);
				String sortdatasql = modelFlowMapper.getFormalOverlayDataSql(params);
				sortdatasql = sortdatasql.replace("''", "'");
				toolShowTableData.runSql(sortdatasql);
			}		
		}catch(Exception e){
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
	
	private boolean flowMergeData(ModelFlow flow,String serial)
	{
		try{
			//判断表是否存在，存在则先删除	
			checkTable(flow.getTargetTable()+"_"+serial);
			createTable(flow.getTargetTable(),serial);
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", flow.getTargetTable());
			params.put("serial", serial);
			params.put("flowid", flow.getId());
			params.put("srctable1", flow.getSourceTable1());
			params.put("srctable2", flow.getSourceTable2());
			
			int mode = Integer.parseInt(flow.getProcMode());
			String megerdatasql = "";
			switch(mode)
			{
				case 1:
					megerdatasql = modelFlowMapper.getFormalMergeDataSql1(params);
					megerdatasql = megerdatasql.replace("''", "'");
					toolShowTableData.runSql(megerdatasql);
					break;
				case 2:
					megerdatasql = modelFlowMapper.getFormalMergeDataSql2(params);
					megerdatasql = megerdatasql.replace("''", "'");
					toolShowTableData.runSql(megerdatasql);
					break;
				case 3:
					megerdatasql = modelFlowMapper.getFormalMergeDataSql3(params);
					megerdatasql = megerdatasql.replace("''", "'");
					toolShowTableData.runSql(megerdatasql);
					break;
				case 4:
					megerdatasql = modelFlowMapper.getFormalMergeDataSql4(params);
					megerdatasql = megerdatasql.replace("''", "'");
					toolShowTableData.runSql(megerdatasql);
					break;
				case 5:
					megerdatasql = modelFlowMapper.getFormalMergeDataSql3(params);
					megerdatasql = megerdatasql.replace("''", "'");
					toolShowTableData.runSql(megerdatasql);
					megerdatasql = modelFlowMapper.getFormalMergeDataSql4(params);
					megerdatasql = megerdatasql.replace("''", "'");
					toolShowTableData.runSql(megerdatasql);
					break;
			}
		}catch(Exception e){
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
	
	//检查表是否存在，存在则删除
	private void checkTable(String tablename){
		if(toolShowTableData.getTableCount(tablename)>0)
			toolShowTableData.dropTable(tablename);
	}
	
	private void createTable(String tablename,String serial){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("targettable", tablename);
		params.put("serial", serial);
		String createtablesql = modelFlowMapper.getCreateTable(params);
		toolShowTableData.runSql(createtablesql);
	}
	
	private void clearTempTable(List<String> tablelist){
	//	for(int i=0;i<tablelist.size();i++)
	//		toolShowTableData.dropTable(tablelist.get(i));
	}
	
	private void writeFlowRunLog()
	{
	}
	
	public List<Map<String,Object>> getTableFieldByFlowID(Long flowid){
		return modelFlowMapper.getTableFieldByFlowID(flowid);
	}
	
	public String selectTableNameByFlowID(Long flowid){
		return modelFlowMapper.selectTableNameByFlowID(flowid);
	}
	
	//查询中间表数据
	public PageInfo<Map<String, Object>> getTableDataByFlowID(Map<String, Object> params){
		long flowid = Long.parseLong(params.get("id").toString());
		String tableName = selectTableNameByFlowID(flowid);
		params.put("targettable", tableName);
		if(params.get("serial")!=null)
			tableName = tableName+"_"+params.get("serial").toString();
		long ncount = toolShowTableData.getTableDataCount2(tableName);
		String sql = modelFlowMapper.getFormalShowTableSql(params);
		int pageNum = Integer.parseInt(params.get("pageNum").toString());
		int pageSize = Integer.parseInt(params.get("pageSize").toString());
		
		List<Map<String, Object>> datalist = toolShowTableData.getTableData(sql);
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNum, pageSize,true);
		page.addAll(datalist);
		page.setTotal(ncount);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(page);
		return pageInfo;
	}
}
