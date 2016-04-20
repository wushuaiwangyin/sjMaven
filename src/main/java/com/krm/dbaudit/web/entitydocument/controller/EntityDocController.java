package com.krm.dbaudit.web.entitydocument.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.JSONObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializableSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.krm.dbaudit.web.entitydocument.model.CellInfo;
import com.krm.dbaudit.web.entitydocument.model.JyzbInfo;
import com.krm.dbaudit.web.entitydocument.model.LinkManInfo;
import com.krm.dbaudit.web.entitydocument.model.OtherThings;
import com.krm.dbaudit.web.entitydocument.model.RowInfo;
import com.krm.dbaudit.web.entitydocument.model.SjJcRecds;
import com.krm.dbaudit.web.entitydocument.model.SjQe;
import com.krm.dbaudit.web.entitydocument.model.UserOrgBaseInfo;
import com.krm.dbaudit.web.entitydocument.model.YearVsId;
import com.krm.dbaudit.web.entitydocument.service.EntityDocService;
import com.krm.dbaudit.web.sys.model.SysOffice;
import com.krm.dbaudit.web.sys.model.SysUser;
import com.krm.dbaudit.web.sys.service.SysOfficeService;
import com.krm.dbaudit.web.util.SysUserUtils;
@Controller
@RequestMapping("entitydoc/main")
public class EntityDocController {
	@Resource
	private SysOfficeService sysOfficeService;
	@Resource
	private EntityDocService entityDocService;
	@RequestMapping(value="index")	
	public String enterIndex() {
		return "entitydoc/test2";
	}

	@RequestMapping(value="showdata")	
	public String showTable(@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) {
	//获取默认当前登录用户的机构信息
	SysUser sysUser = SysUserUtils.getCacheLoginUser();
	SysOffice currentOrgan = sysOfficeService.selectByPrimaryKey(sysUser.getOfficeId());
	UserOrgBaseInfo uo=new UserOrgBaseInfo();
//	model.addAttribute("address", "金融街")	;
//	model.addAttribute("openDate", "20160217");
//	model.addAttribute("telno", "13782345054");
	uo.setAddress("金融街");
	uo.setBusOrgan("金融街支行");
	uo.setBzr("张三");
	uo.setMoveTelNo("13782345054");
	uo.setOpenDate("20160217");
	uo.setOrganName("金融街支行");
	uo.setPostCode("010000");
	uo.setRepDate("20160217");
	uo.setSpr("李四");
	model.addAttribute("user", uo);
	//sysUser.getCompanyId()
	return "entitydoc/test";
	}
	@RequestMapping(value="getDatas",method=RequestMethod.GET)
	public void getDatas(@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) throws JSONException{
	List<LinkManInfo> list=new ArrayList<LinkManInfo>();
	LinkManInfo link=new LinkManInfo();
	link.setId(1);
	link.setTitleId("行长");
	link.setName("peter");
	link.setRzq("3年");
	link.setApart("审计1部");
	link.setMoveTellNo("13101011001");
	link.setPerTellNo("010-28465786");
	link.setRemark("暂无");
	list.add(link);
	LinkManInfo link2=new LinkManInfo();
	link2.setId(2);
	link2.setTitleId("副行长");
	link2.setName("steven");
	link2.setRzq("3年");
	link2.setApart("审计1部");
	link2.setMoveTellNo("13101023401");
	link2.setPerTellNo("010-28465786");
	link2.setRemark("暂无");
	list.add(link2);
	JSONObject json=new JSONObject();
	JSONArray jsonArray=(JSONArray)JSONArray.toJSON(list);
	json.put("total", list.size());
	json.put("rows", jsonArray);
	 response.setContentType("application/json");  
		response.setCharacterEncoding("UTF-8");  
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	
	}
	
	/**获取指标经营情况
	 * @param params
	 * @param response
	 * @param model
	 * @throws JSONException
	 */
	@RequestMapping(value="getJyzbDatas",method=RequestMethod.GET)
	public void getJyzbDatas(@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) throws Exception{
	String jsonStr="";
	if(null==params.get("startDate") || null==params.get("endDate")){//初始化本年度12个月份数据
	List<JyzbInfo> list=new ArrayList<JyzbInfo>();
	JyzbInfo jy=new JyzbInfo();
	jy.setId(1);
	jy.setApril("12");
	jy.setAujust("11");
	jy.setDecember("124");
	jy.setFebruary("200");
	jy.setJanuary("99");
	jy.setJuly("20");
	jy.setJune("203");
	jy.setMarch("308");
	jy.setMay("7");
	jy.setNovember("455");
	jy.setOctober("007");
	jy.setProName("存款总额");
	jy.setSeptember("300");
	
	JyzbInfo jy1=new JyzbInfo();
	jy1.setId(2);
	jy1.setApril("12");
	jy1.setAujust("11");
	jy1.setDecember("124");
	jy1.setFebruary("200");
	jy1.setJanuary("99");
	jy1.setJuly("20");
	jy1.setJune("203");
	jy1.setMarch("308");
	jy1.setMay("7");
	jy1.setNovember("455");
	jy1.setOctober("007");
	jy1.setProName("对公存款");
	jy1.setSeptember("300");
	list.add(jy);
	list.add(jy1);
	JSONObject json=new JSONObject();
	JSONArray jsonArray=(JSONArray)JSONArray.toJSON(list);
	json.put("rows", jsonArray);
	jsonStr=json.toString();
	}else{//根据起始日期和结束日期查询记录，（13个月记录）
		 jsonStr=entityDocService.getZbjyList(params);
	}
	 response.setContentType("application/json");  
		response.setCharacterEncoding("UTF-8");  
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	
	}	

	/**初始化历年审计检查记录
	 * @param params
	 * @param response
	 * @param model
	 * @throws JSONException
	 */
	@RequestMapping(value="getJcDatas",method=RequestMethod.GET)
	public void getJcDatas(@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) throws JSONException{
	List<SjJcRecds> sjList=new ArrayList<SjJcRecds>();
	SjJcRecds sj1=new SjJcRecds();
	sj1.setId(1);
	sj1.setSjDate("20160217");
	sj1.setSjType("初审");
	sj1.setXmbh("01");
	sj1.setXmmc("大华银行");
	sj1.setZsr("许意浓");
	sj1.setRemark("hha");
	SjJcRecds sj2=new SjJcRecds();
	sj2.setId(2);
	sj2.setSjDate("20160217");
	sj2.setSjType("处审");
	sj2.setXmbh("001");
	sj2.setXmmc("东风");
	sj2.setZsr("秦风");
	sj2.setRemark("hha");
	
	sjList.add(sj1);
	sjList.add(sj2);
	
	JSONObject json=new JSONObject();
	JSONArray jsonArray=(JSONArray)JSONArray.toJSON(sjList);
	json.put("total", sjList.size());
	json.put("rows", jsonArray);
	 response.setContentType("application/json");  
		response.setCharacterEncoding("UTF-8");  
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	/**历年审计过程中发现的问题
	 * @param params
	 * @param response
	 * @param model
	 * @throws JSONException
	 */
	@RequestMapping(value="getQeDatas",method=RequestMethod.GET)
	public void getQeDatas(@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) throws JSONException{
	List<SjQe> sjList=new ArrayList<SjQe>();
	SjQe qe=new SjQe();
	qe.setId(1);
	qe.setFlxq("次级较严重");
	qe.setFxfl("次级");
	qe.setJcDate("20160217");
	qe.setQe("次级贷款");
	qe.setQems("次级贷款");
	qe.setResult("贷款逾期");
	qe.setYwtx("贷款");
	qe.setRemark("暂无");
	
	SjQe qe1=new SjQe();
	qe1.setId(2);
	qe1.setFlxq("可疑");
	qe1.setFxfl("可疑");
	qe1.setJcDate("20150217");
	qe1.setQe("可以贷款");
	qe1.setQems("可以贷款");
	qe1.setResult("造成损失");
	qe1.setYwtx("贷款");
	qe1.setRemark("跟踪中");
	sjList.add(qe1);
	sjList.add(qe);
	
	
	JSONObject json=new JSONObject();
	JSONArray jsonArray=(JSONArray)JSONArray.toJSON(sjList);
	json.put("total", sjList.size());
	json.put("rows", jsonArray);
	 response.setContentType("application/json");  
		response.setCharacterEncoding("UTF-8");  
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	@RequestMapping(value="getOtherDatas",method=RequestMethod.GET)
	public void getOtherDatas(@RequestParam Map<String, Object> params,HttpServletResponse response, Model model) throws JSONException{
	List<OtherThings> olist=new ArrayList<OtherThings>();
	OtherThings o=new OtherThings();
	o.setId(1);
	o.setRecd1("ceshi1");
	o.setRecd2("ceshi2");
	OtherThings o1=new OtherThings();
	o1.setId(2);
	o1.setRecd1("ceshi3");
	o1.setRecd2("cesh4");
	olist.add(o);
	olist.add(o1);
	JSONObject json=new JSONObject();
	JSONArray jsonArray=(JSONArray)JSONArray.toJSON(olist);
	json.put("total", olist.size());
	json.put("rows", jsonArray);
	 response.setContentType("application/json");  
		response.setCharacterEncoding("UTF-8");  
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	/**使用json反序列化
	 * @param params
	 * @param response
	 * @param model
	 */
	@RequestMapping(value="saveLinkManDatas",method=RequestMethod.POST)
	public void saveLinkManDatas(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		String json = params.get("enQueryProperties").toString();
		JSONArray jArray = JSONArray.parseArray(json);
		LinkManInfo other = null;
		List<LinkManInfo> oList = new ArrayList<LinkManInfo>();
		// 以下代码可抽出作为业务逻辑实现层的独立方法
		// 反序列化获取java对象，并验证每个id是否存在，不存在则是增加，存在则是更新
		List<LinkManInfo> saveList = new ArrayList<LinkManInfo>();// 新增对象
		List<LinkManInfo> updateList = new ArrayList<LinkManInfo>();// 更新对象
		// 数据库中所有记录的不重复id set集合
		Set ids = new HashSet();
		ids=entityDocService.getAllLinkIds();
		// 初始化olist
		for (int i = 0; i < jArray.size(); i++) {
			other = JSONObject.toJavaObject(jArray.getJSONObject(i),
					LinkManInfo.class);
			oList.add(other);
			// ids.add(other.getId());
		}
		// 在内存中比对set集合与本次请求的集合，存在相同id的则更新当前id对应的数据库记录，不存在则增加心得记录
		String mess = entityDocService.saveOrUpdateLinks(ids, oList);

	}
	/**使用json反序列化
	 * @param params
	 * @param response
	 * @param model
	 */
	@RequestMapping(value="saveOtherDatas",method=RequestMethod.POST)
	public void saveOtherDatas(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		String json = params.get("enQueryProperties").toString();
		JSONArray jArray = JSONArray.parseArray(json);
		OtherThings other = null;
		List<OtherThings> oList = new ArrayList<OtherThings>();
		// 以下代码可抽出作为业务逻辑实现层的独立方法
		// 反序列化获取java对象，并验证每个id是否存在，不存在则是增加，存在则是更新
		List<OtherThings> saveList = new ArrayList<OtherThings>();// 新增对象
		List<OtherThings> updateList = new ArrayList<OtherThings>();// 更新对象
		// 数据库中所有记录的不重复id set集合
		Set ids = new HashSet();
		ids = entityDocService.getAllOtherIds();
		// 初始化olist
		for (int i = 0; i < jArray.size(); i++) {
			other = JSONObject.toJavaObject(jArray.getJSONObject(i),
					OtherThings.class);
			oList.add(other);
		}
		// 在内存中比对set集合与本次请求的集合，存在相同id的则更新当前id对应的数据库记录，不存在则增加记录

		String mess = entityDocService.saveOrUpdateOthers(ids, oList);

	}
}
