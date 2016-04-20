package com.krm.dbaudit.web.report.index.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.krm.dbaudit.web.report.analysis.model.Analysis;
import com.krm.dbaudit.web.report.analysis.model.ReportType;
import com.krm.dbaudit.web.report.analysis.service.AnalysisService;
import com.krm.dbaudit.web.report.analysis.service.ReportTypeService;
import com.krm.dbaudit.web.sys.model.SysDict;

/**
 * 指标监测
 * @author tanwen
 *
 */
@Controller
@RequestMapping("indexmonitor")
public class IndexController {
	@Resource
	private AnalysisService analysisService;
	@Resource
	private ReportTypeService reportTypeService;
	/**
	 * 跳转到模块主页面
	 */
	@RequestMapping
	public String toIndex(Model model){
		SysDict sysDict = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportType", 4L);
		List<Analysis> analysislist = analysisService.getAnalysisList(map);
		List<SysDict> list = new ArrayList<SysDict>();
		
		//获取统计分析的所有类型
		List<ReportType> typelist = reportTypeService.findByStartWithId(4L);
		
		for(ReportType rt : typelist){
			sysDict = new SysDict();
			sysDict.setId(rt.getId());
			sysDict.set("parentId", rt.getParentId());
			sysDict.set("name",rt.getName());
			list.add(sysDict);
		}
		
		
		for(Analysis temp : analysislist){
			sysDict = new SysDict();
			sysDict.setId(temp.getReportId());
			sysDict.set("parentId", temp.getReportType());
			sysDict.set("name", temp.getReportName());
			sysDict.set("url", temp.getReportUrl());
			sysDict.set("target", "reportframe");
			list.add(sysDict); 
		}
		
		
		model.addAttribute("treeList", JSON.toJSONString(list));
		return "report/reportmonitor";
	}
}
