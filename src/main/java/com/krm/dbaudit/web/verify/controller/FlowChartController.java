package com.krm.dbaudit.web.verify.controller;

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
import com.krm.dbaudit.web.sys.model.SysArea;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.verify.model.FlowChart;
import com.krm.dbaudit.web.verify.model.FlowType;
import com.krm.dbaudit.web.verify.service.FlowChartService;
import com.krm.dbaudit.web.verify.service.FlowTypeService;

/**
* @author taosq on 2015-08-13
*/

@Controller
@RequestMapping("verify/flow")
public class FlowChartController extends BaseController {
	
	
	@Resource
	private FlowChartService flowChartService;
	
	@Resource
	private FlowTypeService flowTypeService;
	
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toflowChart(Model model){
		List<FlowType> treeList = flowTypeService.findFlowTree();
		model.addAttribute("treeList", treeList);
		return "verify/flow/flow";
	}
	
	@RequestMapping(value="tree",method=RequestMethod.POST)
	public void getTree(Model model,HttpServletResponse response){
		List<FlowType> treeList = flowTypeService.findFlowTree();
		ResponseUtils.renderJson(response, treeList);
	}
	
	
	@RequestMapping(value="draw")
	public String toDraw(Model model){
		model.addAttribute("treeList", JSON.toJSONString(flowTypeService.findAllFlowType()));
		return "verify/flow/draw";
	}
	
	@RequestMapping(value="{id}/edit")
	public String toDraw(Model model,@PathVariable Long id){
		FlowChart flow = flowChartService.selectByPrimaryKey(id);
		if(flow != null){
			flow.set("xml", "");//为了传输效率，将xml置空
			model.addAttribute("flow",JSON.toJSON(flow) );
		}
		model.addAttribute("treeList", JSON.toJSONString(flowTypeService.findAllFlowType()));
		return "verify/flow/draw";
	}
	
	@RequestMapping(value="{id}/show")
	public String toShow(Model model,@PathVariable Long id){
		if(id != null){
			FlowChart flow = flowChartService.selectByPrimaryKey(id);
			if(flow != null){
				model.addAttribute("id",id);
				return "verify/flow/show";
			}
		}
		return "error/404";
	}
	
	@RequestMapping(value="getXml",method=RequestMethod.POST)
	public @ResponseBody String getXml(Long id){
		FlowChart fl = flowChartService.getById(id);
		if(fl != null){
			return fl.getXml();
		}
		return "";
	}

	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<FlowChart> page = flowChartService.findPageInfo(params);
		model.addAttribute("page", page);
		return "flowChart/flowChart-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Long save(@ModelAttribute FlowChart flowChart){
		return flowChartService.saveFlowChart(flowChart);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return flowChartService.deleteFlowChart(id);
	}
	
	/**
	 * 批量删除
	* @param
	* @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return flowChartService.deleteFlowChart(ids);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		FlowChart flowChart = null;
		if(StringUtils.equals("edit", mode)){
			flowChart = flowChartService.selectByPrimaryKey(id);
			model.addAttribute("flowChart", flowChart);
		}else if(StringUtils.equals("detail", mode)){
			flowChart = flowChartService.selectByPrimaryKey(id);
			model.addAttribute("flowChart", flowChart);
		}
		
		return mode.equals("detail")?"flowChart/flowChart-detail":"flowChart/flowChart-save";
	}
	

}
