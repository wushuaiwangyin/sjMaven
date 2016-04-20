package com.krm.dbaudit.web.sys.controller;

import java.util.HashMap;
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
/*
 * add by TanWen 20151127 源数据管理
 */
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.model.model.AuditType;
import com.krm.dbaudit.web.sys.model.SrcDataTable;
import com.krm.dbaudit.web.sys.model.SrcDataType;
import com.krm.dbaudit.web.sys.service.SrcDataService;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;

@Controller
@RequestMapping("srcdata")
public class SrcDataController extends BaseController {

	@Resource
	private SrcDataService srcDataService;
	
	private String BASE_PATH = "sys/srcdata/";
	/**
	 * 跳转到源数据管理主页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toModelBase(Model model){
		model.addAttribute("treeList", srcDataService.findAllSrcDataType());
		return "sys/srcdata/index";
	}
	
	@RequestMapping(value="test")
	public String test(String tableName, Model model){
		return "sys/srcdata/test";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SrcDataType srcDataType,@RequestParam Map<String, Object> params){
		srcDataType.setTypeFlag((long)0);	//源数据管理模块新建分类，默认为0，系统使用。
		return srcDataService.saveSrcDataType(srcDataType);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return srcDataService.deleteSrcDataTypeById(id);
	}
	
	@RequestMapping(value = "tree")
	public @ResponseBody List<SrcDataType> tree(Long property,String line) {
		return srcDataService.findAllSrcDataType();
		
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
		PageInfo<SrcDataType> page = srcDataService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,Long parentId,@PathVariable String mode, Model model){
		AuditType auditType = null;
		if(StringUtils.equals("edit", mode)){
			//auditType = auditTypeService.selectByPrimaryKey(id);
			//model.addAttribute("type", auditType);
			//if(auditType.getParentId() != null){
			//	model.addAttribute("pType", auditTypeService.selectByPrimaryKey(auditType.getParentId()));
			//}
		}else if(StringUtils.equals("detail", mode)){
			//auditType = auditTypeService.findById(id);
			//model.addAttribute("item", auditType);
		}else if(StringUtils.equals("add", mode)){
			model.addAttribute("pType", srcDataService.selectByPrimaryKey(parentId));
		}
		
		return mode.equals("detail")? BASE_PATH + "detail":BASE_PATH +"save";
	}
	
	
	/**
	 * 弹窗显示
	* @param 
	* @return
	 */
	@RequestMapping(value="showsavetable",method=RequestMethod.POST)
	public String showsavetable(String tableName, Model model){
		SrcDataType srcDataType = null;
		
		//根据表名
		SrcDataTable srctable = srcDataService.findSrcTableByName(tableName);
		model.addAttribute("srctable", srctable);
		
		srcDataType = srcDataService.selectByPrimaryKey(srctable.getTableTypeid());
		model.addAttribute("pType", srcDataType);
		
		return BASE_PATH +"savetable";
	}
	
	
	@RequestMapping(value="savetable",method=RequestMethod.POST)
	public @ResponseBody Integer savetable(@ModelAttribute SrcDataTable srcDataTable){
		return srcDataService.saveSrcTable(srcDataTable);
	}
	
	@RequestMapping(value="savenewtable",method=RequestMethod.POST)
	public @ResponseBody void savenewtable(@RequestParam Map<String, Object> params,HttpServletResponse response){
		params.put("createBy", SysUserUtils.getCacheLoginUser().getId());
		ResponseUtils.renderJson(response, srcDataService.savenewtable(params));
	}
	
	@RequestMapping(value="searchlayer")
	public String searchlayer(Model model){
		//加载源数据分类
		 List<SrcDataType> treeList =srcDataService.findAllSrcDataType();
		 
		 Map<String, Object> params = new HashMap<String, Object>();
		 //加载源数据表
		 List<SrcDataType> srctabelList = srcDataService.findSrcTable(params);
		 
		 treeList.addAll(srctabelList);
		
		model.addAttribute("treeList", treeList);
		
		return "verify/resourceData/index";
	}
	
	
	@RequestMapping(value="createlayer")
	public String createlayer(Model model){
		//加载源数据分类
		 List<SrcDataType> treeList =srcDataService.findAllSrcDataType();
		 
		
		return BASE_PATH +"createtable";
	}
	
}
