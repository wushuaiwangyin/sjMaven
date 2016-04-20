package com.krm.dbaudit.web.report.analysis.controller;

import java.util.List;
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
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.report.analysis.model.Analysis;
import com.krm.dbaudit.web.report.analysis.model.ReportType;
import com.krm.dbaudit.web.report.analysis.service.AnalysisService;
import com.krm.dbaudit.web.report.analysis.service.ReportTypeService;
import com.krm.dbaudit.web.sys.model.SrcDataTable;
import com.krm.dbaudit.web.sys.model.SrcDataType;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;

/**
 * 报表类型配置
 * @author tanwen
 *
 */
@Controller
@RequestMapping("reporttype")
public class ReportTypeController {
	@Resource
	private ReportTypeService reportTypeService;
	@Resource
	private AnalysisService analysisService;
	
	private String BASE_PATH = "reporttype/";
	/**
	 * 跳转到模块主页面
	 */
	@RequestMapping
	public String toReportType(Model model){
		model.addAttribute("treeList", reportTypeService.findAllReportType());
		return "reporttype/index";
	}
	
	@RequestMapping(value = "tree")
	public @ResponseBody List<ReportType> tree(Long property,String line) {
		return reportTypeService.findAllReportType();
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,Long parentId,@PathVariable String mode, Model model){
		ReportType reportType=null;
		if(StringUtils.equals("edit", mode)){
			reportType = reportTypeService.findById(id);
			model.addAttribute("type", reportType);
			if(reportType.getParentId() != null){
				model.addAttribute("pType", reportTypeService.findById(reportType.getParentId()));
			}
		}else if(StringUtils.equals("detail", mode)){
			
		}else if(StringUtils.equals("add", mode)){
			model.addAttribute("pType", reportTypeService.findById(parentId));
		}
		
		return mode.equals("detail")? BASE_PATH + "detail":BASE_PATH +"save";
	}
	
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute ReportType reportType,@RequestParam Map<String, Object> params){
		reportType.setStatus("0");;	//源数据管理模块新建分类，默认为0，系统使用。
		return reportTypeService.saveReportType(reportType);
	}
	
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return reportTypeService.deleteByPrimaryKey(id);
	}
	
	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,HttpServletResponse response){
		if(params.containsKey("sortC")){
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<Analysis> page = analysisService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	@RequestMapping(value="createlayer")
	public String createlayer(Model model){
		return BASE_PATH +"createreport";
	}
	
	@RequestMapping(value="savenewreport",method=RequestMethod.POST)
	public @ResponseBody void savenewtable(@ModelAttribute Analysis analysis,@RequestParam Map<String, Object> params,HttpServletResponse response){
		params.put("createBy", SysUserUtils.getCacheLoginUser().getId());
		if(null==analysis.getId() || "".equals(analysis.getId())){
			//新建
			analysis.setStatus("0");
			analysis.setSort(1L);
			ResponseUtils.renderJson(response, analysisService.insertSelective(analysis));
		}else{
			//编辑
			ResponseUtils.renderJson(response, analysisService.updateById(analysis));
		}
		
	}
	
	/**
	 * 弹窗显示
	* @param 
	* @return
	 */
	@RequestMapping(value="showsavereport",method=RequestMethod.POST)
	public String showsavetable(Long id, Model model){
		Analysis analysis = null;
		
		//根据表名
		analysis = analysisService.findById(id);
		model.addAttribute("analysis", analysis);
		
		ReportType ptype = null;
		ptype = reportTypeService.findById(analysis.getReportType());
		model.addAttribute("pType", ptype);
		
		return BASE_PATH +"editreport";
	}
}
