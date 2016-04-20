package com.krm.dbaudit.web.sys.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.utils.FileUtils;
import com.krm.dbaudit.web.sys.model.KbStd;
import com.krm.dbaudit.web.sys.model.SysAccessory;
import com.krm.dbaudit.web.sys.model.SysDict;
import com.krm.dbaudit.web.sys.service.KbStdEntryService;
import com.krm.dbaudit.web.sys.service.KbStdService;
import com.krm.dbaudit.web.sys.service.SysAccessoryService;
import com.krm.dbaudit.web.sys.service.SysDictService;
import com.krm.dbaudit.web.util.ResponseUtils;

/**
 * 审计依据处理器
 * @author Parker
 *
 */
@Controller
@RequestMapping("repository/kbstd")
public class KbStdController extends BaseController
{
	@Resource
	private SysDictService sysDictService;
	@Resource
	private KbStdService kbStdService;
	@Resource
	private SysAccessoryService sysAccessoryService;
	@Resource
	private KbStdEntryService kbStdEntryService;
	/**
	 * 跳转到菜单管理页面
	 * 加载树
	 * @param model
	 * @return 菜单管理模块html
	 */
	@RequestMapping
	public String toMenu(Model model) {
		List<SysDict> kbstdList = (List<SysDict>) sysDictService.findAllMultimap().get("kbstd_type");
		List<SysDict> list = new ArrayList<SysDict>();
		SysDict sysDict = new SysDict();
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
		model.addAttribute("treeList", JSON.toJSONString(list));
		return "repository/kbstd";
	}
	
	/**
	 * 分页显示菜单table
	 * 
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
		String parentId = null;
		String type = null;
		String name = null;
		PageInfo<KbStd> page = null;
		if(params.containsKey("parentId")){
			parentId = params.get("parentId").toString();
			type = params.get("type").toString();
			if(parentId.equals("null")){
				if(type.equals("1501")){
					params.put("type", null);
					page = kbStdService.findPageInfo(params);
					ResponseUtils.renderJson(response, page);
				}
			}if(parentId.equals("1501")){
				String array [] = type.split(",");
				params.put("type", array);
				page = kbStdService.findPageInfo(params);
				ResponseUtils.renderJson(response, page);
			}
		}else if(params.containsKey("name")){
			name = params.get("name").toString();
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			name = java.net.URLDecoder.decode(name, "UTF-8");
			params.put("name", name);
			page = kbStdService.findPageInfo(params);
			ResponseUtils.renderJson(response, page);
		}else{
			params.put("type", null);
			page = kbStdService.findPageInfo(params);
			ResponseUtils.renderJson(response, page);
		}
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":edit}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		KbStd kbStd = null;
		//编辑页面
		if (StringUtils.equalsIgnoreCase(mode, "edit")){
			kbStd = kbStdService.findById(id);
			Long docId = kbStd.getDocId();
			if(docId != null){
				model.addAttribute("fileName", sysAccessoryService.findById(docId).getFileName());
			}else{
				model.addAttribute("fileName", null);
			}
		}if (StringUtils.equalsIgnoreCase(mode, "detail")){
			kbStd = kbStdService.findById(id);
			Long docId = kbStd.getDocId();
			if(docId != null){
				model.addAttribute("fileName", sysAccessoryService.findById(docId).getFileName());
			}else{
				model.addAttribute("fileName", null);
			}
		}
		model.addAttribute("kbstd", kbStd);
		return  mode.equals("detail") ? "sys/repository/kbstd/kbstd-detail" 
				: "sys/repository/kbstd/kbstd-edit";
	}
	
	
	
		
		
	/**
	 * 保存或修改数据
	 * @param kbStd
	 * @param file
	 * @param request
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute KbStd kbStd, 
			@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap mode){
		Long docId = null;
		Integer count1 = null;
		if(file != null){
			SysAccessory sysAccessory = new SysAccessory();
			InputStream is=null;
			try
			{
				String fileName = FileUtils.getFileNameNoSuffix(file.getOriginalFilename()); 
				String extName = FileUtils.getFileSuffix(file.getOriginalFilename());
				is = file.getInputStream();
				byte[] data = new byte[(int) file.getSize()];  
				is.read(data);
				//编辑时，审计依据里有docId，将此id设置为附件id
				//新增时，没有此id，自增
				docId = kbStd.getDocId();					
				if(docId != null){							
					sysAccessory.setDocId(docId);
				}
				sysAccessory.setDocType("1501");
				sysAccessory.setFileName(fileName);
				sysAccessory.setAccessory(data);
				sysAccessory.setExtName(extName);
				sysAccessory.setStatus("1");
				count1 = sysAccessoryService.saveFile(sysAccessory);
			} catch (IOException e)
			{
				return -1;
			}
		}
		//新增时，审计依据没有docId,从附件产生的id中获取
		//编辑时，默认不变
		docId = kbStd.getDocId();								
		if(docId == null){										
			if(count1 != null && count1 > 0){
				docId = sysAccessoryService.getDocId() -1L;
				kbStd.setDocId(docId);
			}
		}
		if(kbStd.getDisporder() == null){
			Long disporder = Long.parseLong(kbStd.getStdCode().substring(6));
			kbStd.setDisporder(disporder);
		}if(kbStd.getStatus() == null){
			kbStd.setStatus("1");
		}
		Integer count2 = kbStdService.save(kbStd);
		if(count2 != null && count2 > 0){
			return count2;
		}
		return -1;
	}
	
	
	/**
	 * 删除审计依据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Integer dels(Long id) {
		SysAccessory sysAccessory = null;
		Long docId = null;
		Integer count = 0;
		if (null != id) {
			sysAccessory = sysAccessoryService.findById(id);
			if(sysAccessory != null){
				docId = sysAccessory.getDocId();
			}
			count = kbStdService.deleteKbstdByRootId(id);
		}
		if(count != null && count >0){
			if(docId != null){
				sysAccessoryService.deleteAccessoryByRootId(docId);
			}
		}
		return count;
	}
	
}
