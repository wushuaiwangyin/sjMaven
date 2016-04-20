package com.krm.dbaudit.web.all360.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.web.all360.model.PhyTableCngModel;
import com.krm.dbaudit.web.all360.model.TableModelColumns;
import com.krm.dbaudit.web.all360.service.QueryModelService;
import com.krm.dbaudit.web.util.ResponseUtils;

/**查询模型管理
 * @author cat
 *
 */
@Controller
@RequestMapping("cust360/model/manage")
public class ModelManageController extends BaseController {
	@Resource 
	private QueryModelService queryModelService;
	@RequestMapping(value = "{mode}/{foo}/showLayer", method = RequestMethod.POST)	
public String showTablePage(@PathVariable String mode,@PathVariable String foo,@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) {
		String forward="verify/360/modelManage/";
		//判断当前modelid下是否已经存在物理表和物理列的配置关系，如果存在不再添加新物理表信息，但是可以添加或删除物理列
		params.put("model_id", params.get("modelId"));
		List<TableModelColumns>	list=queryModelService.getTabModelCosPageInfo(params);
		if(list.size()>0){
			model.addAttribute("flag", true);//是否在页面显示配置物理表的按钮
			model.addAttribute("tableName", list.get(0).getTabName());
		}else {
			model.addAttribute("flag", false);	
		}
		
		if(StringUtils.equals("add", mode)&& StringUtils.equals("table", foo)){//添加新的物理表信息
			model.addAttribute("tableName",null);
			model.addAttribute("isPublic", null);
			forward+="add_table";
		}
		if(StringUtils.equals("edit", mode)&& StringUtils.equals("table", foo)){//修改物理表信息
			PhyTableCngModel tabcng=new PhyTableCngModel();
			tabcng.setPkid(Long.valueOf( params.get("tableId").toString()));
			tabcng=queryModelService.getPhyTableCng(tabcng);
			model.addAttribute("tableName", tabcng.getPhyTable());
			model.addAttribute("isPublic", tabcng.getIsPublic());
			model.addAttribute("tabId", tabcng.getPkid());
			forward+="add_table";
		}
		if(StringUtils.equals("add", mode)&& StringUtils.equals("cols", foo)){
			forward+="addCols";
		}
		if(StringUtils.equals("edit", mode)&& StringUtils.equals("cols", foo)){
			forward+="edit_colinfo";
		}
	return forward;
	
}
	@RequestMapping(value = "saveTable", method = RequestMethod.POST)
public void ajaxSavePhytable(@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) {
		PhyTableCngModel table=new PhyTableCngModel();
		table.setIsPublic(params.get("isPub").toString());
		table.setPhyTable(params.get("tableName").toString());
		String suss="";
		try {
			queryModelService.savePhyTable(table);
			suss="success";
		} catch (Exception e) {
			suss="error";
		}
		response.setContentType("html/text;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(suss);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
		
		
}	
	
	@RequestMapping(value = "saveColInfo", method = RequestMethod.POST)
public void ajaxSaveColInfo(@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) {
		TableModelColumns tmc=new TableModelColumns();
		tmc.setId(Long.valueOf(params.get("id").toString()));
		tmc.setAlise(params.get("aliseName").toString());
		tmc.setColName(params.get("colName").toString());
		tmc.setDicid(Long.valueOf(params.get("dicid").toString()));
	//	tmc.setIsShow("1");
	//	tmc.setDataType("1");
		String suss="";
			suss=	queryModelService.saveOneColumnInfo(tmc);
		response.setContentType("html/text;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(suss);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
		
		
}	
	@RequestMapping(value="getColInfo",method=RequestMethod.POST)
	public String getColById(@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) {
		TableModelColumns tmc=new TableModelColumns();
		tmc.setId(Long.valueOf(params.get("id").toString()));
		tmc=queryModelService.getColInfo(tmc);
		model.addAttribute("colid", tmc.getId());
		model.addAttribute("tabId", tmc.getTab_id());
		model.addAttribute("aliseName", tmc.getAlise());
		model.addAttribute("colName", tmc.getColName());
		model.addAttribute("dicid", tmc.getDicid());
		
		return "verify/360/modelManage/edit_colinfo";
	}
	@RequestMapping(value="getCols",method=RequestMethod.POST)
	public String getColsByTableName(@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) {
	List colsList=	queryModelService.getColsByTableName(params);
	model.addAttribute("cols", colsList);
/*	JSONObject jsonObject=new JSONObject();
	JSONArray jsonArray=new JSONArray(colsList);
	jsonObject.put("cols", jsonArray);
	 response.setContentType("application/json");  
		response.setCharacterEncoding("UTF-8");  
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}*/
	
	
	return "verify/360/modelManage/edit_colinfo";
	}

	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long modelId){
		return queryModelService.deleteModel(modelId);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="deleteCols",method=RequestMethod.POST)
	public @ResponseBody Integer delCols(String tableid,String modelId,String colId){
		return queryModelService.deleteCols(colId, tableid, modelId);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="detail/showlayer",method=RequestMethod.POST)
	public String layer(@RequestParam Map<String, Object> params, Model model){
	
		model.addAttribute("modelId", params.get("modelId"));
		params.put("model_id", params.get("modelId"));
		List<TableModelColumns>	list=queryModelService.getTabModelCosPageInfo(params);
		if(list.size()>0){
			model.addAttribute("mode", true);
			model.addAttribute("tableName", list.get(0).getTabName());
		}else{
			model.addAttribute("mode", false);
		}
		return "verify/360/modelManage/model_detail";
	}
	
	
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void queryContents(@RequestParam  Map<String, Object> params, 
			HttpServletResponse response){
		params.put("model_id", params.get("modelId"));
		List<TableModelColumns>	list=queryModelService.getTabModelCosPageInfo(params);
		JSONObject json=new JSONObject();
		JSONArray jsonArray=(JSONArray) JSONArray.toJSON(list);
		json.put("total", list.size());
		json.put("list", jsonArray);
		ResponseUtils.renderJson(response, json);
	}
	@RequestMapping(value="checkTableNameRepeat",method=RequestMethod.POST)
public void checkTableNameRepeat(@RequestParam  Map<String, Object> params, 
		HttpServletResponse response){
		PhyTableCngModel tabCng=new PhyTableCngModel();
		tabCng.setPhyTable(params.get("tableName").toString());
		tabCng=queryModelService.getPhyTableCng(tabCng);
		String mess="";
		if(tabCng.getPkid()!=Long.valueOf(0)){
			 mess="对不起，该表的配置信息已存在，不能重复添加！";
		}
		response.setContentType("html/text;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(mess);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
		
}
	
}