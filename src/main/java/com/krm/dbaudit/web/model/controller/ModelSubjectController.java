package com.krm.dbaudit.web.model.controller;

import java.util.ArrayList;
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
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.model.model.ModelSubject;
import com.krm.dbaudit.web.model.service.ModelPropertyService;
import com.krm.dbaudit.web.model.service.ModelSubjectService;
import com.krm.dbaudit.web.sys.model.SysDict;
import com.krm.dbaudit.web.sys.service.SysDictService;
import com.krm.dbaudit.web.util.ResponseUtils;

/**
* @author taosq on 2015-08-13
*/

@Controller
@RequestMapping("model/subject")
public class ModelSubjectController extends BaseController {
	
	
	@Resource
	private ModelSubjectService modelSubjectService;
	
	@Resource
	private ModelPropertyService modelPropertyService;
	
	@Resource
	private SysDictService sysDictService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toModelSubject(Model model){
		model.addAttribute("treeList", createTree(false));
		return "model/subject/index";
	}
	
	

	/**
	 * 主题树
	 * @return
	 */
	@RequestMapping(value = "tree")
	public @ResponseBody List<ModelSubject> tree(Long property,String line) {
		if(property == null && line == null){
			return createTree(true);
		}else{
			ModelSubject ms = new ModelSubject();
			ms.setProperty(property);
			ms.setBuzLine(line);
			return modelSubjectService.select(ms);
		}
	}
	
	
	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,Model model,HttpServletResponse response){
		if(params.containsKey("sortC")){
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<ModelSubject> page = modelSubjectService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute ModelSubject modelSubject){
		return modelSubjectService.saveModelSubject(modelSubject);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return modelSubjectService.deleteModelSubject(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return modelSubjectService.deleteModelSubject(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode,String line,Long property,Long parentId, Model model){
		ModelSubject modelSubject = null;
		if(StringUtils.equals("edit", mode)){
			modelSubject = modelSubjectService.selectByPrimaryKey(id);
			model.addAttribute("subject", modelSubject);
			model.addAttribute("buzLine",modelSubject.getBuzLine());
			ModelProperty mp = modelPropertyService.selectByPrimaryKey(modelSubject.getProperty());
			model.addAttribute("property",mp);
			if(modelSubject.getParentId() != null && modelSubject.getParentId() != 0){
				ModelSubject pSubject = modelSubjectService.selectByPrimaryKey(modelSubject.getParentId());
				model.addAttribute("pSubject", pSubject);
			}
		}else if(StringUtils.equals("detail", mode)){
			modelSubject = modelSubjectService.findById(id);
			model.addAttribute("item", modelSubject);
		}else if(StringUtils.equals("add", mode)){
			//如果传递了父编号，就已知属性
			if(parentId != null){
				ModelSubject pSubject = modelSubjectService.selectByPrimaryKey(parentId);
				ModelProperty mp = modelPropertyService.selectByPrimaryKey(pSubject.getProperty());
				model.addAttribute("property",mp);
				if(mp != null && mp.getHaveLine() == 1){//如果有条件，直接使用父节点的条线
					model.addAttribute("buzLine",pSubject.getBuzLine());
				}
				model.addAttribute("pSubject", pSubject);
			}else{
				if(line != null){
					model.addAttribute("buzLine",line);
				}
				if(property != null){
					ModelProperty mp = modelPropertyService.selectByPrimaryKey(property);
					model.addAttribute("property",mp);
				}
			}
		}
		
		return mode.equals("detail")?"model/subject/detail":"model/subject/save";
	}
	
	
	/************************创建树********************************/
	private List<ModelSubject> createTree(boolean delNoSubject){
		List<ModelSubject> lists = new ArrayList<ModelSubject>();
		
		ModelSubject ms  = null;
		List<ModelProperty> mps = modelPropertyService.findAllProperty();
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
				
				//解决主题分类
				if(mp.getHaveSubject() == 1){
					//从缓存中读取
					List<ModelSubject> subjects = modelSubjectService.findAllSubject();
					for (ModelSubject subject : subjects) {
						if(subject.getProperty() != null && subject.getProperty().longValue() == mp.getId().longValue()){
							ms =  new ModelSubject();
							ms.set("id", "s_"+ subject.getId());
							ms.setName(subject.getName());
							if(subject.getParentId() == null || subject.getParentId() == 0){//根节点，重新计算其父ID
								if(mp.getHaveLine() == 1 && subject.getBuzLine() != null){
									ms.set("parentId", "l_"+ mp.getId() + "_" +subject.getBuzLine());
								}else{
									ms.set("parentId", "p_"+mp.getId());
								}
							}else{
								ms.set("parentId","s_" + subject.getParentId());
							}
							lists.add(ms);
						}
					}
				}
			}
		}
		return lists;
	}
	
}
