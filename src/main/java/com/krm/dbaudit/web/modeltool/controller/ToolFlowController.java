package com.krm.dbaudit.web.modeltool.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.modeltool.service.ToolFlowService;
import com.krm.dbaudit.web.modeltool.service.ToolModelService;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;

/**
* @author chenwei on 2015-11-05
*/

@Controller
@RequestMapping("model/flow")
public class ToolFlowController extends BaseController{
	private String BASE_PATH = "model/tool/";
	
	@Resource
	private ToolFlowService toolFlowService;
	
	@Resource
	private ToolModelService toolModelService;
	
	/**
	 * 跳转到分析工具
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toModelBase(Model model){
		return BASE_PATH + "mindexdata";
	}
	
	/**
	 * 分析工具加工流程处理
	* @param json
	* @return json
	 */
	@RequestMapping(value="doflow")
	public void doflow(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		params.put("createby",SysUserUtils.getCacheLoginUser().getId());
		String s = toolFlowService.doFlow(params);
		ResponseUtils.renderJson(response, s);
	}
	
	/**
	 * 数据关联界面
	* @param
	* @return
	 */
	@RequestMapping(value="association")
	public String association(@RequestParam Map<String, Object> params,Model model){
		return BASE_PATH + "association";
	}
	
	/**
	 * 数据关联界面
	* @param
	* @return
	 */
	@RequestMapping(value="condition")
	public String condition(@RequestParam Map<String, Object> params,Model model){
		return BASE_PATH + "condition";
	}
	
	/**
	 * 数据关联界面
	* @param
	* @return
	 */
	@RequestMapping(value="distinct")
	public String distinct(@RequestParam Map<String, Object> params,Model model){
		return BASE_PATH + "distinct";
	}
	
	/**
	 * 数据叠加
	* @param
	* @return
	 */
	@RequestMapping(value="add")
 	public String overlay(@RequestParam Map<String, Object> params,Model model){
 		return  BASE_PATH + "add";
 	}
	
	/**
	 * 历史流程
	* @param
	* @return
	 */
 	@RequestMapping(value="history")
 	public String history(@RequestParam Map<String, Object> params,Model model){
 		return BASE_PATH + "history";
 	}
	
	/**
	 * 保存流程
	* @param
	* @return
	 */
	@RequestMapping(value="saveflow")
	public @ResponseBody Integer saveflow(@RequestParam Map<String, Object> params)  throws UnsupportedEncodingException
	{
		/*
		if(params.get("modelname")!=null)
		{
			System.out.println(params.get("modelname").toString());
			String s = new String(((String) params.get("modelname")).getBytes("ISO-8859-1"),"UTF-8");
			s = java.net.URLDecoder.decode(s, "UTF-8");
			params.put("modelname",s);
			System.out.println(params.get("modelname").toString());
		}
		*/
		params.put("createuser",SysUserUtils.getCacheLoginUser().getId()+","+SysUserUtils.getCacheLoginUser().getName());
		if(toolModelService.saveModel(params))
			return 1;
		return 0;
	}
}
