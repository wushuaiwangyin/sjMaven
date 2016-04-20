package com.krm.dbaudit.web.model.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;

import com.krm.dbaudit.web.model.model.ModelResult;
import com.krm.dbaudit.web.model.service.ModelResultService;

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
@RequestMapping("modelResult")
public class ModelResultController extends BaseController {
	
	
	@Resource
	private ModelResultService modelResultService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String tomodelResult(Model model){
		return "modelResult/modelResult";
	}
	
	

	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<ModelResult> page = modelResultService.findPageInfo(params);
		model.addAttribute("page", page);
		return "modelResult/modelResult-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute ModelResult modelResult){
		return modelResultService.saveModelResult(modelResult);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return modelResultService.deleteModelResult(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return modelResultService.deleteModelResult(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		ModelResult modelResult = null;
		if(StringUtils.equals("edit", mode)){
			modelResult = modelResultService.selectByPrimaryKey(id);
			model.addAttribute("modelResult", modelResult);
		}else if(StringUtils.equals("detail", mode)){
			modelResult = modelResultService.selectByPrimaryKey(id);
			model.addAttribute("modelResult", modelResult);
		}
		
		return mode.equals("detail")?"modelResult/modelResult-detail":"modelResult/modelResult-save";
	}
	

}
