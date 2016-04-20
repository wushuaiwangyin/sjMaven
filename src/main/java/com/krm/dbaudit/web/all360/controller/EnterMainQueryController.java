package com.krm.dbaudit.web.all360.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.web.all360.model.CommonCustInfo;
import com.krm.dbaudit.web.all360.model.HistoryCustRed;
import com.krm.dbaudit.web.all360.model.TableModelColumns;
import com.krm.dbaudit.web.all360.service.QueryModelService;
import com.krm.dbaudit.web.all360.util.EchartSeries;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author cat 查询客户信息，及其关联属性
 */
@Controller
@RequestMapping("cust360/main")
public class EnterMainQueryController extends BaseController {
	@Resource
	private QueryModelService queryModelService;

	/**
	 * 对公对私客户公共信息
	 * 
	 * @param params
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "commonCust", method = RequestMethod.GET)
	public void commonCust(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		//params.put("model_id", 8);
		// 对公对私客户公共基本信息查询模型
		if("1".equals(params.get("isPublic").toString())){
			params.put("model_id", 15);//对私
		}
		if("2".equals(params.get("isPublic").toString())){
			params.put("model_id", 8);//对公
		}
		List<Map<String, Object>> custModellist = queryModelService
				.getModels(params);

//		List<Map<String, Object>> custlist = queryModelService.getCustList(
//				params, custModellist);
//
//		String key = "";
//		StringBuffer sb = new StringBuffer();
//		if (custlist.size() > 0) {
//			for (Map<String, Object> map : custlist) {
//				for (Iterator it = map.keySet().iterator(); it.hasNext();) {
//					key = it.next().toString();
//					sb.append("<dt>" + key + ":</dt>");
//					sb.append("<dd>" + map.get(key).toString().trim() + "</dd>");
//				}
//
//			}
//		}
		JSONObject jsonObject=new JSONObject();
		CommonCustInfo custInfo=queryModelService.getCommonCustInfos(params, custModellist);
		 response.setContentType("application/json");  
		response.setCharacterEncoding("UTF-8");  
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(jsonObject.toJSONString(custInfo));
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	/**
	 * 结果主页信息查询
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.GET)
	public String mainQuery(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {

	//	params.put("model_id", 2);// 客户基本信息查询模型
		if("1".equals(params.get("isPublic").toString())){//对私
			params.put("model_id", 11);
		}
		if("2".equals(params.get("isPublic").toString())){//对公
			params.put("model_id", 2);
		}
		List<Map<String, Object>> custModellist = queryModelService
				.getModels(params);
		List<Map<String, Object>> custlist = queryModelService.getCustList(
				params, custModellist);
		model.addAttribute("cust", custlist);
		model.addAttribute("isPublic", params.get("isPublic"));
		model.addAttribute("custId", params.get("custId"));
		if(custlist.size()>0){
			String custName="";
			String key ="";
				for (Map<String, Object> map:custlist) {
				for (Iterator it=map.keySet().iterator();it.hasNext();) {
					key=it.next().toString();
					if	("客户名称".equals(key.toUpperCase())){
						custName=map.get(key).toString();
					}
				}	
				}
	//	model.addAttribute("isPublic", params.get("isPublic"));
	//	model.addAttribute("custId", params.get("custId"));
		// 将此次查询记录保存到客户查询历史记录表里。
		HistoryCustRed hs = new HistoryCustRed();
		hs.setIsPublic(params.get("isPublic").toString());
		hs.setCustId(params.get("custId").toString().trim());
		 hs.setCustName(custName);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		hs.setHsDate(sf.format(new Date()));
		queryModelService.saveHisCustReds(hs);
		}
		return "verify/360/result-index";
	}

	/**
	 * 客户其他联系信息查询
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/bigCust", method = RequestMethod.POST)
	public void loanInfo(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		//params.put("model_id", 7);
		// 客户其他联系信息查询
		if("1".equals(params.get("isPublic").toString())){//对私
			params.put("model_id", 16);
		}
		if("2".equals(params.get("isPublic").toString())){//对公
			params.put("model_id", 7);
		}
		List<Map<String, Object>> custRelModelList = queryModelService
				.getModels(params);
		String json = queryModelService.custList2(params, custRelModelList);
		response.setContentType("html/text;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	/**
	 * 客户基本信息
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bigCust2", method = RequestMethod.GET)
	public String bigCust(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		if("1".equals(params.get("isPublic").toString())){
			params.put("model_id", 11);
		}
		if("2".equals(params.get("isPublic").toString())){
			params.put("model_id", 2);
		}
		//params.put("model_id", 2);// 客户基本信息查询模型
		List<Map<String, Object>> custModellist = queryModelService
				.getModels(params);
		List<Map<String, Object>> custlist = queryModelService.getCustList(
				params, custModellist);
		model.addAttribute("cust", custlist);
		model.addAttribute("isPublic", params.get("isPublic"));
		model.addAttribute("custId", params.get("custId"));
		// ResponseUtils.renderJson(response, loanAccList);
		return "verify/360/result-custinfo";
	}

	/**
	 * 存款总额
	 * 
	 * @param params
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/dspSumData", method = RequestMethod.POST)
	public void getDspSumData(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		params.put("sum", "dps");
		Map<String, Object> map = queryModelService.getDspSumData(params);
		ResponseUtils.renderJson(response, map);
	}

	/**
	 * 存/贷款总额
	 * 
	 * @param params
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/loanSumData", method = RequestMethod.POST)
	public void getloanSumData(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		params.put("sum", "loan");
		Map<String, Object> map = queryModelService.getDspSumData(params);
		ResponseUtils.renderJson(response, map);
	}

	/**
	 * 进入存款账户信息页面
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dspAccnoInfo", method = RequestMethod.GET)
	public String enterDspAccunt(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		model.addAttribute("custId", params.get("custId"));
		model.addAttribute("isPublic", params.get("isPublic"));
		return "verify/360/result-dsp";
	}

	/**
	 * 异步获取存款账户信息
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/getDspAccnoInfo", method = RequestMethod.POST)
	public void getDspAccunt(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		//params.put("model_id", 4);
		// 存款账户基本信息查询模型
		if("1".equals(params.get("isPublic").toString())){
			params.put("model_id", 13);//private
		}
		if("2".equals(params.get("isPublic").toString())){
			params.put("model_id", 4);//public
		}
		
		List<Map<String, Object>> loanAccModellist = queryModelService
				.getModels(params);
		String json = queryModelService
				.getLoanAccList(params, loanAccModellist);
		response.setContentType("html/text;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	/**
	 * 进入贷款信息
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loanAccnoInfo", method = RequestMethod.GET)
	public String enterLoanAccunt(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		params.put("model_id", 3);// 贷款账户基本信息查询模型
//		List<Map<String, Object>> loanAccModellist = queryModelService
//				.getModels(params);
		model.addAttribute("custId", params.get("custId"));
		model.addAttribute("isPublic", params.get("isPublic"));
		return "verify/360/result-loan";
	}

	/**
	 * 异步获取贷款账户信息
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/getLoanAccnoInfo", method = RequestMethod.POST)
	public void getLoanAccunt(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
	//	params.put("model_id", 3);
		// 贷款账户基本信息查询模型
		if("1".equals(params.get("isPublic").toString())){
			params.put("model_id", 12);//private
		}
		if("2".equals(params.get("isPublic").toString())){
			params.put("model_id", 3);//public
		}
		List<Map<String, Object>> loanAccModellist = queryModelService
				.getModels(params);
		String json = queryModelService
				.getLoanAccList(params, loanAccModellist);
		response.setContentType("html/text;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	/**
	 * 异步获取存款余额
	 * 
	 * @param params
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/ajaxGetDpsAccounts", method = RequestMethod.POST)
	public void ajaxGetDpsAccounts(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		List<EchartSeries> list = queryModelService.getDpsAccountList(params);
		ResponseUtils.renderJson(response, list);
	}

	/**
	 * 异步获取客户查询历史记录
	 * 
	 * @param params
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/getHistoryRecds", method = RequestMethod.POST)
	public void ajaxGetHisCustrecds(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		List<Map<String, Object>> list = queryModelService
				.getHistoryCustReds(params);
		ResponseUtils.renderJson(response, list);
	}

	/**
	 * 关联信息查询
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/custrel", method = RequestMethod.GET)
	public String enterCustRels(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		// List<CustRelCustModel> list = queryModelService.getCustRels(params);
		model.addAttribute("custId", params.get("custId"));
		model.addAttribute("isPublic", params.get("isPublic"));
		// model.addAttribute("list", list);
		return "verify/360/result-custRel";
	}

	@RequestMapping(value = "/getCustrel", method = RequestMethod.POST)
	public void getcustRels(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model)  {
	//	params.put("model_id", 9);
		// 关联关系信息查询模型
		if("1".equals(params.get("isPublic").toString())){
			params.put("model_id", 17);//private
		}
		if("2".equals(params.get("isPublic").toString())){
			params.put("model_id", 9);//public
		}
		List<Map<String, Object>> relModelList = queryModelService
				.getModels(params);
		String json = queryModelService.getCustRels(params, relModelList);
		response.setContentType("html/text;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	/**
	 * @param params
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/getcustrels", method = RequestMethod.POST)
	public void ajaxGetCustRels(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		// PageInfo<CustRelCustModel>
		// page=queryModelService.getCustRels(params);
		// ResponseUtils.renderJson(response,page);
	}

	/**
	 * 担保信息
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/custSure", method = RequestMethod.GET)
	public String enterCustSure(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		model.addAttribute("custId", params.get("custId"));
		model.addAttribute("isPublic", params.get("isPublic"));
		return "verify/360/result-resure";
	}

	@RequestMapping(value = "/getCustSure", method = RequestMethod.POST)
	public void getCustSure(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model)  {
		//params.put("model_id", 5);
		// 担保信息查询模型
		if("1".equals(params.get("isPublic").toString())){
			params.put("model_id", 14);//private
		}
		if("2".equals(params.get("isPublic").toString())){
			params.put("model_id", 5);//public
		}
		List<Map<String, Object>> sureList = queryModelService
				.getModels(params);
		String json = queryModelService.getCustSureList(params, sureList);
		response.setContentType("html/text;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	/**
	 * 联系人信息查询
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/custRelPersonInfo", method = RequestMethod.GET)
	public String getCustRelPersonInfo(
			@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		params.put("model_id", 2);// 客户基本信息查询模型
		if("1".equals(params.get("isPublic").toString())){
			params.put("model_id", 11);//private
		}
		if("2".equals(params.get("isPublic").toString())){
			params.put("model_id", 2);//public
		}
		List<Map<String, Object>> custModellist = queryModelService
				.getModels(params);
		List<Map<String, Object>> custlist = queryModelService.getCustList(
				params, custModellist);
		model.addAttribute("cust", custlist);

		params.put("model_id", 6);// 客户联系人基本信息查询模型
		
		if("1".equals(params.get("isPublic").toString())){
			params.put("model_id", 18);//private
		}
		if("2".equals(params.get("isPublic").toString())){
			params.put("model_id", 6);//public
		}
		List<Map<String, Object>> custRelModelList = queryModelService
				.getModels(params);
		List<Map<String, Object>> custRellist = queryModelService.getCustList(
				params, custRelModelList);
		model.addAttribute("cust", custlist);
		model.addAttribute("relCust", custRellist);
		model.addAttribute("custId", params.get("custId"));
		model.addAttribute("isPublic", params.get("isPublic"));

		return "verify/360/result-relCustInfo";
	}

	/**
	 * 客户其他联系信息查询
	 * 
	 * @param params
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/otherCustRelInfo", method = RequestMethod.GET)
	public String getOtherCustRelInfo(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		
		// 客户基本信息查询模型
		if("1".equals(params.get("isPublic").toString())){
			params.put("model_id", 11);//对私
		}
		if("2".equals(params.get("isPublic").toString())){
			params.put("model_id", 2);//对公
		}
		List<Map<String, Object>> custModellist = queryModelService
				.getModels(params);
		List<Map<String, Object>> custlist = queryModelService.getCustList(
				params, custModellist);
		model.addAttribute("cust", custlist);

		//params.put("model_id", 7);
		// 客户其他联系信息查询
		if("1".equals(params.get("isPublic").toString())){
			params.put("model_id", 16);//对私
		}
		if("2".equals(params.get("isPublic").toString())){
			params.put("model_id", 7);//对公
		}
		List<Map<String, Object>> custRelModelList = queryModelService
				.getModels(params);
		List<Map<String, Object>> custRellist = queryModelService.getCustList(
				params, custRelModelList);
		model.addAttribute("cust", custlist);
		model.addAttribute("relCust", custRellist);
		model.addAttribute("custId", params.get("custId"));
		model.addAttribute("isPublic", params.get("isPublic"));

		return "verify/360/result-otherCustRel";
	}

	@RequestMapping(value = "/common", method = RequestMethod.POST)
	public void queryCommonCust(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model)  {
		params.put("model_id", 7);// 客户其他联系信息查询
		List<Map<String, Object>> custRelModelList = queryModelService
				.getModels(params);
		String json = queryModelService.custList2(params, custRelModelList);
		response.setContentType("html/text;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="detail/showlayer",method=RequestMethod.POST)
	public String layer(@RequestParam Map<String, Object> params, Model model){
	
		model.addAttribute("modelId", params.get("modelId"));
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
	
	@RequestMapping(value="queryModels", method=RequestMethod.POST)
	public void queryModels(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		List<Map<String, Object>> list=queryModelService.getQueryModelsList(params);
		JSONObject json=new JSONObject();
		JSONArray jArray = (JSONArray) JSONArray.toJSON(list);
		json.put("total", list.size());
		json.put("list",jArray);
		ResponseUtils.renderJson(response,json);
		
	}
		@RequestMapping(value = "enterManageIndex", method = RequestMethod.GET)
		public String eterMainQuery(@RequestParam Map<String, Object> params,
				HttpServletResponse response, Model model) {
			return "verify/360/modelManage/modelManageIndex";
		}
		
		/**贷款账户变动信息
		 * @param params
		 * @param response
		 * @param model
		 */
		@RequestMapping(value="ajaxGetLoanAccountPersont", method=RequestMethod.POST)
		public void ajaxGetLoanAccountPersont(@RequestParam Map<String, Object> params,
				HttpServletResponse response, Model model) {	
		//	params.put("model_id", 10);
			// 贷款账户变动信息
			if("1".equals(params.get("isPublic").toString())){
				params.put("model_id", 19);//private
			}
			if("2".equals(params.get("isPublic").toString())){
				params.put("model_id", 10);//public
			}
			List<Map<String, Object>> sureList = queryModelService
					.getModels(params);
			String json = queryModelService.getCustSureList(params, sureList);
			response.setContentType("html/text;charset=utf-8");
			PrintWriter out=null;
			try {
				out = response.getWriter();
				out.write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				out.close();
			}
			
		}
		
		/**获取top3贷款账户余额信息
		 * @param params
		 * @param response
		 * @param model
		 */
		@RequestMapping(value="ajaxGetLoanData", method=RequestMethod.POST)
		public void ajaxGetLoanData(@RequestParam Map<String, Object> params,
				HttpServletResponse response, Model model) {	
			String json=queryModelService.getLoanData(params);
			// response.setContentType("application/json");  
			response.setCharacterEncoding("UTF-8");  
			PrintWriter out=null;
			try {
				out = response.getWriter();
				out.write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				out.close();
			}
		}
		
		
		/**
		 * 弹窗显示
		* @param 
		* @return
		 */
		@RequestMapping(value="detail/enterDspDetail",method=RequestMethod.POST)
		public String enterDspPage(@RequestParam Map<String, Object> params, Model model){
		
			model.addAttribute("acctno", params.get("acctno").toString().trim());
			model.addAttribute("isPublic", params.get("isPublic"));
			return "verify/360/dsp_detail";
		}
		
	
		/**
		 * 获取存款账户详细信息
		 * 
		 * @param params
		 * @param response
		 * @param model
		 * @throws IOException
		 */
		@RequestMapping(value = "detail/getDspDetail", method = RequestMethod.POST)
		public void getDspDetail(@RequestParam Map<String, Object> params,
				HttpServletResponse response, Model model) {
			//params.put("model_id", 4);
			// 存款账户详细信息查询模型
			if("1".equals(params.get("isPublic").toString())){
				params.put("model_id", 21);//private
			}
			if("2".equals(params.get("isPublic").toString())){
				params.put("model_id", 20);//public
			}
			
			List<Map<String, Object>> loanAccModellist = queryModelService
					.getModels(params);
			String json = queryModelService
					.getLoanAccList(params, loanAccModellist);
			response.setContentType("html/text;charset=utf-8");
			PrintWriter out=null;
			try {
				out = response.getWriter();
				out.write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				out.close();
			}
		}

		
	public static void main(String[] args) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	System.out.println(sf.format(new Date()));
}
}