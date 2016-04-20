package com.krm.dbaudit.web.model.controller;

import java.util.List;
import java.util.Map;

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

import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.model.model.ModelStep;
import com.krm.dbaudit.web.model.service.ModelBaseService;
import com.krm.dbaudit.web.model.service.ModelPropertyService;
import com.krm.dbaudit.web.model.service.ModelStepService;

/**
* @author taosq on 2015-08-13
*/

@Controller
@RequestMapping("model/step")
public class ModelStepController extends BaseController {
	
	
	@Resource
	private ModelStepService modelStepService;
	
	@Resource
	private ModelBaseService modelBaseService;
	
	@Resource 
	private ModelPropertyService modelPropertyService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String tomodelStep(Model model){
		return "model/tool/index";
	}
	
	
	@RequestMapping("main")
	public String toModelTool(Model model,Long id){
		if(id != null){
			ModelBase modelBase = modelBaseService.findById(id);
			model.addAttribute("model", modelBase);
			List<ModelProperty> properties = modelPropertyService.findAllProperty(); 
			model.addAttribute("ps", properties);
		}else{
			List<ModelProperty> properties = modelPropertyService.findAllProperty(); 
			model.addAttribute("ps", properties);
		}
		return "model/tool/main";
	}
	
	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<ModelStep> page = modelStepService.findPageInfo(params);
		model.addAttribute("page", page);
		return "modelStep/modelStep-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute ModelStep modelStep){
		return modelStepService.saveModelStep(modelStep);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return modelStepService.deleteModelStep(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return modelStepService.deleteModelStep(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		ModelStep modelStep = null;
		if(StringUtils.equals("edit", mode)){
			modelStep = modelStepService.selectByPrimaryKey(id);
			model.addAttribute("modelStep", modelStep);
		}else if(StringUtils.equals("detail", mode)){
			modelStep = modelStepService.selectByPrimaryKey(id);
			model.addAttribute("modelStep", modelStep);
		}
		
		return mode.equals("detail")?"modelStep/modelStep-detail":"modelStep/modelStep-save";
	}
	

}
