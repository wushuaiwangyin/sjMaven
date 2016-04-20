package com.krm.dbaudit.web.modeltool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
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
import com.krm.dbaudit.web.modeltool.model.ToolTable;
import com.krm.dbaudit.web.modeltool.model.ToolTableField;
import com.krm.dbaudit.web.modeltool.oracle.dao.ToolShowTableData;

/**
* @author chenwei on 2015-11-13
* 分析工具操作接口
*/

@Service("ToolOperateService")
public class ToolOperateService {
	@Resource
	private ToolOperateMapper toolOperateMapper;
	@Resource
	private ToolShowTableData toolShowTableData;
	@Resource
	private ToolFlowMapper toolFlowMapper;
	
	private int nTableIndex = 0;	//递归循环使用，对有序的List创建树形结构
	
	/**
	 * 格式化返回数据json
	* @param 
	* @return json
	 */
	public String rtnmsg(String code,String msg,String data){
		System.out.println("[{\"return_flag\":\""+code+"\"," +
				"\"return_msg\":\""+msg+"\","+
				"\"return_data\":"+data+"}]");
		return "[{\"return_flag\":\""+code+"\"," +
				"\"return_msg\":\""+msg+"\","+
				"\"return_data\":"+data+"}]";
	}
	
	public String getTableTree(Map<String, Object> params) {
		
		List<ToolTable> tablelist = toolOperateMapper.getTableTree(params);
		nTableIndex = 0;
		
		String s = createTableTree(tablelist,"0").toString();
	
		return s;
	}
	
	//递归，生成数据表树形结构
	public JSONArray createTableTree(List<ToolTable> tableList,String parent){
		String s = "";
		JSONArray ja = new JSONArray();
		int nchildnum = 0; 
		for(int i=nTableIndex;i<tableList.size();i++)
		{	
			ToolTable tooltable = tableList.get(i);
			String tablename = tooltable.getTableName();
			String tableparent = tooltable.getTableParent();
			if(parent.equals(tableparent))
			{
				JSONObject jo = new JSONObject();
				String type = tooltable.getTableFlag().toString();
				jo.put("name", tooltable.getTableName());
				jo.put("text", tooltable.getTableAlias());
				jo.put("actionid", tooltable.getActionid());
				jo.put("type", type);
				if(!type.equals("0"))
					jo.put("iconCls", "tree-file");				
				nTableIndex++;
				JSONArray ja1 = createTableTree(tableList,tablename);
				if(ja1.size()>0)
				{
					jo.put("state", "closed");
					jo.put("children", ja1);
				}
				ja.add(jo);
			}
			else
			{
				break;
			}
			i = nTableIndex-1;
		}
		return ja;
	}
	
	public String getTableField(Map<String, Object> params) {
		List<ToolTableField> fieldlist = toolOperateMapper.getTableField(params);
		JSONArray ja = new JSONArray();
		for(int i=0;i<fieldlist.size();i++)
		{
			ToolTableField fielditem = fieldlist.get(i);	
			JSONObject jo = new JSONObject();
			jo.put("id", fielditem.getId().toString());
			//转大写，是因为 数据库 select列别名定义时，将大小写全部转成了大写，导致列字段别名中包含字母的项匹配不上，数据显示不出
			jo.put("fieldname", fielditem.getFieldName().toUpperCase());
			//转大写，是因为 数据库 select列别名定义时，将大小写全部转成了大写，导致列字段别名中包含字母的项匹配不上，数据显示不出
			jo.put("fieldalias", fielditem.getFieldAlias().toString().toUpperCase());
			jo.put("fieldentitytype", fielditem.getFieldEntityType());
			jo.put("fieldtype", fielditem.getFieldType().toString());
			jo.put("fieldlengt",fielditem.getFieldLength());
			ja.add(jo);
		}
		String s = ja.toString();
		ja.clear();
		System.out.println(s);
		return s;
	}
	
	public String getTableCubeField(Map<String, Object> params){
		String parenttable = toolOperateMapper.getParentGroupTablename(params);
		params.put("tablename", parenttable);
		return getTableField(params);
	}

	public String getTableFieldHtml(Map<String, Object> params) {
		String s = "<tr>";
		List<ToolTableField> fieldlist = toolOperateMapper.getTableFieldByZhTableName(params);
		for(ToolTableField fielditem : fieldlist){
			s += "<th data-field=\""+fielditem.getFieldName().toUpperCase()+"\" data-sortable=\"true\">"+fielditem.getFieldAlias()+"</th>";
		}
		s += "</tr>";
		return s;
	}

	public String getTableFieldOption(Map<String, Object> params) {
		String s = "";
		List<ToolTableField> fieldlist = toolOperateMapper.getTableFieldByZhTableName(params);
		for(ToolTableField fielditem : fieldlist){
			s += "<option value=\""+fielditem.getFieldName()+"\">"+fielditem.getFieldAlias()+"</option>";
		}
		return s;
	}
	
	//删除指定分析表,操作界面需要先确定是否为分析表
	public String deleteTable(Map<String, Object> params){
		try{
			String tablename = params.get("tablename").toString();
			//判断表是否存在
			if(toolOperateMapper.getChildTableCount(tablename)>0)
			{
				return rtnmsg("1","有其他流程使用该表，不能删除。","null");
			}
			else
			{
				toolOperateMapper.deleteTable(tablename);
				toolShowTableData.dropTable(tablename);
				return rtnmsg("0","分析表删除成功。","null");
			}
		}
		catch(Exception e){
			return rtnmsg("2","分析表删除异常，请重新尝试。","null");
		}
	}
	
	//删除指定分析表,操作界面需要先确定是否为分析表
	public String updateTableName(Map<String, Object> params){
		try{
			if(toolFlowMapper.getTableCountByAlias(params.get("tablealias").toString())>0)
				return rtnmsg("1","表名已经存在，不允许重复，修改失败。","null");
			else
			{
				toolOperateMapper.updateTableName(params);
				toolOperateMapper.updateFlowName(params);
				return rtnmsg("0","修改成功","null");
			}
		}
		catch(Exception e){
			return rtnmsg("2","表名修改异常，请重新尝试。","null");
		}
	}
	
	//表头拖拽位置，flowid,beforefieldid,tablename
	public String moveField(Map<String, Object> params){
		try{
			int fieldorder = 1;
			if(!params.get("beforefieldid").equals("0"))
				fieldorder = toolFlowMapper.getOrderByFieldId(params.get("beforefieldid").toString());
			params.put("fieldorder", fieldorder);
			toolFlowMapper.updateFieldOrder(params);
			toolOperateMapper.updateFieldOrder(params);
			return rtnmsg("0","修改成功","null");
		}
		catch(Exception e){
			return rtnmsg("2","修改异常，请重新尝试。","null");
		}
	}
	
	/**
	 * 提取流程信息
	* @param id
	* @return json
	 */
	public String geteditflowinfo(Map<String, Object> params){
		String tablename = params.get("tablename").toString();
		String flowid = toolFlowMapper.getFlowIDByTable(tablename);
		params.put("flowid", flowid);
		
		ToolFlow flow = toolOperateMapper.getFlowById(params);
		
		JSONObject jo = new JSONObject();
		jo.put("action_id", flow.getWebAction());
		
		JSONArray jadata = new JSONArray();		//存放action_data域
		JSONObject jodata = new JSONObject();	//存放action_data域数据
		jodata.put("flowid", flow.getId());
		jodata.put("tablename", flow.getTargetTable());
		jodata.put("tablealias", flow.getFlowName());
		jodata.put("srctable1", flow.getSourceTable1());
		jodata.put("srctable2", flow.getSourceTable2());
		jodata.put("srctablename1", flow.getSourceTable1Name());
		jodata.put("srctablename2", flow.getSourceTable2Name());
		jodata.put("field", (JSONArray) JSON.toJSON(toolOperateMapper.getToolDeffieldInfo(params)));
		
		//提取条件
		String conditon = toolOperateMapper.getToolDefFlowConditionInfo(params);
		if(conditon!=null)
			jodata.put("contition", conditon);
		else
			jodata.put("contition", "");
		
		List<Map<String,Object>> list;
		
		JSONArray jalist = new JSONArray();
		JSONObject jolist = new JSONObject();
		
		if(flow.getWebAction().equals("get_data")){
			//数据提取相关信息	
			list = toolOperateMapper.getToolGetRangeInfo(params);
			if(list.size()>0)
			{
				list.get(0).put("firstnum",list.get(0).get("firstnum").equals("0")? "":list.get(0).get("firstnum")); 
				list.get(0).put("endnum",list.get(0).get("endnum").equals("0")? "":list.get(0).get("endnum"));
			}
			else
			{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("firstnum","");
				map.put("endnum", "");
				list.add(map);
			}
			jalist = (JSONArray) JSON.toJSON(list);
			jodata.put("getinfo", jalist);
		}
		else if(flow.getWebAction().equals("group_data")){
		
			//汇总信息
			list = toolOperateMapper.getToolGroupFieldInfo(params);
			jolist.put("groupfield", (JSONArray) JSON.toJSON(list));
			
			list = toolOperateMapper.getToolCollectFieldInfo(params);
			jolist.put("collectfield", (JSONArray) JSON.toJSON(list));
			
			jalist.add(jolist);
			jodata.put("groupinfo", jalist);
		}
		
		else if(flow.getWebAction().equals("distinct_data")){
			//汇总信息
			list = toolOperateMapper.getToolDistinctFieldInfo(params);
			jolist.put("distinctfield", (JSONArray) JSON.toJSON(list));
			jolist.put("mode", flow.getProcMode());
			jalist.add(jolist);
			jodata.put("distinctinfo", jalist);
		}
		
		else if(flow.getWebAction().equals("sort_data")){
			list = toolOperateMapper.getToolSortFieldInfo(params);
			for(int i=0;i<list.size();i++)
			{
				Map<String,Object> map = list.get(i);
				map.put("mode", map.get("sortmode"));
				map.remove("sortmode");
				list.set(i, map);
			}
			jolist.put("sortfield", (JSONArray) JSON.toJSON(list));
			jalist.add(jolist);
			jodata.put("sortinfo", jalist);
		}
		
		else if(flow.getWebAction().equals("merge_data")){
			list = toolOperateMapper.getToolMergeFieldInfo(params);
			jolist.put("relationfield", (JSONArray) JSON.toJSON(list));
			jolist.put("mode", flow.getProcMode());
			jalist.add(jolist);
			jodata.put("mergeinfo", jalist);
		}
		else if(flow.getWebAction().equals("overlay_data")){
			list = toolOperateMapper.getToolOverlayFieldInfo(params);
			list.remove(0);
			jalist.add(jolist);
			jolist.put("overlaytable", (JSONArray) JSON.toJSON(list));
			jodata.put("overlayinfo", jalist);
		}
		
		jadata.add(jodata);
		jo.put("action_data", jadata);
		
		String s = "["+jo.toString()+"]";
		
		jo.clear();
		jodata.clear();
		jadata.clear();
		jolist.clear();
		jalist.clear();
				
		return s;
	}
	
	/**
	 * 提取所有相关子流程
	* @param id
	* @return json
	 */
	public String getChildFlow(String flowid)
	{
		List<ToolFlow> flowlist = new ArrayList<ToolFlow>();
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("flowid", flowid);
		
		ToolFlow flow = toolOperateMapper.getFlowById(params);
		flowlist.add(flow);		//添加本级
		
		//获取下级所有流程
		params.put("targettable", flow.getTargetTable());
		String s = toolOperateMapper.getFlowChildTree(params);
		
		StringTokenizer line=new StringTokenizer(s,";");
		while(line.hasMoreTokens()){
			ToolFlow childflow = new ToolFlow();
			List<String> itemlist=new ArrayList<String>();
			StringTokenizer item=new StringTokenizer(line.nextToken(),",");
			while(item.hasMoreTokens()){
        		itemlist.add(item.nextToken());
        	}
			if(itemlist.size()>=7)
			{
				childflow.setId(Long.parseLong(itemlist.get(0).toString()));
				childflow.setFlowName(itemlist.get(1).toString());
				childflow.setProcScript(itemlist.get(2).toString());
				childflow.setTargetTable(itemlist.get(3).toString());
				childflow.setSourceTable1(itemlist.get(4).toString());
				childflow.setSourceTable2(itemlist.get(5).toString());
				childflow.setProcMode(itemlist.get(6).toString());
				flowlist.add(childflow);
			}
		}
		
		return "";
	}
}
