package com.krm.dbaudit.web.sys.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.web.sys.model.SysDict;
import com.krm.dbaudit.web.sys.service.KbAuditService;
import com.krm.dbaudit.web.sys.service.KbProfitService;
import com.krm.dbaudit.web.sys.service.KbStdEntryService;
import com.krm.dbaudit.web.sys.service.KbStdService;
import com.krm.dbaudit.web.sys.service.KbTrainService;
import com.krm.dbaudit.web.sys.service.SysAccessoryService;
import com.krm.dbaudit.web.sys.service.SysDictService;

/**
 * 审计依据处理器
 * @author Parker
 *
 */
@Controller
@RequestMapping("repository")
public class RepositoryController extends BaseController
{
	@Resource
	private SysDictService sysDictService;
	@Resource
	private KbStdService kbStdService;
	@Resource
	private SysAccessoryService sysAccessoryService;
	@Resource
	private KbStdEntryService kbStdEntryService;
	@Resource
	private KbProfitService kbProfitService;
	@Resource
	private KbAuditService kbAuditService;
	@Resource
	private KbTrainService kbTrainService;
	/**
	 * 跳转到菜单管理页面
	 * 加载树
	 * @param model
	 * @return 菜单管理模块html
	 */
	@RequestMapping
	public String toMenu(Model model) {
		List<SysDict> kbstdList = (List<SysDict>) sysDictService.findAllMultimap().get("kbstd_type");
		List<SysDict> kbprofitList = (List<SysDict>) sysDictService.findAllMultimap().get("kbprofit_type");
		List<SysDict> businessList = (List<SysDict>) sysDictService.findAllMultimap().get("business_type");
		List<SysDict> list = new ArrayList<SysDict>();
		SysDict sysDict = new SysDict();
		//审计依据树
		for (SysDict temp : kbstdList)
		{
			sysDict = new SysDict();
			if(temp.getId() == 1501L){
				sysDict.set("parentId", 0L);
			}else{
				sysDict.set("parentId", 1501L);
			}
			sysDict.setId(temp.getId());
			sysDict.set("name", temp.getLabel());
			sysDict.setValue(temp.getValue());
			list.add(sysDict);
		}
		//审计成果树
		for (SysDict temp : kbprofitList)
		{
			sysDict = new SysDict();
			if(temp.getId() == 1503L){
				sysDict.set("parentId", 0L);
			}else{
				sysDict.set("parentId", 1503L);
			}
			sysDict.setId(temp.getId());
			sysDict.set("name", temp.getLabel());
			sysDict.setValue(temp.getValue());
			list.add(sysDict);
		}
		//审计程序树
		for (SysDict temp : businessList)
		{
			sysDict = new SysDict();
			if(temp.getId() == 1401L){
				sysDict.set("parentId", 0L);
			}else{
				sysDict.set("parentId", 1401L);
			}
			sysDict.setId(temp.getId());
			sysDict.set("name", temp.getLabel());
			sysDict.setValue(temp.getValue());
			list.add(sysDict);
		}
		//培训学习树
		sysDict = new SysDict();
		sysDict.set("parentId", 0L);
		sysDict.setId(1402L);
		sysDict.set("name", "培训学习");
		sysDict.setValue("PX");
		list.add(sysDict);
		model.addAttribute("treeList", JSON.toJSONString(list));
		return "sys/repository/index";
	}
	
	
	
	/**
	 * 获取审计依据编码并跳转
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String addNew(@RequestParam Map<String, Object> params, Model model) throws Exception{
		String code = null;
		List<Map<String,Object>> stdCodeList = new ArrayList<Map<String,Object>>();
		String codePre = params.get("codePre").toString();
		String type = params.get("type").toString();
		String title = null;
		String name = null;
		//从通知书反馈传来的title需要处理乱码问题，涉及到浏览器不同导致乱码无法处理，后台直接赋值
		if(params.containsKey("title")){
			title = "典型问题";
//			title = java.net.URLDecoder.decode(new String(params.get("title").toString().getBytes("ISO-8859-1"),"UTF-8"), "UTF-8");
		}else{
			name = params.get("name").toString();
		}
		String parentId = params.get("parentId").toString();
		if(parentId.equals("1501")){
			stdCodeList = kbStdService.findCodes(codePre);
		}if(parentId.equals("1503")){
			stdCodeList = kbProfitService.findCodes(codePre);
		}if(parentId.equals("1401")){
			stdCodeList = kbAuditService.findCodes(codePre);
		}if(type.equals("1402")){
			stdCodeList = kbTrainService.findCodes(codePre);
		}
		//得到中间四位当前日期的年份
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);
		String year = Integer.toString(y);
		
		// 把后三们序号作元素放到一个列表中
		List<Integer> orderList = new ArrayList<Integer>();
		for (Map<String, Object> map : stdCodeList)
		{
			code = map.get("code").toString();
			orderList.add(new Integer(code.substring(code.length() - 3)));
		}
		// 从1开始，到999看哪个元素可用，形成检查点编码返回
		for (int i = 1; i < 1000; i++){
			if (!orderList.contains(i)){
				code = codePre + year + String.format("%03d", i);
				break;
			}
		}
		model.addAttribute("code", code);
		model.addAttribute("type", type);
		if(params.containsKey("title")){
			model.addAttribute("name", title);
		}else{
			model.addAttribute("name", name);
		}
		if(parentId.equals("1501")){
			return "sys/repository/kbstd/kbstd-save";
		}if(parentId.equals("1503")){
			return "sys/repository/kbprofit/kbprofit-save";
		}if(parentId.equals("1401")){
			return "sys/repository/kbaudit/kbaudit-save";
		}if(type.equals("1402")){
			return "sys/repository/kbtrain/kbtrain-save";
		}
		return null;
	}
		
		
	
}
