package com.krm.dbaudit.web.sys.controller;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.BLOB;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.dbaudit.common.utils.FileUtils;
import com.krm.dbaudit.web.sys.model.SysAccessory;
import com.krm.dbaudit.web.sys.service.KbAuditService;
import com.krm.dbaudit.web.sys.service.KbProfitService;
import com.krm.dbaudit.web.sys.service.KbStdService;
import com.krm.dbaudit.web.sys.service.SysAccessoryService;

/**
 * 附件上传下载处理器
 * @author Parker
 *
 */
@Controller
@RequestMapping("sysAccessory")
public class SysAccessoryController
{
	@Resource
	private SysAccessoryService sysAccessoryService;
	@Resource
	private KbStdService kbStdService;
	@Resource
	private KbProfitService kbProfitService;
	@Resource
	private KbAuditService kbAuditService;
	/**
	 * 弹窗显示
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="file/showlayer",method=RequestMethod.POST)
	public String layer(@RequestParam Map<String,Object> params, Model model){
		Long id = Long.parseLong(params.get("id").toString());
		String mode = params.get("mode").toString();
		SysAccessory sysAccessory = sysAccessoryService.findById(id);
		model.addAttribute("sysAccessory", sysAccessory);
		model.addAttribute("mode", mode);
		return "sys/repository/accessory-detail";
	}
	
	/**
	 * 下载附件
	 * @param response
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value="download",method=RequestMethod.POST)
	public void downloadFile(HttpServletResponse response, Long id) throws Exception{
		Map<String,Object> sysAccessory = sysAccessoryService.getSysAccessory(id);
		String fileName = sysAccessory.get("fileName").toString();
		String extName = sysAccessory.get("extName").toString();
		oracle.sql.BLOB blob = (BLOB) sysAccessory.get("accessory");
		long size = blob.length();
		java.io.InputStream in = blob.getBinaryStream();
		byte[] data = new byte[(int) size];
		in.read(data);
		ByteArrayInputStream is = new ByteArrayInputStream(data, 0, (int)size);
		FileUtils.downloadFile(response, is, fileName+"."+extName);
		is.close();
	}
	
	/**
	 * 删除审计依据附件
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer delete(Long id){
		Integer count = null;
		String docTyte = null;
		SysAccessory sysAccessory = sysAccessoryService.findById(id);
		docTyte = sysAccessory.getDocType();
		if(sysAccessory != null){
			count = sysAccessoryService.deleteAccessoryByRootId(id);
			if(count != null && count > 0){
				if(docTyte.equals("1501")){
					return kbStdService.updateByDocId(id);
				}if(docTyte.equals("1503")){
					return kbProfitService.updateByDocId(id);
				}if(docTyte.equals("1401")){
					return kbAuditService.updateByDocId(id);
				}
			}
		}
		return -1;
	}
	
	
}
