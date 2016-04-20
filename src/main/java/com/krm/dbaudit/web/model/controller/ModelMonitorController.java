package com.krm.dbaudit.web.model.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.dbaudit.web.model.service.ModelMonitorService;

@Controller
@RequestMapping("model/monitor")
public class ModelMonitorController {
	private String BASE_PATH = "model/base/";
	
	@Resource
	private ModelMonitorService modelMonitorService;
	
	@RequestMapping
	public String toModelBase(Model model){
		List<Map<String, Object>> statuslist = modelMonitorService.getModelStatusData();
		model.addAttribute("sl",statuslist);
		List<Map<String, Object>> deallist = modelMonitorService.getModelDealData();
		model.addAttribute("dl",deallist);
		List<Map<String, Object>> errorlist = modelMonitorService.getModelRunError();
		model.addAttribute("el",errorlist);
		return BASE_PATH + "monitor";
	}
}
