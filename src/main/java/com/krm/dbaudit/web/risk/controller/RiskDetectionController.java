package com.krm.dbaudit.web.risk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.krm.dbaudit.common.excel.ExcelUtils;
import com.krm.dbaudit.common.spring.utils.SpringContextHolder;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.model.model.AuditModel;
import com.krm.dbaudit.web.model.model.AuditType;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.model.service.AuditTypeService;
import com.krm.dbaudit.web.model.service.ModelPropertyService;
import com.krm.dbaudit.web.risk.model.RiskModelBase;
import com.krm.dbaudit.web.risk.service.RiskDetectionService;
import com.krm.dbaudit.web.sys.model.SysDict;
import com.krm.dbaudit.web.sys.model.SysOffice;
import com.krm.dbaudit.web.sys.model.SysUser;
import com.krm.dbaudit.web.sys.service.SysDictService;
import com.krm.dbaudit.web.sys.service.SysOfficeService;
import com.krm.dbaudit.web.util.ListUtil;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;

/**
 * 风险监测
 * @author Parker
 *
 */
@Controller
@RequestMapping("risk/detection")
public class RiskDetectionController
{
	@Resource
	private RiskDetectionService riskDetectionService;
	
	@Resource
	private ModelPropertyService modelPropertyService;
	
	@Resource
	private SysDictService sysDictService;
	
	@Resource
	private AuditTypeService auditTypeService;
	
	static SysOfficeService sysOfficeService = SpringContextHolder.getBean("sysOfficeService");
	/**
	 * 首页跑批模型数量方法
	 * @param model
	 * @return
	 */
	@RequestMapping("control")
	public String toIndex(Model model) {
		model.addAttribute("lastMonthRunList", findRunAmount(1));
		model.addAttribute("yesterdayRunList", findRunAmount(0));
		return "risk/index";
	}
	
	/**
	 * 跳转合规模型页面
	 * @param model
	 * @return
	 */
	@RequestMapping("hg")
	public String page2(Model model){
		model.addAttribute("property", riskDetectionService.getProperty("合规模型"));
		return "risk/hg";
	}
	
	/**
	 * 跳转到重大风险模型页面
	 * @param model
	 * @return
	 */
	@RequestMapping("big")
	public String page3(Model model){
		model.addAttribute("property", riskDetectionService.getProperty("重大风险模型"));
		return "risk/zd";
	}
	
	/**
	 * 跳转到监管模型页面
	 * @param model
	 * @return
	 */
	@RequestMapping("jg")
	public String page5(Model model){
		model.addAttribute("property", riskDetectionService.getProperty("监管模型"));
		return "risk/jg";
	}
	
	/**
	 * 进入审计专项模型主界面
	 * @param params
	 * @param response
	 * @return
	 */
	@RequestMapping("sjzx")
	public String  showsjzxlayer(@RequestParam Map<String, Object> params, Model model)
	{
		model.addAttribute("treeList", auditTypeService.findAllAuditType());
		return "risk/sj";
	}	
	
	/**
	 * 查询跑批模型数量
	 * @param flag(日期标记：1：上月，0：昨日)
	 * @return
	 */
	public List<Map<String,Object>>  findRunAmount(Integer flag)
	{
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("flag", flag);
		params.put("status", 8);
		List<Map<String,Object>> list = riskDetectionService.findRunAmount(params);
		Integer total = 0;
		Map<String,Object> account = new HashMap<String, Object>();
		for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();)
		{
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			if(map.entrySet().size() <2)
			{
				total =  Integer.parseInt(map.get("value").toString());
				iterator.remove();
			}
		}
		account.put("total", total);
		list.add(account);
		return list;
	}
	/**
	 * 分页显示
	* @param params
	* @return
	 * @throws Exception 
	 */
	@RequestMapping(value="list", method=RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,
			HttpServletResponse response) throws Exception
	{
		Map<String,Object> map = new HashMap<String, Object>();
		for (String key : params.keySet())		//处理中文乱码
		{
			String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"),"UTF-8");
			paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
			map.put(key, paramsTrans);
		}
		if (params.containsKey("sortC"))		//加入排序参数
		{
			map.put("sortC", StringConvert.camelhumpToUnderline(params.get(
					"sortC").toString()));
		}
		map.put("status", 8);	//默认已发布状态
		//搜索树自动查询结果，根据条线和机构两个条件
		String buzLine = null;
		String organId = null;
		if(map.containsKey("buzLine")){
			buzLine = map.get("buzLine").toString();
		}if(map.containsKey("organId")){
			organId = map.get("organId").toString();
		}
		if(buzLine != null && buzLine.length() != 0){
			String buzLines [] = buzLine.split(",");
			map.put("buzLine", buzLines);
		}
		if(organId != null && organId.length() != 0){
			map.put("organId", organId);
		}else{
			SysUser sysUser = SysUserUtils.getCacheLoginUser();
			map.put("organId", sysUser.getOfficeId());
		}
		PageInfo<RiskModelBase> page = riskDetectionService.findPageInfo(map);
		ResponseUtils.renderJson(response, page);
	}
	/**
	 * 审计专项分页显示
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="list1", method=RequestMethod.POST)
	public void list1(@RequestParam Map<String, Object> params,
			HttpServletResponse response) throws Exception
			{
		Map<String,Object> map = new HashMap<String, Object>();
		for (String key : params.keySet())		//处理中文乱码
		{
			String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"),"UTF-8");
			paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
			map.put(key, paramsTrans);
		}
		if (params.containsKey("sortC"))		//加入排序参数
		{
			map.put("sortC", StringConvert.camelhumpToUnderline(params.get(
					"sortC").toString()));
		}
		map.put("status", 8);	//默认已发布状态
		//搜索树自动查询结果，根据条线和机构两个条件
		String type = null;
		String organId = null;
		if(map.containsKey("type")){
			type = map.get("type").toString();
		}if(map.containsKey("organId")){
			organId = map.get("organId").toString();
		}
		if(type != null && type.length() != 0){
			String types [] = type.split(",");
			map.put("type", types);
		}
		if(organId != null && organId.length() != 0){
			map.put("organId", organId);
		}else{
			SysUser sysUser = SysUserUtils.getCacheLoginUser();
			map.put("organId", sysUser.getOfficeId());
		}
		PageInfo<RiskModelBase> page = riskDetectionService.findPageInfoByAudit(map);
		ResponseUtils.renderJson(response, page);
			}
	
	
	/**
	 * 饼图查询模型跑批数量
	 * @param flag
	 * @param response
	 */
	@RequestMapping(value="countByPie", method=RequestMethod.POST)
	public void  countByPie(@RequestParam Map<String, Object> params, HttpServletResponse response)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		params.put("status", 8);
		List<Map<String,Object>> list = riskDetectionService.findRunAmount(params);
		for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();)
		{
			Map<String, Object> map1 = (Map<String, Object>) iterator.next();
			if(map1.entrySet().size() <2)
			{
        		iterator.remove();
			}
		}
		if(list.size() == 0){
			map.put("name", "无相关数据！");
			map.put("value", 0);
			list.add(map);
		}
        ResponseUtils.renderJson(response, list);
	}
	
	/**
	 * 柱状图查询新增模型数量
	 * @param response
	 */
	@RequestMapping(value="countByBar", method=RequestMethod.POST)
	public void  countByBar(HttpServletResponse response)
	{
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("status", 8);
		List<Map<String,Object>> list = riskDetectionService.countByBar(params);
		list = ListUtil.mergeList(list);
		ResponseUtils.renderJson(response, list);
	}
	
	/**
	 * 柱状图机构新增模型排行榜
	 * @param params
	 * @param response
	 */
	@RequestMapping(value="findByOrgan", method=RequestMethod.POST)
	public void  findByOrgan(@RequestParam Map<String, Object> params, HttpServletResponse response)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		Integer flag = Integer.parseInt(params.get("flag").toString());
		List<Map<String,Object>> list =  new ArrayList<Map<String,Object>>();
		if(flag == 1){
			list = riskDetectionService.findLastMonthByOrgan(params);
			if(list.size() == 0){
				map.put("name", "无相关数据！");
				map.put("count", 0);
				list.add(map);
			}
		}else{
			list = riskDetectionService.findYesterdayByOrgan(params);
			if(list.size() == 0){
				map.put("name", "无相关数据！");
				map.put("count", 0);
				list.add(map);
			}
		}
		ResponseUtils.renderJson(response, list);
	}
	
	/**
	 * 首页跑批模型数量导出
	 * @param params
	 * @param response
	 */
	@RequestMapping(value="exportDataRunModel",method=RequestMethod.POST)
	public void exportDataRunModel(@RequestParam Map<String, Object> params,HttpServletResponse response)
	{
		Integer flag = Integer.parseInt(params.get("flag").toString());
		List<Map<String,Object>> list = findRunAmount(flag);
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		titleMap.put("模型名称", "name");
		titleMap.put("新增数量", "value");
		if(flag == 1){
			try {
				//流的方式直接下载
				ExcelUtils.exportExcel(response, "上月模型跑批数量统计.xls", list, titleMap);
				
			} catch (Exception e) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				e.printStackTrace();
			}
		}
		if(flag == 0){
			try {
				//流的方式直接下载
				ExcelUtils.exportExcel(response, "昨日模型跑批数量统计.xls", list, titleMap);
				
			} catch (Exception e) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 业务条线树
	 * @param response
	 */
	@RequestMapping(value="buzLine",method=RequestMethod.GET)
	public void findBuzLine(HttpServletResponse response,Long property)
	{
		List<SysDict> list = new ArrayList<SysDict>();
		SysDict sd = null;
		
		List<ModelProperty> mps = modelPropertyService.findAllProperty();
		if(property == null)			//首页树一级菜单不查询，不需要属性值
		{
			if (!mps.isEmpty())
			{
				for (ModelProperty mp : mps)
				{
					sd = new SysDict();
					sd.setId(mp.getId());
					sd.set("name", mp.getName());
					sd.set("parentId", 0L);
					sd.set("haveLine",  mp.getHaveLine());
					sd.setValue("0");
					sd.setType("buzline");
					list.add(sd);
					if(mp.getHaveLine() == 1)
					{
						List<SysDict> dicts = (List<SysDict>) sysDictService
								.findAllMultimap().get("model_buz_line");
						for (SysDict dic : dicts)
						{
							sd = new SysDict();
							sd.setId(dic.getId());
							sd.set("name",dic.getLabel());
							sd.set("parentId", mp.getId());
							sd.setValue(dic.getValue());
							sd.setType("buzline");
							list.add(sd);
						}
					}
				}
			}
				//非首页一级菜单和首页二级菜单树
		}else{									
			if (!mps.isEmpty())
			{
				for (ModelProperty mp : mps)
				{
					if(mp.getId() == property)
					{
						sd = new SysDict();
						sd.setId(mp.getId());
						sd.set("name", mp.getName());
						sd.set("parentId", 0L);
						sd.set("haveLine",  mp.getHaveLine());
						sd.setValue("0");
						sd.setType("buzline");
						list.add(sd);
						if(mp.getHaveLine() == 1)
						{
							List<SysDict> dicts = (List<SysDict>) sysDictService
									.findAllMultimap().get("model_buz_line");
							for (SysDict dic : dicts)
							{
								sd = new SysDict();
								sd.setId(dic.getId());
								sd.set("name",dic.getLabel());
								sd.set("parentId", mp.getId());
								sd.setValue(dic.getValue());
								sd.setType("buzline");
								list.add(sd);
							}
						}
					}
				}
			}
		}

		ResponseUtils.renderJson(response, list);
	}
	
	
	/**
	 * 专项树
	 * @param property
	 * @param line
	 * @return
	 */
	@RequestMapping(value = "auditTree")
	public @ResponseBody List<AuditType> tree(Long property,String line) {
		List<AuditType> auditTypeList = auditTypeService.findAllAuditType();
		List<AuditModel> auditModelList = riskDetectionService.findAllAuditModel();
		for (AuditModel auditModel : auditModelList)
		{
			AuditType auditType = new AuditType();
			auditType.setId(auditModel.getId());
			auditType.setParentId(auditModel.getType());
			auditType.set("modelId", auditModel.getModel());
			auditType.set("type", auditModel.getType());
			auditType.set("name", auditModel.get("name"));
			auditTypeList.add(auditType);
		}
		return auditTypeList;
		
	}
	
	/**
	 * 机构树
	 * @return
	 */
	@RequestMapping(value="tree",method = RequestMethod.POST)
	public @ResponseBody List<SysOffice> getOfficeTreeList(@ModelAttribute SysOffice sysOffice){
		List<SysOffice> list = SysUserUtils.getUserOffice();
		return SysUserUtils.getUserOffice();
	}



	/**
	 * 动态查询数据记录统计
	 * @param params
	 * @param response
	 * @throws Exception
	 */
	 
	@RequestMapping(value="status", method=RequestMethod.POST)
	public @ResponseBody void findDataByStatus(@RequestParam Map<String, Object> params,
			HttpServletResponse response) throws Exception{
		ResponseUtils.renderJson(response, statusTool(params));
	}

	/**
	 * 处理新增数据方法
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> statusTool(@RequestParam Map<String, Object> params){
		Integer property = Integer.parseInt(params.get("property").toString());
		Integer buzLine = null;
		Integer str = Integer.parseInt(params.get("buzLine").toString());	//将二级菜单的条线设为null
		if(str == 0){
			buzLine = null;
		}else{
			buzLine = str;
		}
		
		Map<String,Object> newParams = new HashMap<String, Object>();	//重新封装参数map
		newParams.put("property", property);
		newParams.put("buzLine", buzLine);
		
		List<Map<String,Object>> list = riskDetectionService.findLastMonthByStatus(newParams); //上月数据
		List<Map<String,Object>> list1 = riskDetectionService.findYesterdayByStatus(newParams); //昨天数据
		list.addAll(list1);	//合并数据
		list = ListUtil.mergeList(list); //去除重复元素
		Collections.sort(list, new Comparator<Map<String,Object>>(){		//合并后list进行再排序
			public int compare(Map<String, Object> o1, Map<String, Object> o2){
				Integer sort1 = Integer.parseInt(o1.get("sort").toString());
				Integer sort2 = Integer.parseInt(o2.get("sort").toString());
				return sort1 - sort2;
			}
		});
		return list;
	}
	
	
	
	
}