package com.krm.dbaudit.web.verify.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;

import com.krm.dbaudit.web.verify.model.RiskModel;
import com.krm.dbaudit.web.verify.service.RiskModelService;

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
* @author taosq on 2015-08-13
*/

@Controller
@RequestMapping("riskModel")
public class RiskModelController extends BaseController {
	
	
	@Resource
	private RiskModelService riskModelService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toriskModel(Model model){
		return "riskModel/riskModel";
	}
	
	

	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<RiskModel> page = riskModelService.findPageInfo(params);
		model.addAttribute("page", page);
		return "riskModel/riskModel-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute RiskModel riskModel){
		return riskModelService.saveRiskModel(riskModel);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return riskModelService.deleteRiskModel(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return riskModelService.deleteRiskModel(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		RiskModel riskModel = null;
		if(StringUtils.equals("edit", mode)){
			riskModel = riskModelService.selectByPrimaryKey(id);
			model.addAttribute("riskModel", riskModel);
		}else if(StringUtils.equals("detail", mode)){
			riskModel = riskModelService.selectByPrimaryKey(id);
			model.addAttribute("riskModel", riskModel);
		}
		
		return mode.equals("detail")?"riskModel/riskModel-detail":"riskModel/riskModel-save";
	}
	

}
