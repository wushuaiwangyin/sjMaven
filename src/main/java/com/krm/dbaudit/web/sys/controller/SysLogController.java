

package com.krm.dbaudit.web.sys.controller;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.web.sys.model.SysLog;
import com.krm.dbaudit.web.sys.service.SysLogService;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("syslog")
public class SysLogController extends BaseController {
	
	
	@Resource
	private SysLogService sysLogService;
	
	/**
	 * 跳转到模块页面
	 */
	@RequestMapping
	public String toSysLog(Model model){
		return "sys/log/log";
	}
	
	/**
	 * 分页显示
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model){
		PageInfo<SysLog> page = sysLogService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sys/log/log-list";
	}
	
	/**
	 * 添加或更新
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysLog sysLog){
		return sysLogService.saveSysLog(sysLog);
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return sysLogService.deleteByPrimaryKey(id);
	}
	
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,Model model){
		SysLog sysLog = sysLogService.getById(id);
		model.addAttribute("sysLog", sysLog);
		return "sys/log/log-detail";
	}
	

}
