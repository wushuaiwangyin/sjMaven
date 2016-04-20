package com.krm.dbaudit.web.verify.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.sys.model.SysUser;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;
import com.krm.dbaudit.web.verify.model.CapitalFlow;
import com.krm.dbaudit.web.verify.model.CapitalFlowDps;
import com.krm.dbaudit.web.verify.model.CusGuarFlow;
import com.krm.dbaudit.web.verify.model.CusGuarFlowLoan;
import com.krm.dbaudit.web.verify.service.CapitalFlowService;
import com.krm.dbaudit.web.verify.service.CusGuarFlowService;
@Controller
@RequestMapping("verify/cusguarflow")
public class CusGuarFlowController extends BaseController {
	
	@Resource
	private CusGuarFlowService cusGuarFlowService;
	
	
	/**
	 * 默认跳转到主页面
	* @return 
	 */
	@RequestMapping
	public String toCusGuarFlow(){
		return "verify/cusGuarFlow/index";
	}
	
	
	@RequestMapping(value="searchflow",method = RequestMethod.POST)
	public void getCapitalFlow(@RequestParam Map<String, Object> params,
			HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> maps = new HashMap<String,Object>();
		String amt = params.get("amt")==null?null:(String) params.get("amt");
		String cusid = params.get("cusid")==null?null:(String)params.get("cusid");
		Integer queryCount = params.get("queryCount")==null?null:Integer.parseInt(params.get("queryCount").toString());
		if(null!=cusid && !"".equals(cusid)){
			maps.put("IN_GUAR_NO", cusid);
			maps.put("IN_AMT", amt);
			maps.put("IN_STEP", 0);
			maps.put("IN_SEARCH_ID", null);
			maps.put("IN_GUAR_START_DATE", null);
			cusGuarFlowService.callProSerachFlow(maps);

			Integer searchId = (Integer) maps.get("OUT_STEP");
			
			
			//根据查询编号获取所有的交易记录
			if(null!=searchId && searchId != 0){
				Map<String,Object> sm = new HashMap<String,Object>();
				sm.put("search_flow", searchId);
				
				//查询当前查询最大关联次数
				Integer maxStep = cusGuarFlowService.getSearchFlowMaxStep(maps);
				
				//最大关联次数大于查询条件中是关联次数才继续
				if(null != maxStep && null != params.get("queryCount") && 
						maxStep >= Integer.parseInt((String)params.get("queryCount"))){
					
					//按search_flow分组并返回列表
					Map<String,Object> params2 = new HashMap<String,Object>();
					params2.put("search_flow", searchId);
					List<CusGuarFlowLoan> grouplist = cusGuarFlowService.getDpsSearchFlowGroup2(params2);
					String josnstr = JSON.toJSONString(grouplist);
					System.out.println(josnstr);
					ResponseUtils.renderJson(response, josnstr);
			}else{
				//不满足条件
				ResponseUtils.renderText(response, -1);
			}
		}
		
		
		}else{
			//不输入客户号进行查询
			SysUser user = SysUserUtils.getSessionLoginUser();
			maps.put("IN_USER", user.getUsername());
			maps.put("IN_AMT",amt);
			maps.put("IN_GUAR_START_DATE", null);
			cusGuarFlowService.callProSerachFlowNotCusId(maps);
			
			Integer rownums = (Integer) maps.get("OUT_ROWNUM");
			
			if(rownums>queryCount){
				//取出明细
				Map<String,Object> params1 = new HashMap<String,Object>();
				params1.put("search_user", user.getUsername());
				//对经过金额过滤的中间表进行循环处理CUS_GUAR_LOAN_REL_TEMP_2
				List<CusGuarFlowLoan> list = cusGuarFlowService.getGuarList(params1);
				for(CusGuarFlowLoan guar : list ){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("IN_GUAR_NO", guar.getGuarNo());
					map.put("IN_STEP", 0);
					map.put("IN_SEARCH_ID", null);
					map.put("IN_GUAR_START_DATE", null);
					map.put("IN_SEARCH_USER", user.getUsername());
					cusGuarFlowService.callProSerachFlowGuar(map);
				}
				
				//按search_flow分组并返回列表
				Map<String,Object> params2 = new HashMap<String,Object>();
				params2.put("search_step", 3);
				params2.put("search_user", user.getUsername());
				List<CusGuarFlowLoan> grouplist = cusGuarFlowService.getGuarSearchFlowGroup(params2);
				String josnstr = JSON.toJSONString(grouplist);
				System.out.println(josnstr);
				ResponseUtils.renderJson(response, josnstr);
			}
		}
	}
	
	@RequestMapping(value="showflowlayer",method = RequestMethod.POST)
	public String showflowlayer(Long searchFlow,@RequestParam Map<String, Object> params,
			HttpServletResponse response,HttpServletRequest request,Model model){
		model.addAttribute("searchFlow", searchFlow);
		return "verify/cusGuarFlow/flow";
	}
	
	@RequestMapping(value="showflow",method = RequestMethod.POST)
	public void showCapitalFlow(@RequestParam Map<String, Object> params,
			HttpServletResponse response,HttpServletRequest request){
		String id = (String) params.get("searchFlow");
		Map<String,Object> sm = new HashMap<String,Object>();
		Map<String,Object> rm = new HashMap<String,Object>();
		sm.put("search_flow", id);
		List<CusGuarFlow> list = cusGuarFlowService.listCusGuarFlow(sm);
		String mainname = "";
		Map m = null;
		
		for(CusGuarFlow cf : list){
			//如果是首节点
			m = (Map<String,Object>)cf;
			if( Integer.parseInt(m.get("searchStep").toString())==1){
				mainname = m.get("guarName").toString().trim();
				rm.put("name", mainname);//客户名称
				break;
			}
		}
		rm.put("linklist", list);
		
		//装载nodes,先要去除重复节点,以acctno,name,cusid判断
		List nodelist = cusGuarFlowService.getSearchFlowNodes(sm);
		rm.put("nodelist", nodelist);
		
		ResponseUtils.renderJson(response, rm);
	}
	
	
	@RequestMapping(value="showdata",method = RequestMethod.POST)
	public String showdata(@RequestParam Map<String, Object> params,Model model){
		String id = (String) params.get("id");
		if(null!=id){
			String[] strarr = id.split(",");
			model.addAttribute("guar_no", strarr[0]);
			model.addAttribute("search_flow", strarr[1]);
		}
		return "verify/cusGuarFlow/detail";
	}
	
	@RequestMapping(value="showdatalist",method = RequestMethod.POST)
	public void showdatalist(@RequestParam Map<String, Object> params,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			for (String key : params.keySet())
			{
				String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"),"UTF-8");
				paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
				map.put(key, paramsTrans);
			}
			if (params.containsKey("sortC"))
			{
				map.put("sortC", StringConvert.camelhumpToUnderline(params.get(
						"sortC").toString()));
			}
			PageInfo<CusGuarFlow> page = cusGuarFlowService.getDataByGuarNo(map);
			ResponseUtils.renderJson(response, page);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
