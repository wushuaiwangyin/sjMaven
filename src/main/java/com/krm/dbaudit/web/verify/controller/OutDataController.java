package com.krm.dbaudit.web.verify.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.BaseController;
import com.krm.dbaudit.common.excel.ExcelUtils;
import com.krm.dbaudit.common.excel.template.utils.PoiUtil;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.util.DateUtil;
import com.krm.dbaudit.web.util.ResponseUtils;
import com.krm.dbaudit.web.verify.model.OutData;
import com.krm.dbaudit.web.verify.service.OutDataService;
/**
* @author tanwen on 2015-11-02
*/

@Controller
@RequestMapping("verify/out/data")
public class OutDataController extends BaseController {
	@Resource
	private OutDataService outDataService;
	/**
	 * 默认跳转到外部数据页面
	* @return 
	 */
	@RequestMapping
	public String toOutData(){
		return "verify/outdata/index";
	}
	
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void outdatalist(@RequestParam Map<String, Object> params,
			HttpServletResponse response){
		
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
			PageInfo<OutData> page = outDataService.findPageInfo(map);
			ResponseUtils.renderJson(response, page);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "upload",method=RequestMethod.POST)
	public @ResponseBody Integer upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model){
		System.out.println("开始解析外部数据文件.");
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();
        System.out.println("外部数据文件路径："+path+"----"+fileName);
        InputStream is=null;
        try {
			is = file.getInputStream();
			List<Map<String,Object>> outdatas = createInsertSQL(is);
			//批量插入
	        outDataService.insertBatch(outdatas);
	        System.out.println("数据已经成功插入到数据库！");
		} catch (IOException e) {
			e.printStackTrace();
		}
        return 1;
	}
	
	/**
	 * 读取外部数据文件并把文件内容转换成对象列表
	 * @param file
	 * @return
	 */
	private static List<Map<String,Object>> createInsertSQL(InputStream is){
		List<Map<String,Object>> datalist = new ArrayList<Map<String,Object>>();
		XSSFWorkbook xssfWorkbook = null;
		Map<String,Object> map = null;
		String import_date = DateUtil.getDate(new Date());
		try {
			xssfWorkbook = new XSSFWorkbook(is);
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						XSSFCell event_desc = xssfRow.getCell(0);
						XSSFCell event_user = xssfRow.getCell(1);
						XSSFCell event_date = xssfRow.getCell(2);
						XSSFCell source = xssfRow.getCell(3);
						XSSFCell bus_type = xssfRow.getCell(4);
						XSSFCell province = xssfRow.getCell(5);
						XSSFCell id_num = xssfRow.getCell(6);
						
						//把数据类型的cell转换为string类型
						if(event_date.getCellType()==Cell.CELL_TYPE_NUMERIC){
							event_date.setCellType(Cell.CELL_TYPE_STRING);
						}
						if(id_num.getCellType()==Cell.CELL_TYPE_NUMERIC){
							id_num.setCellType(Cell.CELL_TYPE_STRING);
						}
						map = new HashMap<String,Object>();
						
						map.put("eventDesc", event_desc.getStringCellValue());
						map.put("eventUser", event_user.getStringCellValue());
						map.put("eventDate", event_date.getStringCellValue());
						map.put("source", source.getStringCellValue());
						map.put("busType", bus_type.getStringCellValue());
						map.put("province", province.getStringCellValue());
						map.put("idNum", id_num.getStringCellValue());
						map.put("importDate", import_date);

						datalist.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return datalist;
	}
	
	
	
	/**
	 * 弹窗显示
	* @param params 
	* @return
	 */
	@RequestMapping(value="showimportlayer",method=RequestMethod.POST)
	public String layer(Long id, Model model){
		ModelBase modelBase = null;
		return "verify/outdata/import";
	}
	
	/**
	 * 下载外部数据文件
	 * @param params
	 * @param response
	 */
	@RequestMapping(value="download",method=RequestMethod.POST)
	public void downloadExcelFile(@RequestParam Map<String, Object> params,
			HttpServletResponse response){
		List<OutData> list = outDataService.listOutData(params);
		if(list.size()>0){
			Map<String,String> titleMap = new HashMap<String,String>();
			//设置EXCEL标题
			titleMap.put("事件", "eventDesc");
			titleMap.put("事件人", "eventUser");
			titleMap.put("事件时间", "eventDate");
			titleMap.put("网站来源", "source");
			titleMap.put("行业性质", "busType");
			titleMap.put("所属省份", "province");
			titleMap.put("事件人证件号码", "idNum");
			titleMap.put("导入日期", "importDate");
			try {
				ExcelUtils.exportExcel(response, "外部数据.xls", list, titleMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 导出execl 
	 */
	@RequestMapping(value = "downtemplate",method = RequestMethod.GET)
	public void downtemplate(@RequestParam Map<String, Object> params,
			HttpServletResponse response,HttpServletRequest request){
		String path = request.getSession().getServletContext().getRealPath("/") ;
		System.out.println(path+"static"+File.separator+"template"+File.separator+"外部数据模板.xlsx");
		String templatePath = path+"static"+File.separator+"template"+File.separator+"外部数据模板.xlsx";
		try {
			ExcelUtils.exportExcel(response, templatePath, "外部数据模板.xlsx", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
	
	
	
}
