package com.krm.dbaudit.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.krm.dbaudit.web.sys.model.SysDict;
import com.krm.dbaudit.web.sys.service.SysDictService;

public class CreateColumnUtil {
	public static void main(String[] args) throws IOException {
		File  f = new File("d:/c.txt");
		FileReader reader = new FileReader(f);
		BufferedReader br = new BufferedReader(reader);
		 StringBuffer sb = new StringBuffer();
		 StringBuffer asb = new StringBuffer();
		 String lineTxt = null;
         while((lineTxt = br.readLine()) != null){
             sb.append(lineTxt).append(",");
             asb.append("c."+lineTxt).append(",");
         }
         br.close();
         
         System.out.println(sb.toString());
         System.out.println("+++++=================================");
         
         System.out.println(asb.toString());
	}
	
	/**
	 * 生成表头
	 * @param configs
	 * @param isCheck   是否带多选列
	 * @param operator  是否带操作列
	 * @return
	 */
	public static String createColumn(List<ColumnConfig> configs,boolean isCheck,boolean operator,Integer operatorWidth,String operatorMentod){
		if(operatorWidth == null){
			operatorWidth = 150;
		}
		if(StringUtils.isNotBlank("operatorMentod")){
			operatorMentod = "operatorBtn";
		}
		StringBuffer bf = new StringBuffer();
		if(isCheck){
			bf.append("<th data-field=\"state\" data-checkbox=\"true\"></th>");
		}
		for (ColumnConfig config : configs) {
			bf.append("<th ").append(" data-field=\"")
			.append(config.getCol()).append("\" data-sortable=\"true\" ");
			if(StringUtils.isNotBlank(config.getDic())){
				bf.append(" data-formatter='dicFormatter'");
			}
			//boostrap table有问题，不支持该属性
			//bf.append(" data-sort-name=\""+ColumnTransferUtil.trans(config.getCol()) +"\" ");
			bf.append(">" + config.getCnName()).append(" </th>");
		}
		if(operator){
			bf.append("<th data-field=\"id\" data-align=\"center\" data-sortable=\"false\" data-width=\""+operatorWidth+"\"  data-formatter=\""+operatorMentod+"\">操作</th>");
		}
		return bf.toString();
	}


	public static String createWidthQuery(List<ColumnConfig> list,SysDictService dicService) {
		
		StringBuffer bf = new StringBuffer("");
		
		int i = 0;
		for (ColumnConfig config : list) {
			if(i % 2 == 0){
				bf.append("<div class='row padding-tb-10'>");
			}
			if(StringUtils.isNotBlank(config.getDic())){//下接框
				bf.append("<div class='col-sm-6'>")
				  .append("     <label class='inline'>"+config.getCnName()+"</label>")
				  .append("    <select name='"+config.getCol()+"'  class='chosen-select tag-input-style' id='query-"+config.getCol()+"-select'>")
				  .append("      <option value=''>全部</option>");
				List<SysDict> dics = (List<SysDict>)dicService.findAllMultimap().get(config.getDic());
				for (SysDict dic : dics) {
					bf.append(" <option value='"+dic.getValue()+"'>"+dic.getLabel()+"</option>");
				}
				bf.append("    </select>")
				  .append("		<script type='text/javascript'>")
				  .append("			$('#query-"+config.getCol()+"-select').chosen({width: '80%',disable_search_threshold:10});")
				  .append("		</script>")
				  .append("</div>");
				
			}else if(StringUtils.isNotBlank(config.getColType()) && config.getColType().equals("date")){//日期用my97
				bf.append("<div class='col-sm-6'>");
				bf.append("   <label class='inline'>"+config.getCnName()+"</label>");
				bf.append("   <input type='text' name='"+config.getCol()+"Start' class='Wdate' onClick='WdatePicker()'>");
				bf.append("   <input type='text' name='"+config.getCol()+"End'   class=' Wdate' onClick='WdatePicker()'>");
				bf.append("</div>");
			}else{//普通输入框
				bf.append("<div class='col-sm-6'>");
				bf.append("   <label class='inline'>"+config.getCnName()+"</label>");
				bf.append("   <input type='text' name='"+config.getCol()+"' class='width-80'>");
				bf.append("</div>");
			}
			
			if(i % 2 != 0 || i+1 == list.size()){
				bf.append("</div>");
			}
		   i++;
		}
		bf.append("<div class='padding-trb-10 col-sm-12'>");
	    bf.append("	  <span id='queryAllBtn' class='btn  btn-primary btn-sm col-sm-6'>查 询 全 部</span>");
		bf.append("	  <span id='search-btn' class='btn btn-info btn-sm col-sm-6'>条 件 查 询</span>");
		bf.append("</div>");
		
	
		return bf.toString();
	}

	public static String createQuery(List<ColumnConfig> list,SysDictService dicService) {
		
		StringBuffer bf = new StringBuffer("<div class='form-inline' role='form'>");
		
		for (ColumnConfig config : list) {
			if(StringUtils.isNotBlank(config.getDic())){//下接框
				bf.append("<div class='form-group'>")
				  .append("     <span>"+config.getCnName()+"</span>")
				  .append("    <select name='"+config.getCol()+"'  class='chosen-select form-control w70' id='query-"+config.getCol()+"-select'>")
				  .append("      <option>全部</option>");
				List<SysDict> dics = (List<SysDict>)dicService.findAllMultimap().get(config.getDic());
				for (SysDict dic : dics) {
					bf.append(" <option value='"+dic.getValue()+"'>"+dic.getLabel()+"</option>");
				}
				bf.append("    </select>")
				  .append("		<script type='text/javascript'>")
				  .append("			$('#query-"+config.getCol()+"-select').chosen({width: '120px',disable_search_threshold:10});")
				  .append("		</script>")
				  .append("</div>");
				
			}else if(StringUtils.isNotBlank(config.getColType()) && config.getColType().equals("date")){//日期用my97
				bf.append("<div class='form-group'>")
				  .append("    <span>"+config.getCnName()+"</span>")
				  .append("    <input name='"+config.getCol()+"Start' class='form-control w70' type='text' onClick='WdatePicker()' class='Wdate'>")
				  .append("    -<input name='"+config.getCol()+"End' class='form-control w70' type='text' onClick='WdatePicker()' class='Wdate'>")
				  .append("</div>");
			}else{//普通输入框
				bf.append("<div class='form-group'>")
				  .append("    <span>"+config.getCnName()+"</span>")
				  .append("    <input name='"+config.getCol()+"' class='form-control w70' type='text' >")
				  .append("</div>");
			}
		}
		
		bf.append("<button id='queryBtn' type='button' class='btn btn-primary btn-sm'>查询</button>");
		bf.append("</div>");
		return bf.toString();
	}
	
}
