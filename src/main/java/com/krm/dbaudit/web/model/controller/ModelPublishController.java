package com.krm.dbaudit.web.model.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.excel.ExcelUtils;
import com.krm.dbaudit.common.utils.StringConvert;

import com.krm.dbaudit.web.model.model.ModelApprove;
import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.model.service.ModelApproveService;
import com.krm.dbaudit.web.model.service.ModelBaseService;
import com.krm.dbaudit.web.model.service.ModelDealService;
import com.krm.dbaudit.web.model.service.ModelPropertyService;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* @author chenwei on 2015-11-11
*/

@Controller
@RequestMapping("model/publish")
public class ModelPublishController  extends BaseController {
private String BASE_PATH = "model/base/";
	
	@Resource
	private ModelBaseService modelBaseService;
	
	@Resource
	private ModelDealService modelDealService;
	
	@Resource
	private ModelPropertyService modelPropertyService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String tomodelPublish(Model model){
		List<ModelProperty> properties = modelPropertyService.findAllProperty(); 
		model.addAttribute("ps", properties);
		return BASE_PATH + "model-publish";
	}
	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		if(params.containsKey("sortC")){
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		if(params.get("name")!=null)
		{
			String s = new String(((String) params.get("name")).getBytes("ISO-8859-1"),"UTF-8");
			s = java.net.URLDecoder.decode(s, "UTF-8");
			params.put("name",s);
		}
		long statuses[] = {6,7};
		params.put("statuses", statuses);
		PageInfo<ModelBase> page = modelBaseService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	/**
	 * 验证界面
	* @param
	* @return
	 */
	@RequestMapping(value="deal",method=RequestMethod.POST)
	public String deal(@RequestParam(value = "ids[]") Long[] ids,Model model){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < ids.length; i++){
			sb.append(ids[i].toString()+",");
		}
		model.addAttribute("ids", sb.substring(0, sb.length()-1) );
		return BASE_PATH + "/model-flow-deal-publish";
	}
	
	/**
	 * 发布处理
	* @param
	* @return
	 */
	@RequestMapping(value="dealsave",method=RequestMethod.POST)
	public @ResponseBody Integer dealsave(@RequestParam Map<String, Object> params){
		params.put("model_ids", params.get("ids").toString().split(","));
		params.put("model_status",params.get("type"));
		params.put("deal_memo",params.get("remarks"));
		params.put("deal_action",4);
		params.put("user_id",SysUserUtils.getCacheLoginUser().getId());
		int nresult = modelDealService.updateModelDeal(params);
		nresult = modelDealService.insertModelDealLog(params);
		return nresult;
	}
}
