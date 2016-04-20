/*package com.krm.dbaudit.web.upload.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.upload.service.UploadService;

*//**
* @author Parker on 2016-02-22
*//*

@Controller
@RequestMapping("upload")
public class UploadController {
	
	@Resource
	private UploadService uploadService;
	
	*//**
	 * 
	 * @param param
	 * @param model
	 * @return
	 * @author wushuai
	 * @date 2016-3-4
	 * @version 1.0
	 *//*
	@RequestMapping
	public String toModelBase(@RequestParam Map<String,String> param, Model model){
        model.addAttribute("ps", "ws");
        return "upload/index";
    }
	
	*//**
     * 弹窗显示
    * @param params 
    * @return
     *//*
    @RequestMapping(value="showimportlayer",method=RequestMethod.POST)
    public String layer(@RequestParam("ws") String ws, Model model){
        model.addAttribute("ws", ws);
        return "upload/import";
    }
	
	@RequestMapping(value = "upload",method=RequestMethod.POST)
	public @ResponseBody Integer upload(@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam("ws") String ws, HttpServletRequest request, ModelMap model){
		System.out.println("开始解析外部数据文件.");
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();
        System.out.println("外部数据文件路径："+path+"----"+fileName);
        InputStream is=null;
        try {
			is = file.getInputStream();
			//批量插入表名
			if(ws.equals("1")){
			List<Map<String,Object>> outdatas = importTableName(is);
			uploadService.saveTable(outdatas);
			}
			//批量插入表字段
			if(ws.equals("2")){
			List<Map<String,Object>> outdatas = importTableField(is);
	        uploadService.saveTableAlians(outdatas);
			}
	        System.out.println("数据已经成功插入到数据库！");
		} catch (IOException e) {
			e.printStackTrace();
		}
        return 1;
	}
	
	*//**
	 * 批量解析表名excel
	 * @param file
	 * @return
	 *//*
	private  List<Map<String,Object>> importTableName(InputStream is){
		List<Map<String,Object>> datalist = new ArrayList<Map<String,Object>>();
		XSSFWorkbook xssfWorkbook = null;
		Map<String,Object> map = null;
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
						//表名
						XSSFCell tableName = xssfRow.getCell(0);
						XSSFCell tableAlias = xssfRow.getCell(1);
						XSSFCell tabelParent = xssfRow.getCell(2);
						XSSFCell tableFlag = xssfRow.getCell(3);
						XSSFCell tableTypeId = xssfRow.getCell(4);
						XSSFCell createBy = xssfRow.getCell(5);
						
						//把数据类型的cell转换为string类型
						//表名
						if(tabelParent.getCellType()==Cell.CELL_TYPE_NUMERIC){
							tabelParent.setCellType(Cell.CELL_TYPE_STRING);
						}
						if(tableFlag.getCellType()==Cell.CELL_TYPE_NUMERIC){
							tableFlag.setCellType(Cell.CELL_TYPE_STRING);
						}
						if(tableTypeId.getCellType()==Cell.CELL_TYPE_NUMERIC){
							tableTypeId.setCellType(Cell.CELL_TYPE_STRING);
						}
						if(createBy.getCellType()==Cell.CELL_TYPE_NUMERIC){
							createBy.setCellType(Cell.CELL_TYPE_STRING);
						}
						
						map = new HashMap<String,Object>();
						//表名
						map.put("tableName", tableName.getStringCellValue());
						map.put("tableAlias", tableAlias.getStringCellValue());
						map.put("tabelParent", tabelParent.getStringCellValue());
						map.put("tableFlag", tableFlag.getStringCellValue());
						map.put("tableTypeId", tableTypeId.getStringCellValue());
						map.put("userid", createBy.getStringCellValue());
						datalist.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return datalist;
	}
	
	
	*//**
	 * 批量解析表结构excel
	 * @param file
	 * @return
	 *//*
	private  List<Map<String,Object>> importTableField(InputStream is){
	List<Map<String,Object>> datalist = new ArrayList<Map<String,Object>>();
	XSSFWorkbook xssfWorkbook = null;
	Map<String,Object> map = null;
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
					//表字段
					XSSFCell tableName = xssfRow.getCell(0);
					XSSFCell filedName = xssfRow.getCell(1);
					XSSFCell filedAlias = xssfRow.getCell(2);
					XSSFCell filedEntityType = xssfRow.getCell(3);
					XSSFCell filedType = xssfRow.getCell(4);
					XSSFCell filedLength = xssfRow.getCell(5);
					
					//把数据类型的cell转换为string类型
					
					//表结构
					if(filedEntityType.getCellType()==Cell.CELL_TYPE_NUMERIC){
						filedEntityType.setCellType(Cell.CELL_TYPE_STRING);
					}
					if(filedType.getCellType()==Cell.CELL_TYPE_NUMERIC){
						filedType.setCellType(Cell.CELL_TYPE_STRING);
					}
					if(filedLength.getCellType()==Cell.CELL_TYPE_NUMERIC){
						filedLength.setCellType(Cell.CELL_TYPE_STRING);
					}
					map = new HashMap<String,Object>();
					//表结构
					map.put("id", uploadService.generateId());
					map.put("tableName", tableName.getStringCellValue());
					map.put("filedName", filedName.getStringCellValue());
					map.put("filedAlias", filedAlias.getStringCellValue());
					map.put("filedEntityType", filedEntityType.getStringCellValue());
					map.put("filedType", filedType.getStringCellValue());
					map.put("filedLength", filedLength.getStringCellValue());
					datalist.add(map);
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return datalist;
}
}
*/