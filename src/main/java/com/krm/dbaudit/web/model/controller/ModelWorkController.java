package com.krm.dbaudit.web.model.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.model.service.ModelBaseService;
import com.krm.dbaudit.web.model.service.ModelPropertyService;
import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.util.ResponseUtils;

@Controller
@RequestMapping("model/work")
public class ModelWorkController extends BaseController{
	private String BASE_PATH = "model/base/";
	
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
	public String toModelWork(Model model){
		List<ModelProperty> properties = modelPropertyService.findAllProperty(); 
		model.addAttribute("ps", properties);
		return BASE_PATH + "model-work";
	}
	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		if(params.containsKey("sortC")){
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		if(params.get("name")!=null)
		{
			String s = new String(((String) params.get("name")).getBytes("ISO-8859-1"),"UTF-8");
			s = java.net.URLDecoder.decode(s, "UTF-8");
			params.put("name",s);
		}
		long statuses[] = {8};
		params.put("statuses", statuses);
		PageInfo<ModelBase> page = modelBaseService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
}
