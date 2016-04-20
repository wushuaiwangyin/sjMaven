package com.krm.dbaudit.web.model.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.common.utils.SysConstant;
import com.krm.dbaudit.web.model.model.AuditModel;
import com.krm.dbaudit.web.model.model.AuditType;
import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.model.model.ModelSubject;
import com.krm.dbaudit.web.model.service.AuditModelService;
import com.krm.dbaudit.web.model.service.AuditTypeService;
import com.krm.dbaudit.web.model.service.ModelBaseService;
import com.krm.dbaudit.web.model.service.ModelPropertyService;
import com.krm.dbaudit.web.model.service.ModelSubjectService;
import com.krm.dbaudit.web.sys.model.SysDict;
import com.krm.dbaudit.web.sys.service.SysDictService;
import com.krm.dbaudit.web.util.DateUtil;
import com.krm.dbaudit.web.util.ResponseUtils;

/**
* @author taosq on 2015-08-13
*/

@Controller
@RequestMapping("model/audit/type")
public class AuditTypeController extends BaseController {
	
	@Resource
	private ModelSubjectService modelSubjectService;
	
	@Resource
	private ModelPropertyService modelPropertyService;
	
	@Resource
	private SysDictService sysDictService;
	
	private String BASE_PATH = "model/audit/type/";
	
	@Resource
	private AuditTypeService auditTypeService;
	
	@Resource
	private AuditModelService auditModelService;
	
	@Resource
	private ModelBaseService modelBaseService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toAuditType(Model model){
		model.addAttribute("treeList", auditTypeService.findAllAuditType());
		return BASE_PATH +"index";
	}
	
	
	@RequestMapping(value = "tree")
	public @ResponseBody List<AuditType> tree(Long property,String line) {
		return auditTypeService.findAllAuditType();
		
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
		PageInfo<AuditType> page = auditTypeService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute AuditType auditType){
		return auditTypeService.saveAuditType(auditType);
	}
	
	
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return auditTypeService.deleteAuditType(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return auditTypeService.deleteAuditType(ids);
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
			auditType = auditTypeService.selectByPrimaryKey(id);
			model.addAttribute("type", auditType);
			if(auditType.getParentId() != null){
				model.addAttribute("pType", auditTypeService.selectByPrimaryKey(auditType.getParentId()));
			}
		}else if(StringUtils.equals("detail", mode)){
			auditType = auditTypeService.findById(id);
			model.addAttribute("item", auditType);
		}else if(StringUtils.equals("add", mode)){
			model.addAttribute("pType", auditTypeService.selectByPrimaryKey(parentId));
		}
		
		return mode.equals("detail")? BASE_PATH + "detail":BASE_PATH +"save";
	}
	
	@RequestMapping(value="relationship",method=RequestMethod.POST)
	public String relationship(Long id,Long parentId, Model model){
		
		List treeList = createTree(true);
		AuditModel am = new AuditModel();
		am.setType(id);
		//List<AuditModel>  yxlist = auditModelService.select(am);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", id);
		List<AuditModel>  yxlist  =auditModelService.findAuditModel(params);
		
		model.addAttribute("treeList", treeList);
		model.addAttribute("yxlist", yxlist);
		model.addAttribute("typeid", id);
		
		return "mconfig/ttype/model";
	}
	
	
	@RequestMapping(value="getallmodel",method=RequestMethod.POST)
	public void getAllModel(@RequestParam Map<String, Object> params,HttpServletResponse response){
		
		String id = (String) params.get("id");
		if(null!=id){
			String [] idarr = id.split("_");
			if(idarr[0].equals("p")){
				//属性
				params.put("property", idarr[1]);
			}else if(idarr[0].equals("l")){
				//属性，条件
				params.put("property", idarr[1]);
				params.put("buz_line", idarr[2]);
			}
		}
		List<ModelBase>  modelist = modelBaseService.findModelList(params);
		String  jsonstr = JSON.toJSONString(modelist);
		System.out.println(jsonstr);
		ResponseUtils.renderJson(response, modelist);
	}
	
	@RequestMapping(value="saverelation",method=RequestMethod.POST)
	public void saverelation(@RequestParam Map<String, Object> params,HttpServletResponse response){
		
		String idstr = (String) params.get("idarr");
		Long typeid = Long.parseLong((String) params.get("type")) ;
		if(null!=idstr&&!"".equals(idstr)){
			String [] idarr = idstr.split(",");
			Long[] ids = new Long[idarr.length];
			Map<String,Object> mp = null;
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(int i=0;i<idarr.length;i++){
				mp = new HashMap<String,Object>();
				mp.put("model",Long.parseLong(idarr[i]));
				mp.put("type", typeid);
				mp.put("create_by", "");
				mp.put("create_date", new Date());
				mp.put("update_by", "");
				mp.put("update_date", new Date());
				mp.put("sort", "");
				mp.put("del_flag", "0");
				
				list.add(mp);
				ids[i]=Long.parseLong(idarr[i]);
			}
			
			//先删除该专项下所有关联的模型
			if(ids.length>0){
				auditTypeService.deleteAuditModelByType(typeid);
				//插入新关联的模型
				int count = auditTypeService.batchInsert(list);
				
				ResponseUtils.renderText(response, count);
				
				
				System.out.println("count:"+count);
			}
		}else{
			auditTypeService.deleteAuditModelByType(typeid);
			ResponseUtils.renderText(response, 1);
			
		}
		
		
		
		
	}
	
	
	
	
	
	
	/************************创建树********************************/
	private List<ModelSubject> createTree(boolean delNoSubject){
		List<ModelSubject> lists = new ArrayList<ModelSubject>();
		
		ModelSubject ms  = null;
		ModelProperty modelProperty = new ModelProperty();
		modelProperty.setHaveLine(1);
		List<ModelProperty> mps = modelPropertyService.findAllProperty(modelProperty);
		if(!mps.isEmpty()){
			for (ModelProperty mp : mps) {
				if(delNoSubject){
					if(mp.getHaveSubject().longValue() == 0){
						continue;
					}
				}
				ms =  new ModelSubject();
				ms.set("id", "p_"+mp.getId());
				ms.setName(mp.getName());
				ms.setParentId(0L);
				ms.set("haveLine",mp.getHaveLine());
				ms.set("haveSubject",mp.getHaveSubject());
				lists.add(ms);
				
				//解决业务条线
				if(mp.getHaveLine() == 1){
					List<SysDict> dicts = (List<SysDict>) sysDictService.findAllMultimap().get("model_buz_line");
					for (SysDict dic : dicts) {
						ms =  new ModelSubject();
						ms.set("id", "l_"+ mp.getId() + "_" +dic.getValue());
						ms.setName(dic.getLabel());
						ms.set("parentId", "p_"+mp.getId());
						lists.add(ms);
					}
				}
			}
		}
		return lists;
	}
	

}
