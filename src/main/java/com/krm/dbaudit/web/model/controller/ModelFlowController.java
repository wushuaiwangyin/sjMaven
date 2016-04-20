package com.krm.dbaudit.web.model.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.web.model.model.ModelRun;
import com.krm.dbaudit.web.model.service.ModelFlowService;
import com.krm.dbaudit.web.model.service.ModelRunService;
import com.krm.dbaudit.web.util.ResponseUtils;

@Controller
@RequestMapping("model/runflow")
public class ModelFlowController extends BaseController{
	private String BASE_PATH = "model/base/";
	
	@Resource
	private ModelFlowService modelFlowService;
	
	@Resource
	private ModelRunService modelRunService;
	
	@RequestMapping
	public String toModelFlow(Long id, Model model,HttpSession httpSession){
		model.addAttribute("modelid", id);
		//随机数存放在session中，控制一次调用的多个flow任务，表名后缀能够一致，同时控制多人调用的问题。
		httpSession.setAttribute("modelid", id);
		return BASE_PATH + "/model-run";
	}
	
	@RequestMapping(value="tmp",method=RequestMethod.POST)
	public String toModelFlowTmp(Long id, Model model,HttpSession httpSession){
		model.addAttribute("modelid", id);
		//随机数存放在session中，控制一次调用的多个flow任务，表名后缀能够一致，同时控制多人调用的问题。
		httpSession.setAttribute("modelid", id);
		return BASE_PATH + "/model-run-tmp";
	}
	/**
	 * 模型流程数据提取
	* @param params
	* @return json
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="flowinfo",method=RequestMethod.POST)
	public void flowinfo(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		String s = modelFlowService.getFlowById(Long.parseLong(params.get("id").toString()));
		ResponseUtils.renderJson(response, s);
	}
	
	 /**
	 * 脚本测试开始,记录log使用
	* @param 
	* @return
	 */
	@RequestMapping(value="flowrunbegin",method=RequestMethod.POST)
	public @ResponseBody Integer flowrunbegin(Long modelid,HttpSession httpSession){
		Random rdm = new Random(System.currentTimeMillis());
		String serial = "0000"+Math.abs(rdm.nextInt())%1000;
		serial = serial.substring(serial.length()-4, serial.length());
		httpSession.setAttribute("serial", serial);
		httpSession.setAttribute("flowruntime", System.currentTimeMillis());
		return 1;
	}
	
	 /**
	 * 脚本测试
	* @param 
	* @return
	 */
	@RequestMapping(value="flowrun",method=RequestMethod.POST)
	public @ResponseBody Integer flowrun(Long id,HttpSession httpSession){
		if(modelFlowService.doformalflow(id,httpSession.getAttribute("serial").toString()))
		{
			httpSession.setAttribute("flowrunstatus",1);
			return 1;
		}
		httpSession.setAttribute("flowrunstatus",0);
		return 0;
	}
	
	/**
	 * 脚本测试结束
	* @param 
	* @return
	 */
	@RequestMapping(value="flowrunend",method=RequestMethod.POST)
	public @ResponseBody Integer flowrunend(@RequestParam Map<String, Object> params,HttpSession httpSession){
		long runtime = System.currentTimeMillis()- Long.parseLong(httpSession.getAttribute("flowruntime").toString());
		ModelRun modelRun = new ModelRun();
		Date date = new Date((Long)httpSession.getAttribute("flowruntime"));
		modelRun.setRunDate(date);
		modelRun.setRunTime(runtime);
		modelRun.setModelId((Long)httpSession.getAttribute("modelid"));
		modelRun.setRunStatus((Integer)httpSession.getAttribute("flowrunstatus"));
		System.out.println(modelRun);
		modelRun.setRunResultCount(modelFlowService.getModelDataCount((Long)httpSession.getAttribute("modelid"),httpSession.getAttribute("serial").toString()));
		modelRunService.saveModelRun(modelRun);
		
		String tmpflag = params.get("tmpflag").toString();
		if(tmpflag.equals("0")){	//正式流程
			//导数据
			modelFlowService.transResultData((Long)httpSession.getAttribute("modelid"),httpSession.getAttribute("serial").toString());
			//清除临时表
			modelFlowService.clearTempTable(httpSession.getAttribute("serial").toString());
		}
		
		return 1;
	}
	
	/**
	 * 流程运行结束清除数据
	* @param 
	* @return
	 */
	@RequestMapping(value="flowdeletedata",method=RequestMethod.POST)
	public @ResponseBody Integer flowdeletedata(Long modelid,HttpSession httpSession){
		if(httpSession.getAttribute("serial")!=null){
			modelFlowService.clearTempTable(httpSession.getAttribute("serial").toString());
			httpSession.removeAttribute("serial");
		}
		return 1;
	}
	
	/**
	 * 流程数据入Oracle
	* @param 
	* @return
	 */
	@RequestMapping(value="modeltrans")
	public @ResponseBody Integer modeltrans(Long id,HttpSession httpSession){
		if(modelFlowService.transResultData((Long)httpSession.getAttribute("modelid"),httpSession.getAttribute("serial").toString()))
			return 1;
		return 0;
	}
	
	/**
	 * 流程数据显示页面
	* @param 
	* @return
	 */
	@RequestMapping(value="showflowdata",method = RequestMethod.POST)
	public String getflowfield(Long id, Model model,HttpSession httpSession){
	//	List<Map<String, Object>> headerList = dataNoticeService.queryHeaders(params);
		List<Map<String,Object>> headerList = modelFlowService.getTableFieldByFlowID(id);
		model.addAttribute("headerList",headerList);
		model.addAttribute("flowid",id);
		return BASE_PATH + "/showflowdata";
	}
	
	/**
	 * 流程数据显示，提取数据
	* @param 
	* @return
	 */
	@RequestMapping(value="getflowdata",method = RequestMethod.POST)
	public void getflowdata(@RequestParam Map<String, Object> params, Model model,HttpServletResponse response,HttpSession httpSession){
		String tablename = modelFlowService.selectTableNameByFlowID(Long.parseLong(params.get("id").toString()));
		if(httpSession.getAttribute("serial")!=null)
		{
			params.put("serial", httpSession.getAttribute("serial").toString());
			PageInfo<Map<String,Object>> page = modelFlowService.getTableDataByFlowID(params);
			ResponseUtils.renderJson(response, page);
		}
		else
			ResponseUtils.renderJson(response, "");
	}
}
