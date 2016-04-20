package com.krm.dbaudit.web.risk.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.BLOB;

import org.apache.commons.beanutils.BeanUtils;
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

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.krm.dbaudit.common.excel.ExcelUtils;
import com.krm.dbaudit.common.excel.template.utils.PoiUtil;
import com.krm.dbaudit.common.utils.DateUtils;
import com.krm.dbaudit.common.utils.FileUtils;
import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.service.ModelBaseService;
import com.krm.dbaudit.web.risk.model.DataDealModel;
import com.krm.dbaudit.web.risk.model.DataNoticeModel;
import com.krm.dbaudit.web.risk.model.ModelData;
import com.krm.dbaudit.web.risk.model.ModelDataFile;
import com.krm.dbaudit.web.risk.service.DataDealService;
import com.krm.dbaudit.web.risk.service.DataNoticeService;
import com.krm.dbaudit.web.risk.service.ModelDataFileService;
import com.krm.dbaudit.web.risk.service.RiskDetectionService;
import com.krm.dbaudit.web.sys.model.SysOffice;
import com.krm.dbaudit.web.sys.model.SysUser;
import com.krm.dbaudit.web.sys.service.SysOfficeService;
import com.krm.dbaudit.web.util.DateUtil;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.util.SysUserUtils;

/**
 * 模型数据处理
 * @author Parker
 *
 */
@Controller
@RequestMapping("data")
public class DataController
{
	@Resource
	private DataNoticeService dataNoticeService;
	
	@Resource
	private DataDealService dataDealService;
	
	@Resource
	private RiskDetectionService riskDetectionService;
	
	@Resource
	private ModelBaseService modelBaseService;
	
	@Resource
	private SysOfficeService sysOfficeService;
	
	@Resource
	private ModelDataFileService modelDataFileService;
	/**
	 * 从数据源下发通知，获取数据id并跳转
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value="deal", method=RequestMethod.POST)
	public String deal(@RequestParam Map<String, Object> params,  Model model){
		String dataIdWs = (String) params.get("dataId");
		String modelIdWs = (String) params.get("modelId");
		String noticeTime = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
		int index=0;
		
		StringTokenizer stringTokenizerData = new StringTokenizer(dataIdWs,",");
		StringTokenizer stringTokenizerModel = new StringTokenizer(modelIdWs,",");
		
		String modelId=stringTokenizerModel.nextToken();
		ModelBase  modelBase = modelBaseService.findById(Long.parseLong(modelId));
		List<Long> organList=new ArrayList<Long>();
		List<SysOffice> borganl = new ArrayList<SysOffice>();
		while(stringTokenizerData.hasMoreTokens()){
		
			
			ModelData md =  riskDetectionService.findModelDataByPkid(Integer.parseInt(stringTokenizerData.nextToken()));
			//获取交易机构
			SysUser user = SysUserUtils.getSessionLoginUser();
			SysOffice organ = sysOfficeService.selectByPrimaryKey(Long.parseLong(md.getOrganId()));		//交易机构
			organList.add(organ.getId());
			if(index==0){
				SysOffice currentOrgan = sysOfficeService.selectByPrimaryKey(user.getOfficeId());			//当前机构
				SysOffice superOrgan = sysOfficeService.selectByPrimaryKey(organ.getParentId());			//支行
				SysOffice superOrgan2 = sysOfficeService.selectByPrimaryKey(superOrgan.getParentId());		//合行
				SysOffice superOrgan3= null;
				if(superOrgan2!=null){
						superOrgan3 = sysOfficeService.selectByPrimaryKey(superOrgan2.getParentId());
				}
				//SysOffice superOrgan3 = sysOfficeService.selectByPrimaryKey(superOrgan2.getParentId());		//办事处
														//被通知机构
				if("33001".equals(currentOrgan.getOrgLevel())){
					borganl.add(superOrgan3);
					borganl.add(superOrgan2);
				}else{
					borganl.add(superOrgan2);
					borganl.add(superOrgan);
				}
			}
			index++;
		}
		model.addAttribute("borganl",borganl)
		.addAttribute("modelBase",modelBase)
		.addAttribute("dataId", dataIdWs )
		.addAttribute("modelId", modelId )
		.addAttribute("noticeTime", noticeTime)
		.addAttribute("custNo",params.containsKey("custNo")?params.get("custNo").toString():null)
		.addAttribute("flag", true);	//用来区分是否从数据源下发还是通知书中下发，true为数据源下发
		Set<Object> organSet = new HashSet<Object>();
		if(organList.size()==1){
			model.addAttribute("ifOrganSame", "1");
			
		}else{
			for(Long organLong : organList){
				if(organSet.add(organLong)){
					model.addAttribute("ifOrganSame", "0");
				}else{
					model.addAttribute("ifOrganSame", "1");
				}
			}
		}
		return "risk/deal";
	}
	
	/**
	 * 从通知书处再下发通知，获取数据id并跳转
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value="prenotice", method=RequestMethod.POST)
	public String prenotice(@RequestParam Map<String, Object> params,  Model model){
		String id = (String) params.get("id");
		DataNoticeModel  notice = dataNoticeService.findByPkId(Integer.parseInt(id));
		ModelData md =  riskDetectionService.findModelDataByPkid(notice.getDataId());
		ModelBase  modelBase = modelBaseService.findById(Long.parseLong(md.getModelId().toString()));
		//获取交易机构
		SysUser user = SysUserUtils.getSessionLoginUser();
		SysOffice organ = sysOfficeService.selectByPrimaryKey(Long.parseLong(md.getOrganId()));
		SysOffice currentOrgan = sysOfficeService.selectByPrimaryKey(user.getOfficeId());
		SysOffice superOrgan = sysOfficeService.selectByPrimaryKey(organ.getParentId());
		SysOffice superOrgan2 = sysOfficeService.selectByPrimaryKey(superOrgan.getParentId());
		SysOffice superOrgan3 = sysOfficeService.selectByPrimaryKey(superOrgan2.getParentId());
		List<SysOffice> borganl = new ArrayList<SysOffice>();
		if("33001".equals(currentOrgan.getOrgLevel())){
			borganl.add(superOrgan2);
			borganl.add(superOrgan3);
		}else{
			borganl.add(superOrgan2);
		}
		model.addAttribute("ifOrganSame", "-1")
			.addAttribute("borganl",borganl)
			.addAttribute("modelBase",modelBase)
			.addAttribute("dataId", md.getId())
			.addAttribute("modelId", md.getModelId())
			.addAttribute("custNo", notice.getCustNo())
			.addAttribute("noticeTime", DateUtils.getDate("yyyy-MM-dd HH:mm:ss"))
			.addAttribute("flag", false);	//用来区分是否从数据源下发还是通知书中下发，true为数据源下发
		return "risk/deal";
	}
	
	
	
	
	/**
	 * 显示接收的通知书，通过状态来判断是否显示全部  1未处理，2已处理，否则显示全部
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value="noticereceive/show", method=RequestMethod.GET)
	public String shownoticereceive(@RequestParam Map<String, Object> params,  Model model){
		String status = null;
		if(params.containsKey("status")){
			status = (String) params.get("status");
			if(status.equals("1")){
				model.addAttribute("title", "未处理");
			}else if(status.equals("2")){
				model.addAttribute("title", "已处理");
			}
		}else{
			model.addAttribute("title", "全部");
		}
		model.addAttribute("status", status);
		SysUser user = SysUserUtils.getSessionLoginUser();
		SysOffice currentOrgan = sysOfficeService.selectByPrimaryKey(user.getOfficeId());
		model.addAttribute("orgLevel", currentOrgan.getOrgLevel());
		return "risk/notice/index";
	}
	
	/**
	 * 显示接收的通知书，通过状态来判断是否显示全部  1未处理，2已处理，否则显示全部
	 * @param params
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="noticereceive/list", method=RequestMethod.POST)
	public void noticelist(@RequestParam Map<String, Object> params,  Model model,HttpServletResponse response) 
			throws Exception{
		//获取当前用户
		SysUser user = SysUserUtils.getSessionLoginUser();
		String status = (String) params.get("status");
		model.addAttribute("status", status);
		PageInfo<DataNoticeModel> page = null;
		params.put("organId", user.getOfficeId());
		if(params.containsKey("bnoticeOrganId")){
			params.put("bnoticeOrganId", params.get("bnoticeOrganId").toString());
		}
		if(params.containsKey("organName")){
			String organName = new String(((String) params.get("organName")).getBytes("ISO-8859-1"),"UTF-8");
			organName = java.net.URLDecoder.decode(organName, "UTF-8");
			params.put("organName", organName);
		}
		page = dataNoticeService.findNoticePageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	/**
	 * 显示未处理的通知书,rows参数可以设置返回多少行
	 * @param params
	 * @param model
	 * @return 返回已经处理好的<tr></tr>字符串
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="noticereceive/listbytop", method=RequestMethod.POST)
	public void noticelistbytop(@RequestParam Map<String, Object> params,  Model model,HttpServletResponse response) 
			throws Exception{
		//获取当前用户
		SysUser user = SysUserUtils.getSessionLoginUser();
		String status = (String) params.get("status");
		model.addAttribute("status", status);
		params.put("organId", user.getOfficeId());
		params.put("rows", 5);
		if(params.containsKey("bnoticeOrganId")){
			params.put("bnoticeOrganId", params.get("bnoticeOrganId").toString());
		}
		if(params.containsKey("organName")){
			String organName = new String(((String) params.get("organName")).getBytes("ISO-8859-1"),"UTF-8");
			organName = java.net.URLDecoder.decode(organName, "UTF-8");
			params.put("organName", organName);
		}
		List<DataNoticeModel> list = dataNoticeService.findNoticeByNumber(params);
		StringBuffer sb = new StringBuffer();
		
		for(int i=0;i<list.size();i++){
			DataNoticeModel dnc = list.get(i);
			if(i!=0){
				sb.append("<tr>");
			}
			sb.append("<td width=\"10\" height=\"22\" align=center></td>");
			sb.append("<td width=\"150\" align=\"left\"><span class=\"cutstr\">"+dnc.get("modelname")+"</span></td>");
			sb.append("<td width=\"150\"><span class=\"cutstr\">"+dnc.get("bnoticeOrganName")+"</span></td>");
			if(i!=list.size()-1){
				sb.append("</tr>");
			}
		}
		ResponseUtils.renderText(response, sb.toString());
	}
	
	
	/**
	 * 弹窗显示
	* @param params {"mode":detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Integer id,@PathVariable String mode, Model model){
		DataNoticeModel dataNoticeModel = null;
		DataDealModel dataDealModel = null;
		if (StringUtils.equalsIgnoreCase(mode, "detail")){
			dataNoticeModel = dataNoticeService.findByPkId(id);
			Long docId = dataNoticeModel.getDocId();
			if(docId != null){
				ModelDataFile modelDataFile = modelDataFileService.findById(docId);
				model.addAttribute("modelDataFile", modelDataFile);
			}else{
				model.addAttribute("modelDataFile", null);
			}
			model.addAttribute("dataNoticeModel", dataNoticeModel);
		}if (StringUtils.equalsIgnoreCase(mode, "dealDetails")){
			SysUser user = SysUserUtils.getSessionLoginUser();
			SysOffice currentOrgan = sysOfficeService.selectByPrimaryKey(user.getOfficeId());
			dataDealModel = dataDealService.findBydataId(id);
			Long docId = dataDealModel.getDocId();
			if(docId != null){
				ModelDataFile modelDataFile = modelDataFileService.findById(docId);
				model.addAttribute("modelDataFile", modelDataFile)
					 .addAttribute("currentOrgan", currentOrgan);
			}else{
				model.addAttribute("modelDataFile", null)
					 .addAttribute("currentOrgan", currentOrgan);
			}
			model.addAttribute("dataDealModel", dataDealModel);
		}
		return  mode.equals("detail") ? "risk/notice/data-deal-detail"
				: "risk/notice/feedback-detail";
	}
	
	
	/**
	 * 下载附件
	 * @param response
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value="download",method=RequestMethod.POST)
	public void downloadFile(HttpServletResponse response, Long id) throws Exception{
		Map<String,Object> modelDataFile = modelDataFileService.getFile(id);
		String fileName = modelDataFile.get("fileName").toString();
		String extName = modelDataFile.get("extName").toString();
		oracle.sql.BLOB blob = (BLOB) modelDataFile.get("fileContent");
		long size = blob.length();
		java.io.InputStream in = blob.getBinaryStream();
		byte[] data = new byte[(int) size];
		in.read(data);
		ByteArrayInputStream is = new ByteArrayInputStream(data, 0, (int)size);
		FileUtils.downloadFile(response, is, fileName+"."+extName);
		is.close();
	}
	
	
	/**
	 * 显示发送的通知书，通过状态来判断是否显示全部  1未处理，2已处理，否则显示全部
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value="noticesend/show", method=RequestMethod.GET)
	public String shownoticesend(@RequestParam Map<String, Object> params,  Model model){
		String status = null;
		if(params.containsKey("status")){
			status = (String) params.get("status");
			if(status.equals("1")){
				model.addAttribute("title", "未处理");
			}else if(status.equals("2")){
				model.addAttribute("title", "已处理");
			}
		}else{
			model.addAttribute("title", "全部");
		}
		model.addAttribute("status", status);
		SysUser user = SysUserUtils.getSessionLoginUser();
		SysOffice currentOrgan = sysOfficeService.selectByPrimaryKey(user.getOfficeId());
		model.addAttribute("orgLevel", currentOrgan.getOrgLevel());
		return "risk/notice/send";
	}
	
	
	/**
	 * 显示接收的通知书，通过状态来判断是否显示全部  1未处理，2已处理，否则显示全部
	 * @param params
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="noticesend/list", method=RequestMethod.POST)
	public void noticereceivelist(@RequestParam Map<String, Object> params,  Model model,HttpServletResponse response) 
			throws Exception{
		//获取当前用户
		SysUser user = SysUserUtils.getSessionLoginUser();
		String status = (String) params.get("status");
		model.addAttribute("status", status);
		PageInfo<DataNoticeModel> page = null;
		params.put("organId", user.getOfficeId());
		if(params.containsKey("bnoticeOrganId")){
			params.put("bnoticeOrganId", params.get("bnoticeOrganId").toString());
		}
		if(params.containsKey("organName")){
			String organName = new String(((String) params.get("organName")).getBytes("ISO-8859-1"),"UTF-8");
			organName = java.net.URLDecoder.decode(organName, "UTF-8");
			params.put("organName", organName);
		}
		page = dataNoticeService.findSendNoticePageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	/**
	 * 非现场核实，获取数据id并跳转
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value="deal1", method=RequestMethod.POST)
	public String deal1(@RequestParam Map<String, Object> params,  Model model){
		String id = (String) params.get("id");
		if(null!=id && !"".equals(id)){
			//通过通知书处理
			DataNoticeModel  notice = dataNoticeService.findByPkId(Integer.parseInt(id));
			ModelBase  modelBase = modelBaseService.findById(Long.parseLong(notice.getModelId().toString()));
			model.addAttribute("dataId", notice.getDataId() )
				.addAttribute("modelId",notice.getModelId())
				.addAttribute("organId",notice.getBnoticeOrganId())
				.addAttribute("custNo",notice.getCustNo())
				.addAttribute("model", modelBase)
				.addAttribute("bnoticeorganName", notice.getBnoticeOrganName())
				.addAttribute("trandate", notice.getTranDate())
				.addAttribute("dataDate", notice.getTranDate() )
				.addAttribute("noticeId", notice.getId())
				.addAttribute("dealType", "2")	//非现场核实代号2
				.addAttribute("flag", false);	//用来区分是否从数据源处理还是通知书中处理，true为数据源处理
				
		}else{
			String dataId = (String) params.get("dataId");
			String modelId = (String) params.get("modelId");
			String organId = (String) params.get("organId");
//			String dataDate = (String) params.get("dataDate");
			ModelBase  modelBase = modelBaseService.findById(Long.parseLong(modelId));
			ModelData md =  riskDetectionService.findModelDataByPkid(Integer.parseInt(dataId));
			SysOffice organ = sysOfficeService.selectByPrimaryKey(Long.parseLong(md.getOrganId()));
			model.addAttribute("dataId", dataId )
				 .addAttribute("Id", modelId )
				 .addAttribute("organId",organId)
				 .addAttribute("custNo",params.containsKey("custNo")?params.get("custNo").toString():null)
				 .addAttribute("modelId",modelId)
				 .addAttribute("model", modelBase)
				 .addAttribute("bnoticeorganName", organ.getName())
				 .addAttribute("trandate", md.getDataDate())
				 .addAttribute("dataDate", md.getDataDate())
				 .addAttribute("dealType", "2")	//非现场核实代号2
				 .addAttribute("flag", true);	//用来区分是否从数据源处理还是通知书中处理，true为数据源处理
		}
		
		return "risk/deal1";
	}
	
	
	/**
	 * 现场核实，获取数据id并跳转
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value="deal2", method=RequestMethod.POST)
	public String deal2(@RequestParam Map<String, Object> params,  Model model){
		String id = (String) params.get("id");
		if(null!=id && !"".equals(id)){
			//通过通知书处理
			DataNoticeModel  notice = dataNoticeService.findByPkId(Integer.parseInt(id));
			ModelBase  modelBase = modelBaseService.findById(Long.parseLong(notice.getModelId().toString()));
			model.addAttribute("dataId", notice.getDataId() )
				.addAttribute("modelId",notice.getModelId())
				.addAttribute("organId",notice.getBnoticeOrganId())
				.addAttribute("custNo",notice.getCustNo())
				.addAttribute("model", modelBase)
				.addAttribute("bnoticeorganName", notice.getBnoticeOrganName())
				.addAttribute("trandate", notice.getTranDate())
				.addAttribute("dataDate", notice.getTranDate() )
				.addAttribute("noticeId", notice.getId())
				.addAttribute("dealType", "1")	//现场核实代号1
				.addAttribute("flag", false);	//用来区分是否从数据源处理还是通知书中处理，true为数据源处理
		}else{
			String dataId = (String) params.get("dataId");
			String modelId = (String) params.get("modelId");
			String organId = (String) params.get("organId");
			String dataDate = (String) params.get("dataDate");
			ModelBase  modelBase = modelBaseService.findById(Long.parseLong(modelId));
			ModelData md =  riskDetectionService.findModelDataByPkid(Integer.parseInt(dataId));
			SysOffice organ = sysOfficeService.selectByPrimaryKey(Long.parseLong(md.getOrganId()));
			model.addAttribute("dataId", dataId )
				.addAttribute("modelId", modelId )
				.addAttribute("organId", organId )
				.addAttribute("custNo",params.containsKey("custNo")?params.get("custNo").toString():null)
				.addAttribute("model", modelBase)
				.addAttribute("bnoticeorganName", organ.getName())
				.addAttribute("trandate", md.getDataDate())
				.addAttribute("dataDate", dataDate )
				.addAttribute("dealType", "1")	//现场核实代号1
				.addAttribute("flag", true);	//用来区分是否从数据源处理还是通知书中处理，true为数据源处理
		}
		
		return "risk/deal1";
	}
	
	/**
	 * 下发通知
	* @param params
	 */
	@RequestMapping(value = "sendNotice",method=RequestMethod.POST)  
    public @ResponseBody Integer  sendNotice(@ModelAttribute DataNoticeModel notice, 
    		@RequestParam(value = "file", required = false) MultipartFile file,
    		@RequestParam(value = "dataIdWs") String dataIds, String flag,
    		HttpServletRequest request, ModelMap model) {
	 	ModelDataFile dataFile = new ModelDataFile();
	 	Map<String,Object> params = new HashMap<String, Object>();
	 	StringTokenizer dataIdWs = new StringTokenizer(dataIds,",");
	 while(dataIdWs.hasMoreTokens()){	
	 	Integer dataId = Integer.parseInt(dataIdWs.nextToken().toString());
	 	//数据id
		notice.setDataId(dataId);
		
		ModelData md =  riskDetectionService.findModelDataByPkid(dataId);
		params.put("dataId", notice.getDataId());
		params.put("organId", SysUserUtils.getSessionLoginUser().getOfficeId());
		//先判断省级是否自行处理了
		if(md.getDealStatus() == 1){
			DataNoticeModel dataNoticeModel = dataNoticeService.checkIsReapted(params);
			if(dataNoticeModel == null){
				Long docId = null;
				InputStream is=null;
				//获取当前用户
				SysUser user = SysUserUtils.getSessionLoginUser();
				if(file != null){
					try
					{
						docId = modelDataFileService.generateId();
						is = file.getInputStream();
						byte[] data = new byte[(int) file.getSize()];  
						is.read(data);
						dataFile.setId(docId);
						dataFile.setDataId(dataId);
						dataFile.setFileName(FileUtils.getFileNameNoSuffix(file.getOriginalFilename()));
						dataFile.setExtName(FileUtils.getFileSuffix(file.getOriginalFilename()));
						dataFile.setFileContent(data);;
						modelDataFileService.saveFile(dataFile);
					} catch (IOException e)
					{
						return -1;
					}
				}
				SysOffice currentOrgan = sysOfficeService.selectByPrimaryKey(user.getOfficeId());						//当前机构（下发机构）
				SysOffice bOrgan = sysOfficeService.selectByPrimaryKey(Long.parseLong(md.getOrganId()));				//交易机构
				SysOffice organ2 = sysOfficeService.selectByPrimaryKey(Long.parseLong(notice.getNoticeOrganId2()));		//被通知机构
				notice.setDealStatus("1");		//处理状态默认为未处理
				notice.setNoticeStatus("3");
				notice.setNoticeSender(user.getUsername());
				//通知机构
				notice.setNoticeOrganId(user.getOfficeId().toString());
				notice.setNoticeOrganName(currentOrgan.getName());
				
				
				//交易机构
				notice.setBnoticeOrganId(md.getOrganId().toString());
				notice.setBnoticeOrganName(bOrgan.getName());
				
				//交易日期
				notice.setTranDate(md.getDataDate());
				notice.setNoticeOrganName2(organ2.getName());
				
				if(docId != null){
					notice.setDocId(docId);
				}
				int count1 =  dataNoticeService.SendNotice(notice);
				if(count1 > 0){
					if(flag.equals("true")){
						int count2 = dataNoticeService.updateStatus(notice.getDataId(),2);
						if(count2 > 0){
							//return count1;
						}else{
							return -1; //下发失败，返回-1
						}
					}else{
						params.clear();
						params.put("noticeStatus", "2");
						params.put("dataId", notice.getDataId());
						params.put("organId", currentOrgan.getParentId());
						int count3 = dataNoticeService.updateNoticeStatus(params);
						if(count3 > 0){
							//return count1;
						}else{
							return -1; //下发失败，返回-1
						}
					}
					
				}else {
					return -1; //下发失败，返回-1
				}
			}else{
				return 0;	//0:重复下发
			}        
		}else{
			return -2;	//已处理完毕
		}
	 }
	 return 1;
    }  
	
	
	/**
	 * 跳转到历史处理记录
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toHistory",method=RequestMethod.POST)
	public String toHistory(@RequestParam Map<String, Object> params, Model model){
			model.addAttribute("modelId", params.get("modelId").toString())
				 .addAttribute("organId", params.get("organId").toString())
				 .addAttribute("flag", params.get("flag").toString())
				 .addAttribute("noticeId", params.get("noticeId").toString())
				 .addAttribute("dealType", params.get("dealType").toString())
				 .addAttribute("dataId", params.get("dataId").toString());
		return  "risk/history-deal-info";
	}
	
	
	/**
	 * 历史处理记录列表
	 * @param params
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="historyDealInfo", method=RequestMethod.POST)
	public void historyDealInfo(@RequestParam Map<String, Object> params,
			HttpServletResponse response) throws Exception
	{
		PageInfo<DataDealModel> page = dataDealService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	
	/**
	 * 快速处理
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="fastDeal", method=RequestMethod.POST)
	public @ResponseBody int fastDeal(@RequestParam Map<String, Object> params,HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		try
		{
			Long id = Long.parseLong(params.get("id").toString().trim());
			Integer dataId = Integer.parseInt(params.get("dataId").toString().trim());
			Long docId = null;
			DataDealModel temp = dataDealService.selectByPrimaryKey(id);
			if(temp.getDocId() != null){
				docId = modelDataFileService.generateId();
				ModelDataFile modelDataFile = modelDataFileService.findById(temp.getDocId());
				modelDataFile.setDataId(dataId);
				modelDataFile.setId(docId);
				modelDataFileService.saveFile(modelDataFile);
			}
			DataDealModel dataDealModel = new DataDealModel();
			temp.setDataId(dataId);
			temp.setId(dataDealService.generateId());
			BeanUtils.copyProperties(dataDealModel, temp);
			ModelMap model = new ModelMap();
			params.put("flag", params.get("flag").toString());
			int count = this.dataDeal(dataDealModel, null, params, request, model);
			return count;
		} catch (Exception e)
		{
			return -1;
		}
	}
	
	
	/**
	 * 现场核实/非现场核实
	 * @param params
	 */
	@RequestMapping(value="dataDeal",method=RequestMethod.POST)
	public @ResponseBody int dataDeal(@ModelAttribute DataDealModel dataDealModel, 
    		@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam Map<String, Object> params,
    		HttpServletRequest request, ModelMap model){
		Long docId = null;
		ModelDataFile dataFile = new ModelDataFile();
	 	Integer dataId = dataDealModel.getDataId();
	 	//从数据库查找是否有历史处理记录
	 	DataDealModel temp = dataDealService.findBydataId(dataId);
	 	SysUser user = SysUserUtils.getSessionLoginUser();
	 	//通知书主键
	 	String noticeId = (String) params.get("noticeId");
	 	String flag = params.get("flag").toString();
	 	ModelData md =  riskDetectionService.findModelDataByPkid(dataId);
//	 	DataNoticeModel dataNoticeModel = dataNoticeService.findByPkId(Integer.parseInt(noticeId));
		InputStream is=null;
//		if(!dataNoticeModel.getNoticeStatus().equals("2")){
		//从数据源处理
		if(flag.equals("true")){
			if(md.getNoticeStatus() == 1){
				if(temp == null){
					if(file != null){
						try
						{
							docId = modelDataFileService.generateId();
							is = file.getInputStream();
							byte[] data = new byte[(int) file.getSize()];  
							is.read(data);
							dataFile.setId(docId);
							dataFile.setDataId(dataId);
							dataFile.setFileName(FileUtils.getFileNameNoSuffix(file.getOriginalFilename()));
							dataFile.setExtName(FileUtils.getFileSuffix(file.getOriginalFilename()));
							dataFile.setFileContent(data);
							modelDataFileService.saveFile(dataFile);	//下发和核实保存文件同一个方法
						} catch (IOException e)
						{
							return -1;
						}
					}
					dataDealModel.setDataStatus(2);
					dataDealModel.setDealTime(DateUtil.getDate(new Date()));
					dataDealModel.setDealUser(user.getName());
			        if(docId != null){
			        	dataDealModel.setDocId(docId);
					}
			        int count1 =  dataDealService.dataDeal(dataDealModel);
			        if(count1 > 0){
			        	if(flag.equals("true")){
			        		int count2 = dataDealService.updateDealStatus(dataId, 2);
			        		if(count2 > 0){
								return count1;
					    	}else{
								return -1; //处理失败，返回-1
					    	}
			        	}else{
			        		//更改通知书的状态
				        	if(null!=noticeId && !"".equals(noticeId)){
				        		int count2 = dataDealService.updateDealStatus(dataId, 2);
				        		Map<String,Object> p = new HashMap<String,Object>();
				        		p.put("noticeStatus", "4");
				        		p.put("dealStatus", "2");
				        		p.put("dataId", dataId);
				        		p.put("noticeEndtime", DateUtil.getDate(new Date()));
				        		p.put("noticeDealer", user.getName());
				        		int count3 = dataNoticeService.endNoticeStatus(p);
				        		if(count2 > 0 && count3 > 0){
									return count1;
						    	}else{
									return -1; //处理失败，返回-1
						    	}
				        	}
			        	}
					}else{
						return -1; //核实失败，返回-1
					}
				}else{
					return 0;	//已核实，返回0
				}
			}else if(md.getDealStatus() == 2){
				return 0;		
			}else {
				return -2;		//返回-2，已通知，未处理
			}
		}//从通知单处理
		else{
			if(temp == null){
				if(file != null){
					try
					{
						docId = modelDataFileService.generateId();
						is = file.getInputStream();
						byte[] data = new byte[(int) file.getSize()];  
						is.read(data);
						dataFile.setId(docId);
						dataFile.setDataId(dataId);
						dataFile.setFileName(FileUtils.getFileNameNoSuffix(file.getOriginalFilename()));
						dataFile.setExtName(FileUtils.getFileSuffix(file.getOriginalFilename()));
						dataFile.setFileContent(data);
						modelDataFileService.saveFile(dataFile);	//下发和核实保存文件同一个方法
					} catch (IOException e)
					{
						return -1;
					}
				}
				dataDealModel.setDataStatus(2);
				dataDealModel.setDealTime(DateUtil.getDate(new Date()));
				dataDealModel.setDealUser(user.getName());
		        if(docId != null){
		        	dataDealModel.setDocId(docId);
				}
		        int count1 =  dataDealService.dataDeal(dataDealModel);
		        if(count1 > 0){
		        	if(flag.equals("true")){
		        		int count2 = dataDealService.updateDealStatus(dataId, 2);
		        		if(count2 > 0){
							return count1;
				    	}else{
							return -1; //处理失败，返回-1
				    	}
		        	}else{
		        		//更改通知书的状态
			        	if(null!=noticeId && !"".equals(noticeId)){
			        		int count2 = dataDealService.updateDealStatus(dataId, 2);
			        		Map<String,Object> p = new HashMap<String,Object>();
			        		p.put("noticeStatus", "4");
			        		p.put("dealStatus", "2");
			        		p.put("dataId", dataId);
			        		p.put("noticeEndtime", DateUtil.getDate(new Date()));
			        		p.put("noticeDealer", user.getName());
			        		int count3 = dataNoticeService.endNoticeStatus(p);
			        		if(count2 > 0 && count3 > 0){
								return count1;
					    	}else{
								return -1; //处理失败，返回-1
					    	}
			        	}
		        	}
				}else{
					return -1; //核实失败，返回-1
				}
			}else{
				return 0;	//已核实，返回0
			}
		}
		return -1;
	}
	
	
	 
	
	
	/**
	 * 查询数据结果表头
	 * @param params
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping(value="queryHeaders",method = RequestMethod.POST)
	public String queryHeaders(@RequestParam  Map<String, Object> params, Model model,
			HttpServletResponse response){
		List<Map<String, Object>> headerList = dataNoticeService.queryHeaders(params);
		model.addAttribute("headerList",headerList)
			 .addAttribute("modelId", params.get("modelId"))
			 .addAttribute("organId", params.get("organId"))
			 .addAttribute("custNo", params.get("custNo"))
			 .addAttribute("dealStatus", params.get("dealStatus"));
		return "risk/detail";
	}
	
	/**
	 * 查询数据结果内容
	 * @param params
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="queryContents",method = RequestMethod.POST)
	public void queryContents(@RequestParam  Map<String, Object> params, 
			HttpServletResponse response) throws Exception{
		SysUser user = SysUserUtils.getSessionLoginUser();
		params.put("organId", user.getOfficeId());
		PageInfo<Map<String,Object>> page = dataNoticeService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	
	/**
	 * 查询单条详细数据键
	 * @param params
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping(value="queryDetailsHeaders",method = RequestMethod.POST)
	public String queryDetailsHeaders(@RequestParam  Map<String, Object> params, Model model,
			HttpServletResponse response){
		Map<String, Object> newParams = new HashMap<String, Object>();
		Integer modelId = Integer.parseInt(params.get("modelid").toString());
		Integer dataId = Integer.parseInt(params.get("dataid").toString());
		newParams.put("modelId", modelId);
		List<Map<String, Object>> headerList = dataNoticeService.queryHeaders(newParams);
		model.addAttribute("headerList",headerList)
			 .addAttribute("modelId", modelId)
			 .addAttribute("dataId", dataId);
		return "risk/detail2";
	}
	
	/**
	 * 查询单条详细数据值
	 * @param params
	 * @param response
	 */
	@RequestMapping(value="queryDetailsContents",method = RequestMethod.POST)
	public void queryDetailsContents(@RequestParam  Map<String, Object> params,
			HttpServletResponse response){
		List<Map<String, Object>> dataList = dataNoticeService.queryContents(params);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> dataMap = dataList.get(0);
		Map<String,Object> map = null;
		for (Object key : dataMap.keySet())
		{
			map = new HashMap<String, Object>();
			map.put("key", key);	map.put("value", dataMap.get(key));
			list.add(map);
		}
		ResponseUtils.renderJson(response, list);
	}
	
	
	
	/**
	 * 批量导出模型数据
	 * @throws Exception 
	 */
	@RequestMapping(value = "export",method = RequestMethod.POST)
	public void exportFile(@RequestParam Map<String, Object> params,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF";
		Map<String, Object> map = null;
		String custNo = null;
		if(params.containsKey("custNo")){
			custNo = params.get("custNo").toString();
		}
		if(params.containsKey("id")){
			String array [] = params.get("id").toString().split(",");
			
			List<File> files = new ArrayList<File>();
			for (int i = 0; i < array.length; i++)
			{
				map = new HashMap<String, Object>();
				map.put("modelId", array[i]);
				if(custNo != null && custNo.length() != 0){
					map.put("custNo", custNo);
				}
				files.add(generateFile(map, request, response));
			}
			FileUtils.downLoadFiles(filePath+"\\模型数据文件.zip", files,  response);
		}else{
			this.downloadFile(params, response);
		}
		
	}
	
	/**
	 * 生成临时文件
	 * @param params
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public File generateFile(Map<String, Object> params,HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		String filePath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF";
		String modelName = null;
		modelName = riskDetectionService.findById(Long.parseLong(params.get("modelId").toString())).getName();
		List<Map<String, Object>> headerList = dataNoticeService.queryHeaders(params);//表头
		List<Map<String, Object>> dataList = dataNoticeService.queryContents(params);//数据
		Map<String, Object> headerMap = new HashMap<String, Object>();
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		
		if(headerList.size() != 1){
			if(dataList.size() != 0){
				titleMap.put("通知状态","noticeStatus");   titleMap.put("处理状态","dealStatus");
				titleMap.put("数据编号","dataId");     titleMap.put("模型编号","modelId");   
				titleMap.put("会计日期","dataDate");   titleMap.put("机构编号","organId");
				titleMap.put("客户号", "custNo");
				for (int i = 0; i < headerList.size(); i++)
				{
					headerMap = headerList.get(i);
					titleMap.put(headerMap.get("fields").toString(),headerMap.get("itemvalues").toString());
				}
			}else{
				headerList.removeAll(dataList);
				headerMap.put("温馨提示：无相关数据！", "");
				dataList.add(headerMap);
				titleMap.put("温馨提示：无相关数据！", "");
			}
		}else{
			titleMap.put("温馨提示：", "无相关数据！！！");
		}
		try {
			File file = new File(filePath+"\\"+modelName+".xls");
			FileOutputStream fos = new FileOutputStream(file);
			PoiUtil.writeMyExcel(dataList, fos, titleMap);
			fos.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 下载单个文件
	 * @param params
	 * @param response
	 */
	public void downloadFile(Map<String, Object> params,HttpServletResponse response){
		String modelName = null;
		modelName = riskDetectionService.findById(Long.parseLong(params.get("modelId").toString())).getName();
		List<Map<String, Object>> headerList = dataNoticeService.queryHeaders(params);//表头
		List<Map<String, Object>> dataList = dataNoticeService.queryContents(params);//数据
		Map<String, Object> headerMap = new HashMap<String, Object>();
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		
		if(headerList.size() != 1){
			if(dataList.size() != 0){
				titleMap.put("通知状态","noticestatus");   titleMap.put("处理状态","dealStatus");
				titleMap.put("数据编号","dataId");     titleMap.put("模型编号","modelId");   
				titleMap.put("会计日期","dataDate");   titleMap.put("机构编号","organId");
				titleMap.put("客户号", "custNo");
				for (int i = 0; i < headerList.size(); i++)
				{
					headerMap = headerList.get(i);
					titleMap.put(headerMap.get("fields").toString(),headerMap.get("itemvalues").toString());
				}
			}else{
				headerList.removeAll(dataList);
				headerMap.put("温馨提示：无相关数据！", "");
				dataList.add(headerMap);
				titleMap.put("温馨提示：无相关数据！", "");
			}
		}else{
			titleMap.put("温馨提示：", "无相关数据！！！");
		}
		try {
			//流的方式直接下载
			ExcelUtils.exportMyExcel(response, modelName+".xls", dataList, titleMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
