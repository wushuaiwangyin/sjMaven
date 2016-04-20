package com.krm.dbaudit.web.verify.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
/**
* @author tanwen on 2015-11-13
* @desc 资金流向控制类
*/
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.sys.model.SysUser;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;
import com.krm.dbaudit.web.verify.model.CapitalFlow;
import com.krm.dbaudit.web.verify.model.CapitalFlowDps;
import com.krm.dbaudit.web.verify.model.OutData;
import com.krm.dbaudit.web.verify.service.CapitalFlowService;

import oracle.jdbc.OracleTypes;

@Controller
@RequestMapping("capitalflow")
public class CapitalFlowController extends BaseController {
	@Resource
	private CapitalFlowService capitalFlowService;
	
	@RequestMapping(value="searchflow",method = RequestMethod.POST)
	public void getCapitalFlow(@RequestParam Map<String, Object> params,
			HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> maps = new HashMap<String,Object>();
		String amt = params.get("amt")==null?null:(String) params.get("amt");
		Integer queryCount = params.get("queryCount")==null?null:Integer.parseInt(params.get("queryCount").toString());
		String acctno = params.get("acctno")==null?null:(String)params.get("acctno");
		if(null!=acctno && !"".equals(acctno)){
			maps.put("IN_ACCTNO", acctno);
			maps.put("IN_STEP", 0);
			maps.put("IN_SEARCH_ID", null);
			maps.put("IN_TRAN_DATE", null);
			capitalFlowService.callProSerachFlow(maps);

			Integer searchId = (Integer) maps.get("OUT_STEP");
			
			
			//根据查询编号获取所有的交易记录
			if(null!=searchId && searchId != 0){
				Map<String,Object> sm = new HashMap<String,Object>();
				sm.put("search_flow", searchId);
				
				//查询当前查询最大关联次数
				Integer maxStep = capitalFlowService.getSearchFlowMaxStep(maps);
				
				//最大关联次数大于查询条件中是关联次数才继续
				if(null != maxStep && null != params.get("queryCount") && 
						maxStep >= Integer.parseInt((String)params.get("queryCount"))){
					
					//按search_flow分组并返回列表
					Map<String,Object> params2 = new HashMap<String,Object>();
					params2.put("search_flow", searchId);
					List<CapitalFlowDps> grouplist = capitalFlowService.getDpsSearchFlowGroup2(params2);
					String josnstr = JSON.toJSONString(grouplist);
					System.out.println(josnstr);
					ResponseUtils.renderJson(response, josnstr);
					
				}else{
					//不满足条件
					ResponseUtils.renderText(response, -1);
				}

				
			}else{
				//不满足条件
				ResponseUtils.renderText(response, -1);
			}
		}else{
			//不输入账号
			SysUser user = SysUserUtils.getSessionLoginUser();
			maps.put("IN_USER", user.getUsername());
			maps.put("IN_AMT",amt);
			maps.put("IN_TRAN_DATE", null);
			capitalFlowService.callProSerachFlowNotAcctno(maps);
			
			Integer rownums = (Integer) maps.get("OUT_ROWNUM");
			
			if(rownums>queryCount){
				//取出明细
				Map<String,Object> params1 = new HashMap<String,Object>();
				params1.put("search_user", user.getUsername());
				//对经过金额过滤的中间表进行循环处理SYSMONEY_FLOW_TEMP_2
				List<CapitalFlowDps> list = capitalFlowService.getDpsList(params1);
				for(CapitalFlowDps dps : list ){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("IN_ACCTNO", dps.getAcctno());
					map.put("IN_STEP", 0);
					map.put("IN_SEARCH_ID", null);
					map.put("IN_TRAN_DATE", null);
					map.put("IN_SEARCH_USER", user.getUsername());
					capitalFlowService.callProSerachFlowDps(map);
				}
				
				//按search_flow分组并返回列表
				Map<String,Object> params2 = new HashMap<String,Object>();
				params2.put("search_step", 3);
				params2.put("search_user", user.getUsername());
				List<CapitalFlowDps> grouplist = capitalFlowService.getDpsSearchFlowGroup(params2);
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
		return "verify/capitalFlow/flow";
	}
	
	
	@RequestMapping(value="showflow",method = RequestMethod.POST)
	public void showCapitalFlow(@RequestParam Map<String, Object> params,
			HttpServletResponse response,HttpServletRequest request){
		String id = (String) params.get("searchFlow");
		Map<String,Object> sm = new HashMap<String,Object>();
		Map<String,Object> rm = new HashMap<String,Object>();
		sm.put("search_flow", id);
		List<CapitalFlow> list = capitalFlowService.listCapitalFlow(sm);
		String mainname = "";
		Map m = null;
		
		for(CapitalFlow cf : list){
			//如果是首节点
			m = (Map<String,Object>)cf;
			if( Integer.parseInt(m.get("searchStep").toString())==1){
				mainname = m.get("name").toString().trim();
				rm.put("name", mainname);//客户名称
				break;
			}
		}
		rm.put("linklist", list);
		
		//装载nodes,先要去除重复节点,以acctno,name,cusid判断
		List nodelist = capitalFlowService.getSearchFlowNodes(sm);
		rm.put("nodelist", nodelist);
		
		ResponseUtils.renderJson(response, rm);
	}
	
	@RequestMapping(value="showdata",method = RequestMethod.POST)
	public String showdata(@RequestParam Map<String, Object> params,Model model){
		String id = (String) params.get("id");
		if(null!=id){
			String[] strarr = id.split(",");
			model.addAttribute("acctno", strarr[0]);
			model.addAttribute("search_flow", strarr[1]);
		}
		return "verify/capitalFlow/detail";
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
			PageInfo<CapitalFlow> page = capitalFlowService.getDataByAcctno(map);
			ResponseUtils.renderJson(response, page);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
