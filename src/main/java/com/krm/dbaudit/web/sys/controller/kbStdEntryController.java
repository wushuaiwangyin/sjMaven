package com.krm.dbaudit.web.sys.controller;

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

import com.krm.dbaudit.web.sys.model.KbStd;
import com.krm.dbaudit.web.sys.model.KbStdEntry;
import com.krm.dbaudit.web.sys.service.KbStdEntryService;
import com.krm.dbaudit.web.sys.service.KbStdService;
import com.krm.dbaudit.web.util.ResponseUtils;

/**
 * 审计依据条目处理器
 * @author Parker
 *
 */
@Controller
@RequestMapping("kbStdEntry")
public class kbStdEntryController
{
	@Resource
	private KbStdEntryService kbStdEntryService;
	@Resource
	private KbStdService kbStdService;
	
	
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.detail 2.add 3.edit}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,String stdCode,@PathVariable String mode, Model model){
		KbStd kbStd = null;
		//编辑页面
		if (StringUtils.equalsIgnoreCase(mode, "detail")){
			kbStd = kbStdService.findById(id);
			stdCode = kbStd.getStdCode();
			model.addAttribute("stdCode", stdCode);
			return "sys/repository/entry/entry-detail";
		//新增页面
		}else if (StringUtils.equalsIgnoreCase(mode, "add")){
			kbStd = kbStdService.findById(id);
			String entryCode = kbStdEntryService.getEntryCode(kbStd.getStdCode());
			model.addAttribute("entryCode", entryCode);
			model.addAttribute("stdCode", kbStd.getStdCode());
			return  "sys/repository/entry/entry-save";
		}else if (StringUtils.equalsIgnoreCase(mode, "edit")){
			KbStdEntry kbStdEntry = kbStdEntryService.selectByPrimaryKey(id);
			model.addAttribute("kbStdEntry", kbStdEntry);
			model.addAttribute("stdCode", stdCode);
			return "sys/repository/entry/entry-edit";
		}if (StringUtils.equalsIgnoreCase(mode, "detail1")){
			kbStd = kbStdService.findById(id);
			stdCode = kbStd.getStdCode();
			model.addAttribute("stdCode", stdCode);
			return "repository/entry-detail";
		}
		return "";
	}
	
	
	/**
	 * 审计依据条目详细列表
	 * @param params
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "entryList", method = RequestMethod.POST)
	public void entryList(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
		//加载树节点数据，带上依据类型
		if(params.containsKey("stdCode")){
			params.put("stdCode",params.get("stdCode").toString());
		}
		List<KbStdEntry> list = kbStdEntryService.findPageInfo(params);
		ResponseUtils.renderJson(response, list);
	}
	
	
	/**
	 * 保存或更改审计依据条目
	 * @param kbStdEntry
	 * @return
	 */
	@RequestMapping(value = "saveKbstdEntry", method = RequestMethod.POST)
	public @ResponseBody Integer saveKbstdEntry(@ModelAttribute KbStdEntry kbStdEntry){
		Integer count = null;
		//顺序和条目一致
		kbStdEntry.setDisporder(Long.parseLong(kbStdEntry.getEntryCode()));
		//默认状态为1
		kbStdEntry.setStatus("1");
		count = kbStdEntryService.saveKbstdEntry(kbStdEntry);
		if(count != null && count > 0){
			return count;
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
		Integer count = null;
		count = kbStdEntryService.deleteKbstdEntryByRootId(id);
		if(count != null && count >0){
			return count;
		}
		return -1;
	}
}
