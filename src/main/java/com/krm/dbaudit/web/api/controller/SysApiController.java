package com.krm.dbaudit.web.api.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;

import com.krm.dbaudit.web.api.model.SysApi;
import com.krm.dbaudit.web.api.service.SysApiService;

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
@RequestMapping("sysApi")
public class SysApiController extends BaseController {
	
	
	@Resource
	private SysApiService sysApiService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String tosysApi(Model model){
		return "sysApi/sysApi";
	}
	
	

	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<SysApi> page = sysApiService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sysApi/sysApi-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysApi sysApi){
		return sysApiService.saveSysApi(sysApi);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return sysApiService.deleteSysApi(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return sysApiService.deleteSysApi(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		SysApi sysApi = null;
		if(StringUtils.equals("edit", mode)){
			sysApi = sysApiService.selectByPrimaryKey(id);
			model.addAttribute("sysApi", sysApi);
		}else if(StringUtils.equals("detail", mode)){
			sysApi = sysApiService.selectByPrimaryKey(id);
			model.addAttribute("sysApi", sysApi);
		}
		
		return mode.equals("detail")?"sysApi/sysApi-detail":"sysApi/sysApi-save";
	}
	

}
