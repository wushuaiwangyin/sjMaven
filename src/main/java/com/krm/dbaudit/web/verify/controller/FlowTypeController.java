package com.krm.dbaudit.web.verify.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.web.verify.model.FlowType;
import com.krm.dbaudit.web.verify.service.FlowTypeService;

/**
* @author taosq on 2015-08-13
*/

@Controller
@RequestMapping("flow/type")
public class FlowTypeController extends BaseController {
	
	
	@Resource
	private FlowTypeService flowTypeService;
	
	
	/**
	 * 流程树，连流程一起返回
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.POST)
	public @ResponseBody List<FlowType> getFlowTypeTreeList() {
		List<FlowType> list = flowTypeService.findFlowTree();
		return list;
	}

	/**
	 * 添加或更新流程分类
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute FlowType flowType) {
		return flowTypeService.saveFlowType(flowType);
	}

	/**
	 * 删除流程分类及其子流程分类
	 * 
	 * @param resourceId
	 *            流程分类id
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Integer dels(Long id) {
		Integer count = 0;
		if (null != id) {
			count = flowTypeService.deleteFlowType(id);
		}
		return count;
	}

	/**
	 * 分页显示流程分类table
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model) {
		PageHelper.startPage(params);
		PageInfo<FlowType> page = flowTypeService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sys/area/area-list";
	}

	/**
	 * 弹窗
	 * 
	 * @param id
	 * @param parentId
	 *            父类id
	 * @param mode
	 *            模式(add,edit,detail)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showLayer(Long id, Long parentId,
			@PathVariable("mode") String mode, Model model) {
		FlowType type = null, pType = null;
		if (StringUtils.equalsIgnoreCase(mode, "add")) {
			pType = flowTypeService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "edit")) {
			type = flowTypeService.selectByPrimaryKey(id);
			pType = flowTypeService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "detail")) {
			type = flowTypeService.selectByPrimaryKey(id);
			pType = flowTypeService.selectByPrimaryKey(type.getParentId());
		}
		model.addAttribute("pType", pType).addAttribute("type", type);
		return mode.equals("detail") ? "verify/flow/flow-type-detail"
				: "verify/flow/flow-type-save";
	}
	

}
