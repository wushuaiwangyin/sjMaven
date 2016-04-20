package com.krm.dbaudit.web.model.controller;

import java.util.Map;

import javax.annotation.Resource;
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
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.model.service.ModelPropertyService;
import com.krm.dbaudit.web.util.ResponseUtils;

/**
 * 业务属性管理--控制层
 * 
* @author taosq on 2015-08-13
*/

@Controller
@RequestMapping("model/property")
public class ModelPropertyController extends BaseController {
	
	
	@Resource
	private ModelPropertyService modelPropertyService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toModelProperty(Model model){
		return "model/property/index";
	}
	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,Model model,HttpServletResponse response){
		if(params.containsKey("sortC")){
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<ModelProperty> page = modelPropertyService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute ModelProperty modelProperty){
		return modelPropertyService.saveModelProperty(modelProperty);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return modelPropertyService.deleteModelProperty(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return modelPropertyService.deleteModelProperty(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		ModelProperty modelProperty = null;
		if(StringUtils.equals("edit", mode)){
			modelProperty = modelPropertyService.selectByPrimaryKey(id);
			model.addAttribute("property", modelProperty);
		}else if(StringUtils.equals("detail", mode)){
			modelProperty = modelPropertyService.selectByPrimaryKey(id);
			model.addAttribute("item", modelProperty);
		}
		
		return mode.equals("detail")?"model/property/detail":"model/property/save";
	}
	

}
