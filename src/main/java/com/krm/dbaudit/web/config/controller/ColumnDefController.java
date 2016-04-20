package com.krm.dbaudit.web.config.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;

import com.krm.dbaudit.web.config.model.ColumnDef;
import com.krm.dbaudit.web.config.service.ColumnDefService;

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
@RequestMapping("columnDef")
public class ColumnDefController extends BaseController {
	
	
	@Resource
	private ColumnDefService columnDefService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String tocolumnDef(Model model){
		return "columnDef/columnDef";
	}
	
	

	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<ColumnDef> page = columnDefService.findPageInfo(params);
		model.addAttribute("page", page);
		return "columnDef/columnDef-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute ColumnDef columnDef){
		return columnDefService.saveColumnDef(columnDef);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return columnDefService.deleteColumnDef(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return columnDefService.deleteColumnDef(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		ColumnDef columnDef = null;
		if(StringUtils.equals("edit", mode)){
			columnDef = columnDefService.selectByPrimaryKey(id);
			model.addAttribute("columnDef", columnDef);
		}else if(StringUtils.equals("detail", mode)){
			columnDef = columnDefService.selectByPrimaryKey(id);
			model.addAttribute("columnDef", columnDef);
		}
		
		return mode.equals("detail")?"columnDef/columnDef-detail":"columnDef/columnDef-save";
	}
	

}
