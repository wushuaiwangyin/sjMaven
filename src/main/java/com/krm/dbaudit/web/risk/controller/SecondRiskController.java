package com.krm.dbaudit.web.risk.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.dbaudit.common.excel.ExcelUtils;
import com.krm.dbaudit.common.excel.template.utils.PoiUtil;
import com.krm.dbaudit.common.utils.FileUtils;
import com.krm.dbaudit.web.risk.service.SecondRiskService;
import com.krm.dbaudit.web.util.ResponseUtils;

/**
 * 二次分析模型处理
 * @author Parker
 *
 */
@Controller
@RequestMapping("second/risk")
public class SecondRiskController
{
	@Resource
	private SecondRiskService secondRiskService;
	
	/**
	 * 默认查询所有涉及模型大于等于3的客户列表
	 * 当月结果
	 * @param params
	 * @param response
	 */
	@RequestMapping(value="month", method=RequestMethod.POST)
	public void findResultByMonth(@RequestParam Map<String, Object> params,
			HttpServletResponse response) 
	{
		List<Map<String,Object>> list = secondRiskService.findResultByMonth(params);
		for (Map<String, Object> map : list)
		{
			map.put("flag", 1);
		}
		ResponseUtils.renderJson(response, list);
	}
		
	/**
	 * 默认查询所有涉及模型大于等于3的客户列表
	 * 昨日结果
	 * @param params
	 * @param response
	 */
	@RequestMapping(value="day", method=RequestMethod.POST)
	public void findResultByDay(@RequestParam Map<String, Object> params,
			HttpServletResponse response) 
	{
		List<Map<String,Object>> list = secondRiskService.findResultByDay(params);
		for (Map<String, Object> map : list)
		{
			map.put("flag", 0);
		}
		ResponseUtils.renderJson(response, list);
	}
	
	
	/**
	 * 点击模型数量进入查看详情
	 * @param params
	 * @param response
	 */
	@RequestMapping(value="details", method=RequestMethod.POST)
	public void findDetails(@RequestParam Map<String, Object> params,
			HttpServletResponse response) 
	{
		List<Map<String,Object>> list = secondRiskService.findDetails(params);
		ResponseUtils.renderJson(response, list);
	}
	
	
	/**
	 * 批量导出二次分析模型数据，excel格式
	 * 默认显示数据导出
	 * @throws Exception 
	 */
	@RequestMapping(value = "exportByNo",method = RequestMethod.POST)
	public void exportFile(@RequestParam Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF";
		List<File> files = this.generateFile(params, request, response);
		FileUtils.downLoadFiles(filePath+ "\\二次分析模型数据.zip", files, response);
	}
	
	
	/**
	 * 导出execl 
	 * 查询结果导出
	 */
	@RequestMapping(value = "exportDetails",method = RequestMethod.POST)
	public void exportFile2(@RequestParam Map<String, Object> params,
			HttpServletResponse response){
		List<Map<String,Object>> list = secondRiskService.findDetails(params);
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		titleMap.put("客户号","custno");
		titleMap.put("客户名","custname");
		titleMap.put("模型","modelname");
		
		
		try {
			//流的方式直接下载
			ExcelUtils.exportExcel(response, "二次分析模型查询统计.xls", list, titleMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成临时文件
	 * @param params
	 * @param response
	 * @return
	 */
	public List<File> generateFile(Map<String, Object> params,HttpServletRequest request,HttpServletResponse response){
		String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF";
		List<File> files = new ArrayList<File>();
		Set<String> keyset = params.keySet();
		for (String custNo : keyset)
		{
			String custName = params.get(custNo).toString().trim();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("custNo", custNo);
			List<Map<String,Object>> list = secondRiskService.findDetails(map);
			
			Map<String, String> titleMap = Maps.newLinkedHashMap();
			titleMap.put("客户号","custno");
			titleMap.put("客户名","custname");
			titleMap.put("模型","modelname");
			try {
				File file = new File(filePath+"\\"+custName+".xls");
				FileOutputStream fos = new FileOutputStream(file);
				PoiUtil.writeExcel(list, fos, titleMap);
				fos.close();
				files.add(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}
		return files;
	}
	
}
