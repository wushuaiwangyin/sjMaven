package com.krm.dbaudit.web.modeltool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Service;

import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.modeltool.mapper.ToolFlowMapper;
import com.krm.dbaudit.web.modeltool.mapper.ToolOperateMapper;
import com.krm.dbaudit.web.modeltool.model.ToolFlow;
import com.krm.dbaudit.web.modeltool.oracle.dao.ToolShowTableData;

/**
* @author chenwei on 2015-11-13
*/

@Service("toolFlowService")
public class ToolFlowService {
	@Resource
	private ToolFlowMapper toolFlowMapper;
	
	@Resource
	private ToolShowTableData toolShowTableData;
	
	@Resource
	private ToolOperateMapper toolOperateMapper;
	
	public ToolShowTableData getToolShowTableData() {
		return toolShowTableData;
	}
	public void setToolShowTableData(ToolShowTableData toolShowTableData) {
		this.toolShowTableData = toolShowTableData;
	}

	private String rtnCode;
	private String rtnMsg;
	private String rtnData;
	private String createby;
	
	public String getRtnCode() {
		return rtnCode;
	}
	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}
	public String getRtnMsg() {
		return rtnMsg;
	}
	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}
	public String getRtnData() {
		return rtnData;
	}
	public void setRtnData(String rtnData) {
		this.rtnData = rtnData;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}

	/**
	 * 处理流程类型枚举
	*/
	public enum ActionType {  
		get_data,insert_virtualfield,group_data,show_data,delete_virtualfield,merge_data,sort_data,distinct_data,overlay_data
	}
	
	/**
	 * 分析工具处理操作,解析页面参数，存储流程相关信息
	* @param json 
	* @return json
	 */
	public String doFlow(Map<String, Object> params)
	{
		String strJson = params.get("jsonmemo").toString();
		setCreateby(params.get("createby").toString());
		String result = "";
		
	//	strJson = "[{\"action_id\":\"group_data\",\"action_data\":[{\"modelid\":1,\"flowid\":\"\",\"tablealias\":\"dfgdfgerger\",\"srctable\":\"SALGRADE\",\"field\":[{\"id\":1339,\"fieldname\":\"AGENCIES\",\"fieldalias\":\"工资等级\"}],\"collectfield\":null,\"condition\":\"\"}]}]";
		System.out.println(strJson);
		
		JSONArray ja = JSONArray.parseArray(strJson);
		JSONObject jo = ja.getJSONObject(0);
		String actionid = jo.getString("action_id").toString().toLowerCase();
		ActionType actionType = ActionType.valueOf(actionid);
		
		String oldflowid = "";
		if(jo.getJSONArray("action_data").getJSONObject(0).getString("flowid")!=null)
			oldflowid = jo.getJSONArray("action_data").getJSONObject(0).getString("flowid").toString();
		//新增流程
		if(oldflowid.equals("")){
			switch(actionType)
			{
				case get_data:	//提取数据
					flowGetData(jo.getJSONArray("action_data"));
					break;
				case insert_virtualfield:	//插入虚拟列
					flowInsertVirtualField(jo.getJSONArray("action_data"));
					break;
				case delete_virtualfield:	//删除虚拟列
					flowDeleteVirtualField(jo.getJSONArray("action_data"));
					break;
				case group_data:	//汇总数据
					flowGroupData(jo.getJSONArray("action_data"));
					break;
				case merge_data:	//合并数据
					flowMergeData(jo.getJSONArray("action_data"));
					break;
				case sort_data:		//排序数据
					flowSortData(jo.getJSONArray("action_data"));
					break;
				case distinct_data:	//排重数据
					flowDistinctData(jo.getJSONArray("action_data"));
					break;
				case overlay_data:	//叠加数据
					flowOverlayData(jo.getJSONArray("action_data"));
					break;
			}
		}
		//修改流程
		else{
			switch(actionType)
			{
				case get_data:	//提取数据
					editflowGetData(jo.getJSONArray("action_data"));
					break;
				case group_data:	//汇总数据
					editflowGroupData(jo.getJSONArray("action_data"));
					break;
				case merge_data:	//合并数据
					editflowMergeData(jo.getJSONArray("action_data"));
					break;
				case sort_data:		//排序数据
					editflowSortData(jo.getJSONArray("action_data"));
					break;
				case distinct_data:	//排重数据
					editflowDistinctData(jo.getJSONArray("action_data"));
					break;
				case overlay_data:	//叠加数据
					editflowOverlayData(jo.getJSONArray("action_data"));
					break;
			}			
		}
		result = "[{\"return_flag\":\""+getRtnCode()+"\"," +
				"\"return_msg\":\""+getRtnMsg()+"\","+
				"\"return_data\":"+getRtnData()+"}]";
		System.out.println(result);
		return result;
	}
	
	public String runChildFlow(String targettable)
	{
		String result = "";
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("targettable", targettable);
		ToolFlow flow = toolOperateMapper.getFlowByTableName(params);
		
		if(flow==null)
		{
			setRtnCode("1");
			setRtnMsg("只能刷新分析表数据,路径及源表数据无变化。");
			setRtnData("null");
			
			result = "[{\"return_flag\":\""+getRtnCode()+"\"," +
					"\"return_msg\":\""+getRtnMsg()+"\","+
					"\"return_data\":"+getRtnData()+"}]";
			return result;
		}
		
		params.put("flowid",flow.getId().toString());
		
		runFlow(flow);
		
		//获取下级所有流程
		params.put("targettable", flow.getTargetTable());
		String s = toolOperateMapper.getFlowChildTree(params);
		
		System.out.println(s);
		
		if(s==null || s.equals("")){
			setRtnCode("0");
			setRtnMsg("无下级流程，本级数据刷新成功。");
			setRtnData("null");
			
			result = "[{\"return_flag\":\""+getRtnCode()+"\"," +
					"\"return_msg\":\""+getRtnMsg()+"\","+
					"\"return_data\":"+getRtnData()+"}]";
			return result;
		}
		
		StringTokenizer line=new StringTokenizer(s,";");
		while(line.hasMoreTokens()){
			List<String> itemlist=new ArrayList<String>();
			StringTokenizer item=new StringTokenizer(line.nextToken(),",");
			while(item.hasMoreTokens()){
        		itemlist.add(item.nextToken());
        	}
			if(itemlist.size()>0)
			{
				params.put("flowid", itemlist.get(0));
				ToolFlow childflow = toolOperateMapper.getFlowById(params);
				runFlow(childflow);
			}
		}
		
		setRtnCode("0");
		setRtnMsg("本级及下级流程数据刷新成功。");
		setRtnData("null");
		
		result = "[{\"return_flag\":\""+getRtnCode()+"\"," +
				"\"return_msg\":\""+getRtnMsg()+"\","+
				"\"return_data\":"+getRtnData()+"}]";
		System.out.println(result);
		return result;
	}
	
	/**
	 * 运行单步流程
	* @param json 
	* @return json
	 */
	private boolean runFlow(ToolFlow flow)
	{
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("targettable", flow.getTargetTable());
		params.put("flowid", flow.getId());
		params.put("srctable", flow.getSourceTable1());
		params.put("srctable1", flow.getSourceTable1());
		params.put("srctable2", flow.getSourceTable2());
		params.put("mode", flow.getProcMode());
		
		String createtablesql = "";
		String runsql = "";
		
		//删除创建中间表
		createtablesql = toolFlowMapper.getCreateTable(flow.getTargetTable());
		toolShowTableData.dropTable(flow.getTargetTable());
		toolShowTableData.runSql(createtablesql);
		
		ActionType actionType = ActionType.valueOf(flow.getWebAction());
		switch(actionType)
		{
			case get_data:	//提取数据
				runsql = toolFlowMapper.getProcGetDataSql(params).replace("''", "'");
				toolShowTableData.runSql(runsql);
				break;
			case group_data:	//汇总数据
				runsql = toolFlowMapper.getProcGroupDataSql(params).replace("''", "'");
				toolShowTableData.runSql(runsql);
				break;
			case merge_data:	//合并数据
				String mode = flow.getProcMode();
				if(mode.equals("1")){
					runsql = toolFlowMapper.getProcMergeDataSql1(params).replace("''", "'");
					toolShowTableData.runSql(runsql);
				}
				else if(mode.equals("2")){
					runsql = toolFlowMapper.getProcMergeDataSql2(params).replace("''", "'");
					toolShowTableData.runSql(runsql);
				}
				else if(mode.equals("3")){
					runsql = toolFlowMapper.getProcMergeDataSql3(params).replace("''", "'");
					toolShowTableData.runSql(runsql);
				}
				else if(mode.equals("4")){
					runsql = toolFlowMapper.getProcMergeDataSql4(params).replace("''", "'");
					toolShowTableData.runSql(runsql);
				}
				else if(mode.equals("5")){
					runsql = toolFlowMapper.getProcMergeDataSql3(params).replace("''", "'");
					toolShowTableData.runSql(runsql);
					runsql = toolFlowMapper.getProcMergeDataSql4(params).replace("''", "'");
					toolShowTableData.runSql(runsql);
				}
				break;
			case sort_data:		//排序数据
				runsql = toolFlowMapper.getProcGetDataSql(params).replace("''", "'");
				toolShowTableData.runSql(runsql);
				break;
			case distinct_data:	//排重数据
				runsql = toolFlowMapper.getProcDistinctDataSql(params).replace("''", "'");
				toolShowTableData.runSql(runsql);
				break;
			case overlay_data:	//叠加数据
				List<String> tablelist = toolFlowMapper.selectOverlayTableList(flow.getId().toString());
				for(int i=0;i<tablelist.size();i++)
				{
					String curtable = tablelist.get(i);
					params.put("curtable", curtable);
					runsql = toolFlowMapper.getProcOverlayDataSql(params).replace("''", "'");
					toolShowTableData.runSql(runsql);
				}
				break;
		}
		return true;
	}
	
	//提取数据流程 新增
	private boolean flowGetData(JSONArray ja)
	{
		try{
			//解析json参数
			JSONObject jo = ja.getJSONObject(0);
			String oldflowid = jo.getString("flowid").toString();
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String srctable = jo.getString("srctable").toString();
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			String condition = jo.getString("condition").toString();
			JSONArray ja1 = jo.getJSONArray("field");
			condition = condition.replace("'", "''");
			int beginnum = 0;
			int endnum = 0;
			if(jo.getString("firstnum")!=null)
				if(!jo.getString("firstnum").equals(""))
				beginnum = Integer.parseInt(jo.getString("firstnum").toString());
			if(jo.getString("endnum")!=null)
				if(!jo.getString("endnum").equals(""))
				endnum = Integer.parseInt(jo.getString("endnum").toString());
			
			if(ja.size()<=0){
				setRtnCode("1");
				setRtnMsg("数据提取失败，提取选择的字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			if((tablealias==null) || tablealias.equals(""))
			{
				setRtnCode("1");
				setRtnMsg("表别名不能为空。");
				setRtnData("null");
				return false;
			}
			
			if((beginnum!=0)&&(endnum!=0)&&(endnum<beginnum))
			{
				setRtnCode("1");
				setRtnMsg("数据末行不能小于数据首行。");
				setRtnData("null");
				return false;
			}
			
			if(toolFlowMapper.getTableCountByAlias(tablealias)>0)
			{
				setRtnCode("1");
				setRtnMsg("数据表别名不能重复，请修改。");
				setRtnData("null");
				return false;
			}
			
			String targettable = toolFlowMapper.initTargetTableName();
			String flowid = toolFlowMapper.initFlowID();
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable);
			params.put("createby", getCreateby());
			toolFlowMapper.writeTaretTableInfo(params);
			
			//写入字段信息
			for(int i=0;i<ja1.size();i++)
			{
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				writeFieldInfo(targettable, fieldalias, id, ""+(i+1),"");
			}
			
			//写入数据提取范围
			if((beginnum!=0)||(endnum!=0))
				this.writeGetRange(flowid, beginnum, endnum);
			
			if(condition!=null && !condition.equals(""))
				writeCondition(flowid, condition);
			writeFlowInfo(tablealias, flowid, modelid, parentflowid, "proc_getdata", targettable, srctable, "","");
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("targettable", targettable);
			params.put("flowid", flowid);
			params.put("srctable", srctable);
			String getdatasql = toolFlowMapper.getProcGetDataSql(params);
			getdatasql = getdatasql.replace("''", "'");
			toolShowTableData.runSql(getdatasql);
			
			
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据提取成功。");
			setRtnData("["+jd.toString()+"]");
			
			jd.clear();
			ja.clear();
			ja1.clear();
			jo.clear();
						
		}catch(Exception e){
			e.printStackTrace();
			setRtnCode("2");
			setRtnMsg("数据提取异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	//提取数据流程  修改
	private boolean editflowGetData(JSONArray ja)
	{
		try{
			//解析json参数
			JSONObject jo = ja.getJSONObject(0);
			String flowid = jo.getString("flowid").toString();
			String targettable = toolFlowMapper.getTableNameByFlowID(flowid);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String srctable = jo.getString("srctable").toString();
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			String condition = jo.getString("condition").toString();
			JSONArray ja1 = jo.getJSONArray("field");
			condition = condition.replace("'", "''");
			int beginnum = 0;
			int endnum = 0;
			if(jo.getString("firstnum")!=null)
				if(!jo.getString("firstnum").equals(""))
				beginnum = Integer.parseInt(jo.getString("firstnum").toString());
			if(jo.getString("endnum")!=null)
				if(!jo.getString("endnum").equals(""))
				endnum = Integer.parseInt(jo.getString("endnum").toString());
			
			//检测选择字段是否为空
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("数据提取修改失败，提取选择的字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			//检测别名是否为空
			if((tablealias==null) || tablealias.equals(""))
			{
				setRtnCode("1");
				setRtnMsg("表别名不能为空。");
				setRtnData("null");
				return false;
			}
			
			//检测范围
			if((beginnum!=0)&&(endnum!=0)&&(endnum<beginnum))
			{
				setRtnCode("1");
				setRtnMsg("数据末行不能小于数据首行。");
				setRtnData("null");
				return false;
			}
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable);
			params.put("createby", getCreateby());
			params.put("flowid",flowid);
			
			//检测选择的字段，下级流程是否有超出此范围的字段
			ArrayList checkfieldidlist = new ArrayList();
			for(int i=0;i<ja1.size();i++)
			{
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				checkfieldidlist.add(id+"-");
			}
			params.put("fieldids", checkfieldidlist);
			List<Map<String,Object>> checklist = toolFlowMapper.checkSelectFieldIsWrong(params);
			String checks = "";
			for(int i=0;i<checklist.size();i++)
			{
				checks = checks + "流程 "+checklist.get(i).get("tablealias").toString()+" 使用了 原流程 :"+checklist.get(i).get("fromfield").toString()+"字段 <br>";
			}
			checks = "流程修改提取的字段中，缺少部分已经被下级流程使用字段:<br>"+checks+"请重新选择.";
			if(checklist.size()>0){
				setRtnCode("1");
				setRtnMsg(checks);
				setRtnData("null");
				return false;
			}

			if(toolFlowMapper.getTableCountByAlias2(params)>0)
			{
				setRtnCode("1");
				setRtnMsg("数据表别名不能重复，请修改。");
				setRtnData("null");
				return false;
			}
			toolFlowMapper.updateTaretTableInfo(params);
			
			//修改字段信息
			//先删除本表实体字段,删除掉不再调整范围内的字段
			toolFlowMapper.deleteFieldInfo(params);
			//添加实体字段
			for(int i=0;i<ja1.size();i++)
			{
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				if(!updateFieldInfo(targettable, fieldalias, id, ""+(i+1),""))
					writeFieldInfo(targettable, fieldalias, id, ""+(i+1),"");
			}
			toolFlowMapper.updateVirtualFieldOrder(params);
			
			//修改数据提取范围
			//先删除
			toolFlowMapper.deleteGetRange(params);
			if((beginnum!=0)||(endnum!=0))
				this.writeGetRange(flowid, beginnum, endnum);
			
			//修改条件信息
			toolFlowMapper.deleteCondition(params);
			if(condition!=null && !condition.equals(""))
				writeCondition(flowid, condition);
			
			//修改流程信息，提取操作无需修改流程信息
			toolFlowMapper.updateFlowInfo(params);
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			//需要先清除目标表
			toolShowTableData.dropTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("targettable", targettable);
			params.put("flowid", flowid);
			params.put("srctable", srctable);
			String getdatasql = toolFlowMapper.getProcGetDataSql(params);
			getdatasql = getdatasql.replace("''", "'");
			toolShowTableData.runSql(getdatasql);
			
			
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据提取流程修改成功。");
			setRtnData("["+jd.toString()+"]");
			
			jd.clear();
			ja.clear();
			ja1.clear();
			jo.clear();
						
		}catch(Exception e){
			setRtnCode("2");
			setRtnMsg("数据提取流程修改异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	private boolean flowInsertVirtualField(JSONArray ja)
	{
		try{
			JSONObject jo = ja.getJSONObject(0);
			String modelid = jo.getString("modelid").toString();
			String tablename = jo.getString("tablename").toString();
			String flowid = toolFlowMapper.getFlowIDByTable(tablename);
			if((flowid==null) || flowid.equals(""))
				flowid = "0";
			String fieldalias = jo.getString("fieldalias").toString();
			String fieldscript = jo.getString("fieldscript").toString();
			String fieldtype = jo.getString("fieldtype").toString();
			String fieldlength = jo.getString("fieldlength").toString();
			String beforefieldid = jo.getString("beforefieldid").toString();
			if((beforefieldid==null) || beforefieldid.equals(""))
				beforefieldid = "0";

			Map<String, Object> params=new HashMap<String,Object>();
			params.put("tablename", tablename);
			params.put("fieldalias", fieldalias);
			
			if(toolFlowMapper.checkSameFieldAlias(params)>0)
			{
				setRtnCode("1");
				setRtnMsg("插入虚拟列重复，请重新尝试。");
				setRtnData("null");
				return false;
			}
			
			insertVirtualField(tablename, fieldalias, fieldtype, fieldlength, fieldscript, beforefieldid);
			
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("tablename", tablename);
			jd.put("fieldalias", fieldalias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("插入虚拟列完成。");
			setRtnData("["+jd.toString()+"]");
			
			jd.clear();
			jo.clear();
			
		}catch(Exception e){
			setRtnCode("2");
			setRtnMsg("插入虚拟列异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	private boolean flowDeleteVirtualField(JSONArray ja)
	{
		try{
		JSONObject jo = ja.getJSONObject(0);
		String modelid = jo.getString("modelid").toString();
		String tablename = jo.getString("tablename").toString();
		String flowid = toolFlowMapper.getFlowIDByTable(tablename);
		if((flowid==null) || flowid.equals(""))
			flowid = "0";
		String fieldid = jo.getString("fieldid").toString();
		String entitytype = toolFlowMapper.getlFieldEntityType(fieldid);
		
		if(!entitytype.equals("0"))
		{
			setRtnCode("1");
			setRtnMsg("字段不是虚拟字段，不能删除。");
			setRtnData("null");
			return false;
		}
		
		if(toolFlowMapper.getVirtualFieldUseCount(Integer.parseInt(fieldid))>0)
		{
			setRtnCode("1");
			setRtnMsg("有其他流程使用到该列生成数据，不能删除。");
			setRtnData("null");
			return false;
		}
		
		toolFlowMapper.deleteVirtualField(fieldid);
		
		JSONObject jd = new JSONObject();
		jd.put("modelid", modelid);
		jd.put("flowid", flowid);
		jd.put("tablename", tablename);
		
		setRtnCode("0");
		setRtnMsg("虚拟列删除成功。");
		setRtnData("["+jd.toString()+"]");
		
		}catch(Exception e){
			setRtnCode("2");
			setRtnMsg("删除虚拟列异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	private boolean flowGroupData(JSONArray ja)
	{
		try{
			int nindex = 0;
			JSONObject jo = ja.getJSONObject(0);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String srctable = jo.getString("srctable").toString();
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			String condition = jo.getString("condition").toString();
			JSONArray ja1 = jo.getJSONArray("field");
			JSONArray ja2;
			if(jo.getString("collectfield")!=null)
				ja2 = jo.getJSONArray("collectfield");
			else
				ja2 = new JSONArray();
			condition = condition.replace("'", "''");
			
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("分组字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			//调整后，增加count统计，允许合计字段可以为空
			/*
			if(ja2.size()<=0){
				setRtnCode("1");
				setRtnMsg("汇总字段不能为空。");
				setRtnData("null");
				return false;
			}
			*/
			
			if((tablealias==null) || tablealias.equals(""))
			{
				setRtnCode("1");
				setRtnMsg("表别名不能为空。");
				setRtnData("null");
				return false;
			}
			
			if(toolFlowMapper.getTableCountByAlias(tablealias)>0)
			{
				setRtnCode("1");
				setRtnMsg("数据表别名不能重复，请修改。");
				setRtnData("null");
				return false;
			}
						
			String targettable = toolFlowMapper.initTargetTableName();
			String flowid = toolFlowMapper.initFlowID();
			
			System.out.println(tablealias);
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable);
			params.put("createby", getCreateby());
			toolFlowMapper.writeTaretTableInfo(params);
			
			//写入字段信息
			for(int i=0;i<ja1.size();i++)
			{
				nindex ++;
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				writeFieldInfo(targettable, fieldalias, id, ""+(i+1),"");
			}
			
			//调整后，增加count统计，允许合计字段可以为空
			nindex++;
			jo = ja1.getJSONObject(0);
			String countfieldid = jo.getString("id").toString();
			String countfieldalias = "记录个数";
			writeCountFieldInfo(targettable, countfieldalias, countfieldid, ""+nindex,"count");
			
			for(int i=0;i<ja2.size();i++)
			{
				nindex++;
				jo = ja2.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				String collecttype = jo.getString("collecttype").toString().toLowerCase();
				writeFieldInfo(targettable, fieldalias, id, ""+nindex,collecttype);
			}
			
		//	if(condition!=null && !condition.equals(""))
		//		writeCondition(flowid, condition);
			writeFlowInfo(tablealias, flowid, modelid, parentflowid, "proc_groupdata", targettable, srctable, "","");
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("targettable", targettable);
			params.put("flowid", flowid);
			params.put("srctable", srctable);
			String getdatasql = toolFlowMapper.getProcGroupDataSql(params);
			getdatasql = getdatasql.replace("''", "'");
			toolShowTableData.runSql(getdatasql);
			
			//返回
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据汇总成功。");
			setRtnData("["+jd.toString()+"]");
			
			ja1.clear();
			ja2.clear();
			jd.clear();
			jo.clear();
			
		}catch(Exception e){
			System.out.println(e.toString());
			setRtnCode("2");
			setRtnMsg("数据汇总异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	//修改汇总流程
	private boolean editflowGroupData(JSONArray ja)
	{
		try{
			int nindex = 0;
			JSONObject jo = ja.getJSONObject(0);
			String flowid = jo.getString("flowid").toString();
			String targettable = toolFlowMapper.getTableNameByFlowID(flowid);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String srctable = jo.getString("srctable").toString();
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			String condition = jo.getString("condition").toString();
		//	String field = jo.getString("field").toString();
		//	String collectfield = jo.getString("collectfield").toString();
		//	System.out.println(collectfield);
			JSONArray ja1 = jo.getJSONArray("field");
			JSONArray ja2;
			if(jo.getString("collectfield")!=null)
				ja2 = jo.getJSONArray("collectfield");
			else
				ja2 = new JSONArray();
			condition = condition.replace("'", "''");
			
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("分组字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			/*
			if(ja2.size()<=0){
				setRtnCode("1");
				setRtnMsg("汇总字段不能为空。");
				setRtnData("null");
				return false;
			}
			*/
			
			if((tablealias==null) || tablealias.equals(""))
			{
				setRtnCode("1");
				setRtnMsg("表别名不能为空。");
				setRtnData("null");
				return false;
			}
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable);
			params.put("createby", getCreateby());
			params.put("flowid", flowid);

			if(toolFlowMapper.getTableCountByAlias2(params)>0)
			{
				setRtnCode("1");
				setRtnMsg("数据表别名不能重复，请修改。");
				setRtnData("null");
				return false;
			}
			toolFlowMapper.updateTaretTableInfo(params);
			
			//判断分组字段和汇总字段是否缺少在后继流程使用的
			ArrayList checkfieldidlist = new ArrayList();
			for(int i=0;i<ja1.size();i++)
			{
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				checkfieldidlist.add(id+"-");
			}
			for(int i=0;i<ja2.size();i++)
			{
				jo = ja2.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				String collecttype = jo.getString("collecttype").toString().toLowerCase();
				checkfieldidlist.add(id+"-"+collecttype);
			}
			params.put("fieldids", checkfieldidlist);
			List<Map<String,Object>> checklist = toolFlowMapper.checkSelectFieldIsWrong(params);
			String checks = "";
			for(int i=0;i<checklist.size();i++)
			{
				checks = checks + "流程 "+checklist.get(i).get("tablealias").toString()+" 使用了 原流程 :"+checklist.get(i).get("fromfield").toString()+"字段 <br>";
			}
			checks = "流程修改提取的字段中，缺少部分已经被下级流程使用字段:<br>"+checks+"请重新选择.";
			if(checklist.size()>0){
				setRtnCode("1");
				setRtnMsg(checks);
				setRtnData("null");
				return false;
			}
			
			//先删除本表实体字段,删除掉不再调整范围内的字段
			toolFlowMapper.deleteFieldInfo(params);
			//写入字段信息
			for(int i=0;i<ja1.size();i++)
			{
				nindex ++;
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				if(!updateFieldInfo(targettable, fieldalias, id, ""+(i+1),""))
					writeFieldInfo(targettable, fieldalias, id, ""+(i+1),"");
			}
			
			//调整后，增加count统计，允许合计字段可以为空
			nindex++;
			jo = ja1.getJSONObject(0);
			String countfieldid = jo.getString("id").toString();
			String countfieldalias = "记录个数";
			writeCountFieldInfo(targettable, countfieldalias, countfieldid, ""+nindex,"count");
			
			for(int i=0;i<ja2.size();i++)
			{
				nindex++;
				jo = ja2.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				String collecttype = jo.getString("collecttype").toString().toLowerCase();
				if(!updateFieldInfo(targettable, fieldalias, id, ""+nindex,collecttype))
					writeFieldInfo(targettable, fieldalias, id, ""+nindex,collecttype);
			}
			
			toolFlowMapper.updateVirtualFieldOrder(params);
			
		//	if(condition!=null && !condition.equals(""))
		//		writeCondition(flowid, condition);
			toolFlowMapper.updateFlowInfo(params);
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			//需要先清除目标表
			toolShowTableData.dropTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("targettable", targettable);
			params.put("flowid", flowid);
			params.put("srctable", srctable);
			String getdatasql = toolFlowMapper.getProcGroupDataSql(params);
			getdatasql = getdatasql.replace("''", "'");
			toolShowTableData.runSql(getdatasql);
			
			//返回
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据汇总流程修改成功。");
			setRtnData("["+jd.toString()+"]");
			
			ja1.clear();
			ja2.clear();
			jd.clear();
			jo.clear();
			
		}catch(Exception e){
			System.out.println(e.toString());
			setRtnCode("2");
			setRtnMsg("数据汇总流程修改异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	private boolean flowMergeData(JSONArray ja)
	{
		try{
			int nindex = 0;
			JSONObject jo = ja.getJSONObject(0);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String mode = jo.getString("mode").toString();
			String srctable1 = jo.getString("srctable1").toString();
			String srctable2 = jo.getString("srctable2").toString();
			String condition = jo.getString("condition").toString();
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable1);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			condition = condition.replace("'", "''");
			
			JSONArray ja1 = jo.getJSONArray("field");
			JSONArray ja2 = jo.getJSONArray("relationfield");
			
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("合并表字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			if(ja2.size()<=0){
				setRtnCode("1");
				setRtnMsg("两个源表关联字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			String targettable = toolFlowMapper.initTargetTableName();
			String flowid = toolFlowMapper.initFlowID();
			
			System.out.println(tablealias);
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable1);
			params.put("createby", getCreateby());
			toolFlowMapper.writeTaretTableInfo(params);
			
			//写入字段信息
			for(int i=0;i<ja1.size();i++)
			{
				nindex ++;
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				writeFieldInfo(targettable, fieldalias, id, ""+(i+1),"");
			}
			
			for(int i=0;i<ja2.size();i++)
			{
				jo = ja2.getJSONObject(i);
				String id1 = jo.getString("id1").toString();
				String id2 = jo.getString("id2").toString();	
				writeRelationField(flowid,id1,id2);	
			}
			
			if(condition!=null && !condition.equals(""))
				writeCondition(flowid, condition);
			writeFlowInfo(tablealias, flowid, modelid, parentflowid, "proc_mergedata", targettable, srctable1, srctable2, mode);
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("targettable", targettable);
			params.put("flowid", flowid);
			params.put("srctable1", srctable1);
			params.put("srctable2", srctable2);
			
			String getdatasql = "";
			if(mode.equals("1")){
				getdatasql = toolFlowMapper.getProcMergeDataSql1(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
			}
			else if(mode.equals("2")){
				getdatasql = toolFlowMapper.getProcMergeDataSql2(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
			}
			else if(mode.equals("3")){
				getdatasql = toolFlowMapper.getProcMergeDataSql3(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
			}
			else if(mode.equals("4")){
				getdatasql = toolFlowMapper.getProcMergeDataSql4(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
			}
			else if(mode.equals("5")){
				getdatasql = toolFlowMapper.getProcMergeDataSql3(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
				getdatasql = toolFlowMapper.getProcMergeDataSql4(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
			}
			
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据合并完成。");
			setRtnData("["+jd.toString()+"]");
			
			jo.clear();
			ja1.clear();
			ja2.clear();
			
		}catch(Exception e){
			System.out.println(e.toString());
			setRtnCode("2");
			setRtnMsg("数据合并异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	private boolean editflowMergeData(JSONArray ja)
	{
		try{
			int nindex = 0;
			JSONObject jo = ja.getJSONObject(0);
			String flowid = jo.getString("flowid").toString();
			String targettable = toolFlowMapper.getTableNameByFlowID(flowid);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String mode = jo.getString("mode").toString();
			String srctable1 = jo.getString("srctable1").toString();
			String srctable2 = jo.getString("srctable2").toString();
			String condition = jo.getString("condition").toString();
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable1);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			condition = condition.replace("'", "''");
			
			JSONArray ja1 = jo.getJSONArray("field");
			JSONArray ja2 = jo.getJSONArray("relationfield");
			
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("合并表字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			if(ja2.size()<=0){
				setRtnCode("1");
				setRtnMsg("两个源表关联字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable1);
			params.put("createby", getCreateby());
			params.put("flowid",flowid);
			params.put("mode", mode);
			
			//检测选择的字段，下级流程是否有超出此范围的字段
			ArrayList checkfieldidlist = new ArrayList();
			for(int i=0;i<ja1.size();i++)
			{
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				checkfieldidlist.add(id+"-");
			}
			params.put("fieldids", checkfieldidlist);
			List<Map<String,Object>> checklist = toolFlowMapper.checkSelectFieldIsWrong(params);
			String checks = "";
			for(int i=0;i<checklist.size();i++)
			{
				checks = checks + "流程 "+checklist.get(i).get("tablealias").toString()+" 使用了 原流程 :"+checklist.get(i).get("fromfield").toString()+"字段 <br>";
			}
			checks = "流程修改提取的字段中，缺少部分已经被下级流程使用字段:<br>"+checks+"请重新选择.";
			if(checklist.size()>0){
				setRtnCode("1");
				setRtnMsg(checks);
				setRtnData("null");
				return false;
			}			
			
			if(toolFlowMapper.getTableCountByAlias2(params)>0)
			{
				setRtnCode("1");
				setRtnMsg("数据表别名不能重复，请修改。");
				setRtnData("null");
				return false;
			}
			toolFlowMapper.updateTaretTableInfo(params);
			
			//写入字段信息
			//先删除本表实体字段,删除掉不再调整范围内的字段
			toolFlowMapper.deleteFieldInfo(params);
			for(int i=0;i<ja1.size();i++)
			{
				nindex ++;
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				if(!updateFieldInfo(targettable, fieldalias, id, ""+(i+1),""))
					writeFieldInfo(targettable, fieldalias, id, ""+(i+1),"");
			}
			toolFlowMapper.updateVirtualFieldOrder(params);
			
			//写入关联信息
			//先删除关联字段信息
			toolFlowMapper.deleteMergeInfo(params);
			for(int i=0;i<ja2.size();i++)
			{
				jo = ja2.getJSONObject(i);
				String id1 = jo.getString("id1").toString();
				String id2 = jo.getString("id2").toString();	
				writeRelationField(flowid,id1,id2);	
			}
			
			//修改条件信息
			toolFlowMapper.deleteCondition(params);
			if(condition!=null && !condition.equals(""))
				writeCondition(flowid, condition);
			
			params.put("targettable", targettable);
			params.put("flowid", flowid);
			params.put("srctable1", srctable1);
			params.put("srctable2", srctable2);
			
			//修改流程信息，提取操作无需修改流程信息
			toolFlowMapper.updateFlowInfo(params);
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			toolShowTableData.dropTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			String getdatasql = "";
			if(mode.equals("1")){
				getdatasql = toolFlowMapper.getProcMergeDataSql1(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
			}
			else if(mode.equals("2")){
				getdatasql = toolFlowMapper.getProcMergeDataSql2(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
			}
			else if(mode.equals("3")){
				getdatasql = toolFlowMapper.getProcMergeDataSql3(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
			}
			else if(mode.equals("4")){
				getdatasql = toolFlowMapper.getProcMergeDataSql4(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
			}
			else if(mode.equals("5")){
				getdatasql = toolFlowMapper.getProcMergeDataSql3(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
				getdatasql = toolFlowMapper.getProcMergeDataSql4(params);
				getdatasql = getdatasql.replace("''", "'");
				toolShowTableData.runSql(getdatasql);
			}
			
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据合并完成。");
			setRtnData("["+jd.toString()+"]");
			
			jo.clear();
			ja1.clear();
			ja2.clear();
			
		}catch(Exception e){
			System.out.println(e.toString());
			setRtnCode("2");
			setRtnMsg("数据合并异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	private boolean flowDistinctData(JSONArray ja)
	{
		try{
			int nindex = 0;
			JSONObject jo = ja.getJSONObject(0);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String mode = jo.getString("mode").toString();
			String srctable = jo.getString("srctable").toString();
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			String condition = jo.getString("condition").toString();
			condition = condition.replace("'", "''");
			
			JSONArray ja1 = jo.getJSONArray("field");
			JSONArray ja2 = jo.getJSONArray("distinctfield");
			
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("选择字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			if(ja2.size()<=0){
				setRtnCode("1");
				setRtnMsg("排重字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			String targettable = toolFlowMapper.initTargetTableName();
			String flowid = toolFlowMapper.initFlowID();
			
			System.out.println(tablealias);
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable);
			params.put("createby", getCreateby());
			toolFlowMapper.writeTaretTableInfo(params);
			
			//写入字段信息
			for(int i=0;i<ja1.size();i++)
			{
				nindex ++;
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				writeFieldInfo(targettable, fieldalias, id, ""+nindex,"");
			}
			
			for(int i=0;i<ja2.size();i++)
			{
				jo = ja2.getJSONObject(i);
				String id = jo.getString("id").toString();	
				writeDistinctField(flowid,id);
			}
			
			if(condition!=null && !condition.equals(""))
				writeCondition(flowid, condition);
			writeFlowInfo(tablealias, flowid, modelid, parentflowid, "proc_distinctdata", targettable, srctable, "", mode);
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("targettable", targettable);
			params.put("flowid", flowid);
			params.put("srctable", srctable);
			params.put("mode", mode);
			String getdatasql = toolFlowMapper.getProcDistinctDataSql(params);
			getdatasql = getdatasql.replace("''", "'");
			toolShowTableData.runSql(getdatasql);
			
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据排重完成。");
			setRtnData("["+jd.toString()+"]");
			
			jo.clear();
			ja1.clear();
			ja2.clear();
			jd.clear();
			
		}catch(Exception e){
			System.out.println(e.toString());
			setRtnCode("2");
			setRtnMsg("数据去重异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	private boolean editflowDistinctData(JSONArray ja)
	{
		try{
			int nindex = 0;
			JSONObject jo = ja.getJSONObject(0);
			String flowid = jo.getString("flowid").toString();
			String targettable = toolFlowMapper.getTableNameByFlowID(flowid);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String mode = jo.getString("mode").toString();
			String srctable = jo.getString("srctable").toString();
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			String condition = jo.getString("condition").toString();
			condition = condition.replace("'", "''");
			
			JSONArray ja1 = jo.getJSONArray("field");
			JSONArray ja2 = jo.getJSONArray("distinctfield");
			
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("选择字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			if(ja2.size()<=0){
				setRtnCode("1");
				setRtnMsg("排重字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable);
			params.put("createby", getCreateby());
			params.put("flowid",flowid);
			
			//检测选择的字段，下级流程是否有超出此范围的字段
			ArrayList checkfieldidlist = new ArrayList();
			for(int i=0;i<ja1.size();i++)
			{
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				checkfieldidlist.add(id+"-");
			}
			params.put("fieldids", checkfieldidlist);
			List<Map<String,Object>> checklist = toolFlowMapper.checkSelectFieldIsWrong(params);
			String checks = "";
			for(int i=0;i<checklist.size();i++)
			{
				checks = checks + "流程 "+checklist.get(i).get("tablealias").toString()+" 使用了 原流程 :"+checklist.get(i).get("fromfield").toString()+"字段 <br>";
			}
			checks = "流程修改提取的字段中，缺少部分已经被下级流程使用字段:<br>"+checks+"请重新选择.";
			if(checklist.size()>0){
				setRtnCode("1");
				setRtnMsg(checks);
				setRtnData("null");
				return false;
			}
			
			if(toolFlowMapper.getTableCountByAlias2(params)>0)
			{
				setRtnCode("1");
				setRtnMsg("数据表别名不能重复，请修改。");
				setRtnData("null");
				return false;
			}
			toolFlowMapper.updateTaretTableInfo(params);
			
			//写入字段信息
			//先删除本表实体字段,删除掉不再调整范围内的字段
			toolFlowMapper.deleteFieldInfo(params);
			for(int i=0;i<ja1.size();i++)
			{
				nindex ++;
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				String fieldalias = jo.getString("fieldalias").toString();
				if(!updateFieldInfo(targettable, fieldalias, id, ""+(i+1),""))
					writeFieldInfo(targettable, fieldalias, id, ""+(i+1),"");
			}
			toolFlowMapper.updateVirtualFieldOrder(params);
			
			//排重字段
			toolFlowMapper.deleteDistinctInfo(params);
			for(int i=0;i<ja2.size();i++)
			{
				jo = ja2.getJSONObject(i);
				String id = jo.getString("id").toString();	
				writeDistinctField(flowid,id);
			}
			
			//修改条件信息
			toolFlowMapper.deleteCondition(params);
			if(condition!=null && !condition.equals(""))
				writeCondition(flowid, condition);
			
			//修改流程信息，提取操作无需修改流程信息
			params.put("mode", mode);
			toolFlowMapper.updateFlowInfo(params);
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			//需要先清除目标表
			toolShowTableData.dropTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("targettable", targettable);
			params.put("flowid", flowid);
			params.put("srctable", srctable);
			params.put("mode", mode);
			String getdatasql = toolFlowMapper.getProcDistinctDataSql(params);
			getdatasql = getdatasql.replace("''", "'");
			toolShowTableData.runSql(getdatasql);
			
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据排重完成。");
			setRtnData("["+jd.toString()+"]");
			
			jo.clear();
			ja1.clear();
			ja2.clear();
			jd.clear();
			
		}catch(Exception e){
			System.out.println(e.toString());
			setRtnCode("2");
			setRtnMsg("数据去重异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	//数据排序
	private boolean flowSortData(JSONArray ja)
	{
		try{
			int nindex = 0;
			JSONObject jo = ja.getJSONObject(0);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String srctable = jo.getString("srctable").toString();
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			JSONArray ja1 = jo.getJSONArray("field");
			
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("排序字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			if(toolFlowMapper.getTableCountByAlias(tablealias)>0)
			{
				setRtnCode("1");
				setRtnMsg("数据表别名不能重复，请修改。");
				setRtnData("null");
				return false;
			}
			
			String targettable = toolFlowMapper.initTargetTableName();
			String flowid = toolFlowMapper.initFlowID();
			
			System.out.println(tablealias);
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable);
			params.put("createby", getCreateby());
			toolFlowMapper.writeTaretTableInfo(params);
			toolFlowMapper.writeSortTableField(params);
			
			for(int i=0;i<ja1.size();i++)
			{
				nindex ++;
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				String softtype = jo.getString("mode").toString();
				writeSortField(flowid, targettable, id,softtype);
			}
			
			writeFlowInfo(tablealias, flowid, modelid, parentflowid, "proc_sortdata", targettable, srctable, "", "");
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("targettable", targettable);
			params.put("flowid", flowid);
			params.put("srctable", srctable);
			String getdatasql = toolFlowMapper.getProcGetDataSql(params);
			getdatasql = getdatasql.replace("''", "'");
			toolShowTableData.runSql(getdatasql);
			
			//建立索引
			
			//返回
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据排序成功。");
			setRtnData("["+jd.toString()+"]");
			
			jd.clear();
			ja1.clear();
			jo.clear();
			
		}catch(Exception e){
			System.out.println(e.toString());
			setRtnCode("2");
			setRtnMsg("数据排序异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	//修改数据排序流程
	private boolean editflowSortData(JSONArray ja)
	{
		try{
			int nindex = 0;
			JSONObject jo = ja.getJSONObject(0);
			String flowid = jo.getString("flowid").toString();
			String targettable = toolFlowMapper.getTableNameByFlowID(flowid);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String srctable = jo.getString("srctable").toString();
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			JSONArray ja1 = jo.getJSONArray("field");
			
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("排序字段不能为空。");
				setRtnData("null");
				return false;
			}
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable);
			params.put("createby", getCreateby());
			params.put("flowid", flowid);
			
			//修改表信息
			if(toolFlowMapper.getTableCountByAlias2(params)>0)
			{
				setRtnCode("1");
				setRtnMsg("数据表别名不能重复，请修改。");
				setRtnData("null");
				return false;
			}
			toolFlowMapper.updateTaretTableInfo(params);
			
			//修改排序字段信息
			toolFlowMapper.deleteSortInfo(params);
			for(int i=0;i<ja1.size();i++)
			{
				nindex ++;
				jo = ja1.getJSONObject(i);
				String id = jo.getString("id").toString();
				String softtype = jo.getString("mode").toString();
				writeSortField(flowid, targettable, id,softtype);
			}
			
			//修改流程信息，提取操作无需修改流程信息
			toolFlowMapper.updateFlowInfo(params);
			
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			toolShowTableData.dropTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("targettable", targettable);
			params.put("flowid", flowid);
			params.put("srctable", srctable);
			String getdatasql = toolFlowMapper.getProcGetDataSql(params);
			getdatasql = getdatasql.replace("''", "'");
			toolShowTableData.runSql(getdatasql);
			
			//建立索引
			
			//返回
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据排序流程修改成功。");
			setRtnData("["+jd.toString()+"]");
			
			jd.clear();
			ja1.clear();
			jo.clear();
			
		}catch(Exception e){
			System.out.println(e.toString());
			setRtnCode("2");
			setRtnMsg("数据排序流程修改异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	private boolean flowOverlayData(JSONArray ja)
	{
		try{
			int nindex = 0;
			JSONObject jo = ja.getJSONObject(0);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String srctable = jo.getString("srctable").toString(); 
			String overlaytable = jo.getString("overlaytable").toString();
			String condition = jo.getString("condition").toString();
			condition = condition.replace("'", "''");
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			JSONArray ja1 = jo.getJSONArray("overlaytable");
			
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("叠加表不能为空.");
				setRtnData("null");
				return false;
			}
			
			for(int i=0;i<ja1.size();i++)
			{
				jo = ja1.getJSONObject(i);
				String tablename = jo.getString("tablename").toString();
				if(tablename.equals(srctable))
				{
					setRtnCode("1");
					setRtnMsg("叠加操作副表不能与主表重复.");
					setRtnData("null");
					return false;
				}
			}
			
			String targettable = toolFlowMapper.initTargetTableName();
			String flowid = toolFlowMapper.initFlowID();
			
			System.out.println(tablealias);
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable);
			params.put("flowid", flowid);
			params.put("createby", getCreateby());
			toolFlowMapper.writeTaretTableInfo(params);
			toolFlowMapper.writeOverlayTableInfo(params);
			
			for(int i=0;i<ja1.size();i++)
			{
				nindex ++;
				jo = ja1.getJSONObject(i);
				String tablename = jo.getString("tablename").toString();
				params.put("srctable", tablename);
				writeOverlayTableInfo(flowid,tablename);
			}
			
			if(condition!=null && !condition.equals(""))
				writeCondition(flowid, condition);
			writeOverlayFieldInfo(flowid,targettable);
			
			writeFlowInfo(tablealias, flowid, modelid, parentflowid, "proc_overlaydata", targettable, srctable, "", "");
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("srctable", srctable);
			List<String> tablelist = toolFlowMapper.selectOverlayTableList(flowid);
			for(int i=0;i<tablelist.size();i++)
			{
				String curtable = tablelist.get(i);
				params.put("curtable", curtable);
				String sql = toolFlowMapper.getProcOverlayDataSql(params);
				sql = sql.replace("''", "'");
				toolShowTableData.runSql(sql);
			}
			
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据叠加成功。");
			setRtnData("["+jd.toString()+"]");
			
			jo.clear();
			ja1.clear();
			jd.clear();
			
		}catch(Exception e){
			System.out.println(e.toString());
			setRtnCode("2");
			setRtnMsg("数据叠加异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	//叠加流程修改
	private boolean editflowOverlayData(JSONArray ja)
	{
		try{
			int nindex = 0;
			JSONObject jo = ja.getJSONObject(0);
			String flowid = jo.getString("flowid").toString();
			String targettable = toolFlowMapper.getTableNameByFlowID(flowid);
			String modelid = jo.getString("modelid").toString();
			String tablealias = jo.getString("tablealias").toString();
			String srctable = jo.getString("srctable").toString(); 
			String overlaytable = jo.getString("overlaytable").toString();
			String condition = jo.getString("condition").toString();
			condition = condition.replace("'", "''");
			String parentflowid = toolFlowMapper.getFlowIDByTable(srctable);
			if((parentflowid==null) || parentflowid.equals(""))
				parentflowid = "0";
			JSONArray ja1 = jo.getJSONArray("overlaytable");
			
			if(ja1.size()<=0){
				setRtnCode("1");
				setRtnMsg("叠加表不能为空.");
				setRtnData("null");
				return false;
			}
			
			ArrayList checktablelist = new ArrayList();	//用以检测表字段是否匹配使用
			checktablelist.add(srctable);
			for(int i=0;i<ja1.size();i++)
			{
				jo = ja1.getJSONObject(i);
				String tablename = jo.getString("tablename").toString();
				if(tablename.equals(srctable))
				{
					setRtnCode("1");
					setRtnMsg("叠加操作副表不能与主表重复.");
					setRtnData("null");
					return false;
				}
				checktablelist.add(tablename);
			}
			
		//	String targettable = toolFlowMapper.initTargetTableName();
		//	String flowid = toolFlowMapper.initFlowID();
			
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("targettable", targettable);
			params.put("tablealias", tablealias);
			params.put("srctable", srctable);
			params.put("flowid", flowid);
			params.put("createby", getCreateby());
			params.put("tables", checktablelist);
			
			List<Map<String,Object>> checklist = toolFlowMapper.checkSelectTableIsWrong(params);
			String checks = "";
			for(int i=0;i<checklist.size();i++)
			{
				checks = checks + "流程 "+checklist.get(i).get("tablealias").toString()+" 使用了 原流程 :"+checklist.get(i).get("fromfield").toString()+"字段 <br>";
			}
			checks = "流程修改提取的字段中，缺少部分已经被下级流程使用字段:<br>"+checks+"请重新选择.";
			if(checklist.size()>0){
				setRtnCode("1");
				setRtnMsg(checks);
				setRtnData("null");
				return false;
			}
			
			//检测修改重名问题
			if(toolFlowMapper.getTableCountByAlias2(params)>0)
			{
				setRtnCode("1");
				setRtnMsg("数据表别名不能重复，请修改。");
				setRtnData("null");
				return false;
			}
			toolFlowMapper.updateTaretTableInfo(params);
			
			//更新叠加表信息
			toolFlowMapper.deleteOverlayInfo(params);
			toolFlowMapper.writeOverlayTableInfo(params);
			for(int i=0;i<ja1.size();i++)
			{
				nindex ++;
				jo = ja1.getJSONObject(i);
				String tablename = jo.getString("tablename").toString();
				params.put("srctable", tablename);
				writeOverlayTableInfo(flowid,tablename);
			}
			
			//修改条件信息
			toolFlowMapper.deleteCondition(params);
			if(condition!=null && !condition.equals(""))
				writeCondition(flowid, condition);
			
			//修改字段信息
			toolFlowMapper.deleteOverLayFieldInfo(params);
			editOverlayFieldInfo(flowid,targettable);
			toolFlowMapper.updateVirtualFieldOrder(params);
			
			params.put("srctable2", srctable);
			toolFlowMapper.updateFlowInfo(params);
			String createtablesql = toolFlowMapper.getCreateTable(targettable);
			toolShowTableData.dropTable(targettable);
			toolShowTableData.runSql(createtablesql);
			
			params.put("srctable", srctable);
			List<String> tablelist = toolFlowMapper.selectOverlayTableList(flowid);
			for(int i=0;i<tablelist.size();i++)
			{
				String curtable = tablelist.get(i);
				params.put("curtable", curtable);
				String sql = toolFlowMapper.getProcOverlayDataSql(params);
				sql = sql.replace("''", "'");
				toolShowTableData.runSql(sql);
			}
			
			JSONObject jd = new JSONObject();
			jd.put("modelid", modelid);
			jd.put("flowid", flowid);
			jd.put("flowname", tablealias);
			jd.put("tablename", targettable);
			jd.put("tablealias", tablealias);
			jd.toString();
			
			setRtnCode("0");
			setRtnMsg("数据叠加成功。");
			setRtnData("["+jd.toString()+"]");
			
			jo.clear();
			ja1.clear();
			jd.clear();
			
		}catch(Exception e){
			System.out.println(e.toString());
			setRtnCode("2");
			setRtnMsg("数据叠加异常，请重新尝试。");
			setRtnData("null");
			return false;
		}
		return true;
	}
	
	//写入字段信息
	private boolean writeGetRange(String flowid,int beginnum,int endnum)
	{
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		params.put("beginnum", beginnum);
		params.put("endnum", endnum);
		if(toolFlowMapper.writeGetRange(params)>0)
			return true;
		return false;
	}
	
	//写入字段信息
	private boolean writeFieldInfo(String targettable,String fieldalias,String fromfieldid,String fieldorder,String collecttype)
	{
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("targettable", targettable);
		params.put("fieldalias", fieldalias);
		params.put("fromfieldid", fromfieldid);
		params.put("fieldorder", fieldorder);
		String newfieldalias = toolFlowMapper.getFieldAlias(params);
		params.put("collecttype",collecttype);
		params.put("fieldalias", newfieldalias);
		if(toolFlowMapper.writeFieldInfo(params)>0)
			return true;
		return false;
	}
	
	//写入字段信息
	private boolean writeCountFieldInfo(String targettable,String fieldalias,String fromfieldid,String fieldorder,String collecttype)
	{
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("targettable", targettable);
		params.put("fieldalias", fieldalias);
		params.put("fromfieldid", fromfieldid);
		params.put("fieldorder", fieldorder);
		String newfieldalias = toolFlowMapper.getFieldAlias(params);
		params.put("collecttype",collecttype);
		params.put("fieldalias", newfieldalias);
		if(toolFlowMapper.writeGroupCountFieldInfo(params)>0)
			return true;
		return false;
	}
	
	//修改字段信息
	private boolean updateFieldInfo(String targettable,String fieldalias,String fromfieldid,String fieldorder,String collecttype)
	{
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("targettable", targettable);
		params.put("fieldalias", fieldalias);
		params.put("fromfieldid", fromfieldid);
		params.put("fieldorder", fieldorder);
		String newfieldalias = toolFlowMapper.getFieldAlias(params);
		params.put("collecttype",collecttype);
		if(toolFlowMapper.updateFieldInfo(params)>0)
			return true;
		return false;
	}
	
	private boolean writeFlowInfo(String flowname,String flowid,String modelid,String parentflowid,String flowscript,String targettable,String srctable1,String srctable2,String mode)
	{
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		params.put("flowname", flowname);
		params.put("parentflowid", parentflowid);
		params.put("flowscript", flowscript);
		params.put("targettable", targettable);
		params.put("srctable1", srctable1);
		params.put("srctable2", srctable2);
		params.put("mode", mode);
		if(toolFlowMapper.writeFlowInfo(params)>0)
			return true;
		return false;
	}
	
	private boolean writeCondition(String flowid, String condition){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		params.put("condition", condition);
		if(toolFlowMapper.writeConditionInfo(params)>0)
			return true;
		return false;
	}
	
	private boolean writeRelationField(String flowid,String id1,String id2){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		params.put("id1", id1);
		params.put("id2", id2);
		if(toolFlowMapper.writeRelationField(params)>0)
			return true;
		return false;
	}
	
	private boolean writeDistinctField(String flowid,String id){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		params.put("id", id);
		if(toolFlowMapper.writeDistinctField(params)>0)
			return true;
		return false;
	}
	
	private boolean writeOverlayTableInfo(String flowid,String srctable){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		params.put("srctable", srctable);
		if(toolFlowMapper.getCountOverlayTable(params)==0)
		{
			if(toolFlowMapper.writeOverlayTableInfo(params)>0)
				return true;
		}
		else
			return true;
		return false;
	}
	
	private boolean writeOverlayFieldInfo(String flowid,String targettable){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		params.put("targettable", targettable);
		if(toolFlowMapper.writeOverlayFieldInfo(params)>0)
			return true;
		return false;
	}
	
	private boolean editOverlayFieldInfo(String flowid,String targettable){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		params.put("targettable", targettable);
		toolFlowMapper.editOverlayFieldInfo(params);
		return true;
	}
	
	public boolean writeSortField(String flowid, String targettable, String id,String softtype){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		params.put("targettable", targettable);
		params.put("softmode", softtype);
		params.put("fieldid", id);
		if(toolFlowMapper.writeSortField(params)>0)
			return true;
		return false;
	}
	
	private boolean insertVirtualField(String tablename, String fieldalias, String fieldtype, String fieldlength, String fieldscript, String beforefieldid)
	{
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("tablename", tablename);
		int fieldorder;
		if(!beforefieldid.equals("0"))
		{
			fieldorder = toolFlowMapper.getOrderByFieldId(beforefieldid);
			params.put("fieldorder", fieldorder);
			toolFlowMapper.updateFieldOrder(params);
		}
		else
			fieldorder = toolFlowMapper.getMaxOrderByTable(tablename);
		int fieldnum = toolFlowMapper.getFieldCountByTable(tablename);
		
		params.put("fieldnum", fieldnum);
		params.put("fieldalias", fieldalias);
		params.put("fieldtype", fieldtype);
		params.put("fieldlength", fieldlength);
		params.put("fieldorder", fieldorder);
		params.put("fieldscript", fieldscript);
		toolFlowMapper.insertVirtualField(params);
		
		return true;
	}
}
