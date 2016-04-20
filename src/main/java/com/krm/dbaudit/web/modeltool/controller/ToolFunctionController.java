package com.krm.dbaudit.web.modeltool.controller;

import java.io.UnsupportedEncodingException;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.web.modeltool.service.ToolFunctionService;
import com.krm.dbaudit.web.util.ResponseUtils;

//import com.krm.dbaudit.common.utils.FormulaUtils;
/**
* @author chenwei on 2016-1-11
*/

@Controller
@RequestMapping("model/function")
public class ToolFunctionController  extends BaseController{
	
	@Resource
	private ToolFunctionService toolFunctionService;
	
	/**
	 * 鍒嗘瀽宸ュ叿鑾峰彇鏀寔鍑芥暟鍒楄〃
	* @param 
	* @return json
	 */
	@RequestMapping(value="getfunlist")
	public void getfunlist(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		String s = toolFunctionService.getFunctionList(params);
		ResponseUtils.renderJson(response, s);
	}
	
	/**
	 * 鍒嗘瀽宸ュ叿鑾峰彇鏀寔鍑芥暟楠岃瘉
	* @param 
	* @return json
	 */
	@RequestMapping(value="checkfunlist")
	public void checkfunlist(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		String s = toolFunctionService.checkFunction(params);
		ResponseUtils.renderJson(response, s);
	}
	
	@RequestMapping(value="checkscript")
	public void checkscript(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
//		FormulaUtils.checkScript("((-1+2+abs(1,'2',33333))/231)>1 and '1=1'");
	}
	
	@RequestMapping(value="getfuninfo")
	public void getfuninfo(@RequestParam Map<String, Object> params,HttpServletResponse response) throws UnsupportedEncodingException{
		String s = toolFunctionService.getFunctionInfo(params);
		ResponseUtils.renderJson(response, s);
	}
}