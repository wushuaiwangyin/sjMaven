package com.krm.dbaudit.web.verify.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;

import com.krm.dbaudit.web.verify.model.DataModelType;
import com.krm.dbaudit.web.verify.service.DataModelTypeService;

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
@RequestMapping("dataModelType")
public class DataModelTypeController extends BaseController {
	
	
	@Resource
	private DataModelTypeService dataModelTypeService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String todataModelType(Model model){
		return "dataModelType/dataModelType";
	}
	
	

	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<DataModelType> page = dataModelTypeService.findPageInfo(params);
		model.addAttribute("page", page);
		return "dataModelType/dataModelType-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute DataModelType dataModelType){
		return dataModelTypeService.saveDataModelType(dataModelType);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return dataModelTypeService.deleteDataModelType(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return dataModelTypeService.deleteDataModelType(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		DataModelType dataModelType = null;
		if(StringUtils.equals("edit", mode)){
			dataModelType = dataModelTypeService.selectByPrimaryKey(id);
			model.addAttribute("dataModelType", dataModelType);
		}else if(StringUtils.equals("detail", mode)){
			dataModelType = dataModelTypeService.selectByPrimaryKey(id);
			model.addAttribute("dataModelType", dataModelType);
		}
		
		return mode.equals("detail")?"dataModelType/dataModelType-detail":"dataModelType/dataModelType-save";
	}
	

}
