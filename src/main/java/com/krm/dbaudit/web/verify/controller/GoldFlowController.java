package com.krm.dbaudit.web.verify.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;

import com.krm.dbaudit.web.verify.model.GoldFlow;
import com.krm.dbaudit.web.verify.service.GoldFlowService;

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
@RequestMapping("goldFlow")
public class GoldFlowController extends BaseController {
	
	
	@Resource
	private GoldFlowService goldFlowService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String togoldFlow(Model model){
		return "goldFlow/goldFlow";
	}
	
	

	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<GoldFlow> page = goldFlowService.findPageInfo(params);
		model.addAttribute("page", page);
		return "goldFlow/goldFlow-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute GoldFlow goldFlow){
		return goldFlowService.saveGoldFlow(goldFlow);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return goldFlowService.deleteGoldFlow(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return goldFlowService.deleteGoldFlow(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		GoldFlow goldFlow = null;
		if(StringUtils.equals("edit", mode)){
			goldFlow = goldFlowService.selectByPrimaryKey(id);
			model.addAttribute("goldFlow", goldFlow);
		}else if(StringUtils.equals("detail", mode)){
			goldFlow = goldFlowService.selectByPrimaryKey(id);
			model.addAttribute("goldFlow", goldFlow);
		}
		
		return mode.equals("detail")?"goldFlow/goldFlow-detail":"goldFlow/goldFlow-save";
	}
	

}
