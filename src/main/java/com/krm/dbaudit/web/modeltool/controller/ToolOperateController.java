package com.krm.dbaudit.web.modeltool.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.excel.ExcelUtils;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.modeltool.service.ToolFlowService;
import com.krm.dbaudit.web.modeltool.service.ToolOperateService;
import com.krm.dbaudit.web.modeltool.service.ToolShowDataService;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;
import com.krm.dbaudit.web.verify.model.OutData;

/**
* @author chenwei on 2015-11-17
*/

@Controller
@RequestMapping("model/tool")
public class ToolOperateController {
	private String BASE_PATH = "model/tool/";
	
	@Resource
	private ToolOperateService toolOperateService;
	
	@Resource
	private ToolFlowService toolFlowService;
	
	@Resource
	private ToolShowDataService toolShowDataService;
	
	public ToolShowDataService getToolShowDataService() {
		return toolShowDataService;
	}

	public void setToolShowDataService(ToolShowDataService toolShowDataService) {
		this.toolShowDataService = toolShowDataService;
	}

	@RequestMapping
	public String toModelBase(Model model){
		return BASE_PATH + "mindexdata";
	}
	
	/**
	 * 分析工具树形结构
	* @param 
	* @return json
	 */
	@RequestMapping(value="gettabletree")
	public void gettabletree(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		params.put("createBy", SysUserUtils.getCacheLoginUser().getId());
		String s = toolOperateService.getTableTree(params);
		ResponseUtils.renderJson(response, s);
	}
	
	/**
	 * 分析工具获取表字段信息
	* @param 
	* @return json
	 */
	@RequestMapping(value="gettablefield")
	public void gettablefield(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		String s = toolOperateService.getTableField(params);
		ResponseUtils.renderJson(response, s);
	}
	
	/**
	 * 分析工具获取表字段信息
	* @param 
	* @return json
	 */
	@RequestMapping(value="gettablecubefield")
	public void gettablecubefield(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		String s = toolOperateService.getTableCubeField(params);
		ResponseUtils.renderJson(response, s);
	}
	
	/**
	 * 删除分析表
	* @param 
	* @return json
	 */
	@RequestMapping(value="deletetable")
	public void deletetable(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		String s = toolOperateService.deleteTable(params);
		ResponseUtils.renderJson(response, s);
	}
	
	/**
	 * 分析工具修改分析表别名
	* @param 表名,表别名
	* @return json
	 */
	@RequestMapping(value="updatetablename")
	public void updatetablename(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		/*
		if(params.get("tablealias")!=null){
			String paramsTrans = new String(((String) params.get("tablealias")).getBytes("ISO-8859-1"),"UTF-8");
			paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
			params.put("tablealias", paramsTrans);
		}
		*/
		String s = toolOperateService.updateTableName(params);
		ResponseUtils.renderJson(response, s);
	}
	
	/**
	 * 分析工具修改字段排序
	* @param 表名,fieldid,beforefieldid
	* @return json
	 */
	@RequestMapping(value="movefieldorder")
	public void moveFieldOrder(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		if(params.get("beforefieldid")==null)
			params.put("beforefieldid","0");
		String s = toolOperateService.moveField(params);
		ResponseUtils.renderJson(response, s);
	}
	
	@RequestMapping(value="gettablefieldhtml")
	public void gettablefieldhtml(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			for (String key : params.keySet())
			{
				String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"),"UTF-8");
				paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
				map.put(key, paramsTrans);
			}
			if (params.containsKey("sortC"))
			{
				map.put("sortC", StringConvert.camelhumpToUnderline(params.get(
						"sortC").toString()));
			}
			String s = toolOperateService.getTableFieldHtml(map);
			ResponseUtils.renderJson(response, s);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="gettablefieldoption")
	public void gettablefieldoption(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			for (String key : params.keySet())
			{
				String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"),"UTF-8");
				paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
				map.put(key, paramsTrans);
			}
			if (params.containsKey("sortC"))
			{
				map.put("sortC", StringConvert.camelhumpToUnderline(params.get(
						"sortC").toString()));
			}
			String s = toolOperateService.getTableFieldOption(map);
			ResponseUtils.renderJson(response, s);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 分析工具获取表数据
	* @param 
	* @return json
	 */
	@RequestMapping(value="showtable")
	public void gettabledata(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		if(params.get("condition")==null)
			params.put("condition", "");
		else
		{
			String s = new String(((String) params.get("condition")).getBytes("ISO-8859-1"),"UTF-8");
			s = java.net.URLDecoder.decode(s, "UTF-8");
			params.put("condition",s);
		}
		if(params.get("serialstr")==null)
			params.put("serialstr", "");
		if(params.get("rows")==null)
			params.put("rows", 50);
		if(params.get("page")==null)
			params.put("page", 1);
		String s = toolShowDataService.getShowTableData(params);
		ResponseUtils.renderJson(response, s);
	}
	
	/**
	 * 分析工具数据钻取显示数据
	* @param 
	* @return json
	 */
	@RequestMapping(value="showtablecube")
	public void showtablecube(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		
		//转码
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
			    	 {
			    		 String s = new String(((String) params.get(key)).getBytes("ISO-8859-1"),"UTF-8");
			    		 s = java.net.URLDecoder.decode(s, "UTF-8");
			    		 params.put(key,s);
			    	 }
		    	 }
		     }
		}
		
		if(params.get("rows")==null)
			params.put("rows", 50);
		if(params.get("page")==null)
			params.put("page", 1);
		String s = toolShowDataService.getShowTableCubeData(params);
		ResponseUtils.renderJson(response, s);
	}
	
	@RequestMapping(value="showtablebyzhname")
	public void gettabledatabyzhname(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			for (String key : params.keySet())
			{
				String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"),"UTF-8");
				paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
				map.put(key, paramsTrans);
			}
			if (params.containsKey("sortC"))
			{
				map.put("sortC", StringConvert.camelhumpToUnderline(params.get(
						"sortC").toString()));
			}
			
			//把中文表名转换为英文表名
			String tablename = toolShowDataService.getTableNameByZhTableName(map);
			map.put("tablename", tablename);
			
			List list = toolShowDataService.findPageTableData(map);
			String str = JSON.toJSONString(list);
			ResponseUtils.renderJson(response, str);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="geteditflowinfo")
	public void geteditflowinfo(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		
		String s = toolOperateService.geteditflowinfo(params);
	//	String s = toolShowDataService.getShowTableData(params);
		ResponseUtils.renderJson(response, s);
	}
	
	@RequestMapping(value="refreshflowdata")
	public void refreshflowdata(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		
		String targettable = params.get("tablename").toString();
		String s = toolFlowService.runChildFlow(targettable);
		ResponseUtils.renderJson(response, s);
	}
}
