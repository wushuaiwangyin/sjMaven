package com.krm.dbaudit.web.model.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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
import com.krm.dbaudit.web.model.model.ModelFieldDef;
import com.krm.dbaudit.web.model.model.ModelFlow;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.model.model.ModelRun;
import com.krm.dbaudit.web.model.service.ModelBaseService;
import com.krm.dbaudit.web.model.service.ModelDealService;
import com.krm.dbaudit.web.model.service.ModelDefFieldService;
import com.krm.dbaudit.web.model.service.ModelFlowService;
import com.krm.dbaudit.web.model.service.ModelPropertyService;
import com.krm.dbaudit.web.model.service.ModelRunService;
import com.krm.dbaudit.web.sys.model.SysArea;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;

/**
* @author taosq on 2015-08-13
*/

@Controller
@RequestMapping("model/base")
public class ModelBaseController extends BaseController {
	private String BASE_PATH = "model/base/";
	
	@Resource
	private ModelBaseService modelBaseService;
	
	@Resource
	private ModelFlowService modelFlowService;
	
	@Resource
	private ModelDealService modelDealService;
	
	@Resource
	private ModelRunService modelRunService;
	
	@Resource
	private ModelDefFieldService modelDefFieldService;
	
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
		return BASE_PATH + "mng";
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
		PageInfo<ModelBase> page = modelBaseService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute ModelBase modelBase){
		return modelBaseService.saveModelBase(modelBase);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return modelBaseService.deleteModelBase(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return modelBaseService.deleteModelBase(ids);
	}
	
	/**
	 * 批量设置运行时间
	* @param
	* @return
	 */
	@RequestMapping(value="model-set-run-date",method=RequestMethod.POST)
	public String setrun(@RequestParam(value = "ids[]") Long[] ids,Model model){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < ids.length; i++){
			sb.append(ids[i].toString()+",");
		}
		model.addAttribute("ids", sb.substring(0, sb.length()-1) );
		return BASE_PATH + "model-set-run-date";
	}
	
	/**
	 * 提交页面
	* @param
	* @return
	 */
	@RequestMapping(value="submitadd",method=RequestMethod.POST)
	public String submitadd(@RequestParam(value = "ids[]") Long[] ids,Model model){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < ids.length; i++){
			sb.append(ids[i].toString()+",");
		}
		Map<String, Object> params = new HashMap <String, Object>();
		params.put("model_ids", ids);
		List<ModelBase> modellist = modelBaseService.findByIds(params);
		model.addAttribute("ids", sb.substring(0, sb.length()-1));
		model.addAttribute("models", modellist);
		if(modellist.size()==1)
		{
			model.addAttribute("model", modellist.get(0));
			List<ModelFieldDef> fieldlist = modelDefFieldService.findFieldByModelId(modellist.get(0).getId());
			model.addAttribute("fieldlist", fieldlist);
		}
		List<ModelProperty> properties = modelPropertyService.findAllProperty(); 
		model.addAttribute("ps", properties);
		return BASE_PATH + "save";
	}
	
	/**
	 * 批量设置运行时间
	* @param
	* @return
	 */
	@RequestMapping(value="submitsave",method=RequestMethod.POST)
	public @ResponseBody Integer submitsave(@RequestParam Map<String, Object> params){
	//	return modelBaseService.saveModelBase(modelBase);
		if(params.get("ids")!=null)
		{
			String s = params.get("ids").toString();
			String [] model_ids= s.split(",");
			params.put("model_ids", model_ids);
		}
		return modelBaseService.submismodel(params);
	}
	
	/**
	 * 批量设置运行时间
	* @param
	* @return
	 */
	@RequestMapping(value="setrunsave",method=RequestMethod.POST)
	public @ResponseBody Integer setrunsave(@RequestParam Map<String, Object> params){
	//	return modelBaseService.saveModelBase(modelBase);
		return 1;
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		ModelBase modelBase = null;
		List<ModelDeal> modelDealList = null;
		List<ModelFlow> modelFlowList = null;
		List<ModelRun> modelRunList = null;
		if(StringUtils.equals("edit", mode)){
			modelBase = modelBaseService.findById(id);
			model.addAttribute("model", modelBase);
			List<ModelProperty> properties = modelPropertyService.findAllProperty(); 
			model.addAttribute("ps", properties);
		}else if(StringUtils.equals("detail", mode)){
			modelBase = modelBaseService.findById(id);
			modelFlowList = modelFlowService.findFlowById(id);
			modelDealList = modelDealService.findModelDealInfo(id);
			modelRunList = modelRunService.selectByModelId(id);
			model.addAttribute("item", modelBase);
			model.addAttribute("flowlist", modelFlowList);
			model.addAttribute("deallist",modelDealList);
			model.addAttribute("runlist",modelRunList);
		}else if(StringUtils.equals("add", mode)){
			List<ModelProperty> properties = modelPropertyService.findAllProperty(); 
			model.addAttribute("ps", properties);
		}
		
		return mode.equals("detail")?BASE_PATH + "detail":BASE_PATH + "save";
	}
	
	
	//提交
	@RequestMapping(value="submit",method=RequestMethod.POST)
	public @ResponseBody Integer submit(@RequestParam(value = "ids[]") Long[] ids){
		Map<String, Object> params = new HashMap <String, Object>();
		params.put("model_ids", ids);
		params.put("model_status",2);
		params.put("deal_memo","");
		params.put("deal_action",1);
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
			ExcelUtils.exportExcel(response, "模型.xls", list, titleMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
