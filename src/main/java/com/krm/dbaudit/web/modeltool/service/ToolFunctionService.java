package com.krm.dbaudit.web.modeltool.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.krm.dbaudit.web.modeltool.mapper.ToolFunctionMapper;
import com.krm.dbaudit.web.modeltool.model.ToolFunction;
import com.krm.dbaudit.web.modeltool.model.ToolFunctionParam;

/**
* @author chenwei on 2016-1-12
*/

@Service("toolFunctionService")
public class ToolFunctionService {
	@Resource
	private ToolFunctionMapper toolFunctionMapper;
	
	private String rtnCode;
	private String rtnMsg;
	private String rtnData;
	
	private int nFunIndex;
	
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
	
	public String getFunctionList(Map<String, Object> params){
		List<ToolFunction> list = toolFunctionMapper.listFunction();
		nFunIndex = 0;
		String s = createFunctionTree(list,0).toString();
		return s;
	}
	
	public JSONArray createFunctionTree(List<ToolFunction> list,long parent){
		String s = "";
		JSONArray ja = new JSONArray();
		int nchildnum = 0; 
		for(int i=nFunIndex;i<list.size();i++)
		{
			ToolFunction fun = list.get(i);
			long id = fun.getId();
			long parentid = fun.getParentId();
			if(parentid == parent)
			{
				JSONObject jo = new JSONObject();
				jo.put("name", fun.getFunName());
				jo.put("text", fun.getFunName());
				jo.put("funscript", fun.getFunScript());
				jo.put("type", fun.getType());
				jo.put("funmemo", fun.getFunMemo());
				if(fun.getType()!=0)
					jo.put("iconCls", "tree-file");		
				nFunIndex++;
				JSONArray ja1 = createFunctionTree(list,id);
				if(ja1.size()>0)
				{
					jo.put("state", "closed");
					jo.put("children", ja1);
				}
				ja.add(jo);
			}
			else
				break;
			i=nFunIndex-1;
		}
		return ja;
	}
	
	public String checkFunction(Map<String, Object> params){
		String s = toolFunctionMapper.checkFunction(params);
		if(s.subSequence(0, 5).equals("wrong"))
			return rtnmsg("1","公式错误："+s.replace("wrong:",""),"null");
		else
			return rtnmsg("0","公式校验通过","null");
	}
	
	public String getFunctionInfo(Map<String, Object> params){
		ToolFunction fun = toolFunctionMapper.getFunInfoById(params);
		List <ToolFunctionParam> list = toolFunctionMapper.getFunctionParams(params);
		JSONObject jo = new JSONObject();
		jo.put("id",fun.getId());
		jo.put("funname", fun.getFunName());
		jo.put("funscript", fun.getFunScript());
		jo.put("funmemo", fun.getFunMemo());
		JSONArray ja1 = new JSONArray();
		for(int i=0;i<list.size();i++)
		{
			ToolFunctionParam item = list.get(i);
			JSONObject jo1 = new JSONObject();
			jo1.put("paramid", item.getId());
			jo1.put("paramindex", item.getParamIndex());
			jo1.put("paramname", item.getParamName());
			jo1.put("paramtype",item.getParamType());
			jo1.put("parammemo",item.getParamMemo());
			ja1.add(jo1);
		}
		jo.put("params", ja1);
		return "["+jo.toString()+"]";
	}
}
