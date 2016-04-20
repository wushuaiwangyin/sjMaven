package com.krm.dbaudit.web.model.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;

import com.krm.dbaudit.web.model.model.ModelRun;
import com.krm.dbaudit.web.model.service.ModelRunService;

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
@RequestMapping("model/runlog")
public class ModelRunController extends BaseController {
	private String BASE_PATH = "model/base/";
	
	@Resource
	private ModelRunService modelRunService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String tomodelRun(Model model){
		return BASE_PATH+"model-run";
	}
	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<ModelRun> page = modelRunService.findPageInfo(params);
		model.addAttribute("page", page);
		return "modelRun/modelRun-list";
	}
			
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		ModelRun modelRun = null;
		if(StringUtils.equals("edit", mode)){
			modelRun = modelRunService.selectByPrimaryKey(id);
			model.addAttribute("modelRun", modelRun);
		}else if(StringUtils.equals("detail", mode)){
			modelRun = modelRunService.selectByPrimaryKey(id);
			model.addAttribute("modelRun", modelRun);
		}
		
		return mode.equals("detail")?"modelRun/modelRun-detail":"modelRun/modelRun-save";
	}
	

}
