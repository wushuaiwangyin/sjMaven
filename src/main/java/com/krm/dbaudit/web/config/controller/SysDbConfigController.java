package com.krm.dbaudit.web.config.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;

import com.krm.dbaudit.web.config.model.SysDbConfig;
import com.krm.dbaudit.web.config.service.SysDbConfigService;

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
@RequestMapping("sysDbConfig")
public class SysDbConfigController extends BaseController {
	
	
	@Resource
	private SysDbConfigService sysDbConfigService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String tosysDbConfig(Model model){
		return "sysDbConfig/sysDbConfig";
	}
	
	

	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<SysDbConfig> page = sysDbConfigService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sysDbConfig/sysDbConfig-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysDbConfig sysDbConfig){
		return sysDbConfigService.saveSysDbConfig(sysDbConfig);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return sysDbConfigService.deleteSysDbConfig(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return sysDbConfigService.deleteSysDbConfig(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		SysDbConfig sysDbConfig = null;
		if(StringUtils.equals("edit", mode)){
			sysDbConfig = sysDbConfigService.selectByPrimaryKey(id);
			model.addAttribute("sysDbConfig", sysDbConfig);
		}else if(StringUtils.equals("detail", mode)){
			sysDbConfig = sysDbConfigService.selectByPrimaryKey(id);
			model.addAttribute("sysDbConfig", sysDbConfig);
		}
		
		return mode.equals("detail")?"sysDbConfig/sysDbConfig-detail":"sysDbConfig/sysDbConfig-save";
	}
	

}
