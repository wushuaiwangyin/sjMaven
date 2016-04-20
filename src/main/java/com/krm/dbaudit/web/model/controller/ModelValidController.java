package com.krm.dbaudit.web.model.controller;

import java.io.UnsupportedEncodingException;
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

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.excel.ExcelUtils;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.model.ModelDeal;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.model.service.ModelBaseService;
import com.krm.dbaudit.web.model.service.ModelDealService;
import com.krm.dbaudit.web.model.service.ModelPropertyService;
import com.krm.dbaudit.web.sys.model.SysArea;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;

@Controller
@RequestMapping("model/valid")
public class ModelValidController {
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
	public String toModelBase(Model model){
		List<ModelProperty> properties = modelPropertyService.findAllProperty(); 
		model.addAttribute("ps", properties);
		return BASE_PATH + "model-valid";
	}
	
	/**
	 * 分页显示
	* @param params
	* @return
	 * @throws UnsupportedEncodingException 
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
		long statuses[] = {2,3};
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
		return BASE_PATH + "/model-flow-deal";
	}
	
	/**
	 * 验证处理
	* @param
	* @return
	 */
	@RequestMapping(value="dealsave",method=RequestMethod.POST)
	public @ResponseBody Integer dealsave(@RequestParam Map<String, Object> params){
		params.put("model_ids", params.get("ids").toString().split(","));
		params.put("model_status",params.get("type"));
		params.put("deal_memo",params.get("remarks"));
		params.put("deal_action",2);
		params.put("user_id",SysUserUtils.getCacheLoginUser().getId());
		int nresult = modelDealService.updateModelDeal(params);
		nresult = modelDealService.insertModelDealLog(params);
		return nresult;
	}
	
	/**
	 * 导出execl 
	 */
	@RequestMapping(value = "export",method = RequestMethod.POST)
	public void exportFile(@RequestParam Map<String, Object> params,
			HttpServletResponse response){
		
		long statuses[] = {2,3};
		params.put("statuses", statuses);
		
		List<ModelBase> list = modelBaseService.findModelList(params);
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		
		titleMap.put("﻿模型编号","id");
		titleMap.put("模型名称","name");
		titleMap.put("业务属性","propertyname");
		titleMap.put("业务条线","buzlinename");
		titleMap.put("专题","subjectname");
		titleMap.put("风险级别","risklevelname");
		titleMap.put("状态","statusname");
		titleMap.put("数据源","dbSrc");
		titleMap.put("跑批时间","runTime");
		titleMap.put("频度","interval");
		titleMap.put("跑批方式","runWay");
		titleMap.put("结果表名","tname");
		titleMap.put("主键字段","cKey");
		titleMap.put("机构字段","cOrg");
		titleMap.put("客户号字段","cCustomer");
		titleMap.put("模型描述","description");
		titleMap.put("创建人","createBy");
		titleMap.put("创建时间","createDate");
		titleMap.put("修改人","updateBy");
		titleMap.put("修改时间","updateDate");
		titleMap.put("删除标识","delFlag");
		titleMap.put("最后跑批时间","lastRunDate");
		titleMap.put("最后跑批记录数","lastRunCount");
		titleMap.put("最后跑批状态","lastRunStatus");
		
		try {
			//流的方式直接下载
			ExcelUtils.exportExcel(response, "待验证模型.xls", list, titleMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
