package com.krm.dbaudit.web.util;

import java.util.List;

import com.krm.dbaudit.common.utils.StringConvert;

public class CreateQueryUtil {
	
	public static String createQuery(String preffix,List<ColumnConfig> configs){
		StringBuffer sb = new StringBuffer();
		for (ColumnConfig c : configs) {
			if(c.getColType().equals("date")){
				sb.append("<if test=\"@Ognl@isNotBlank("+c.getCol()+"Start)\">").append("\n");
				sb.append("    and "+preffix+StringConvert.camelhumpToUnderline(c.getCol())+" &gt;=  #{"+c.getCol()+"Start}").append("\n");
				sb.append("</if>").append("\n");
				sb.append("<if test=\"@Ognl@isNotBlank("+c.getCol()+"End)\">").append("\n");
				sb.append("    and "+preffix+StringConvert.camelhumpToUnderline(c.getCol())+" &lt;=  #{"+c.getCol()+"End}").append("\n");
				sb.append("</if>").append("\n");
			}else if(c.getColType().equals("int") || c.getColType().equals("long")){
				sb.append("<if test=\"@Ognl@isNotBlank("+c.getCol()+")\">").append("\n");
				sb.append("    and "+preffix+StringConvert.camelhumpToUnderline(c.getCol())+" =  #{"+c.getCol()+"}").append("\n");
				sb.append("</if>").append("\n");
			}else{
				sb.append("<if test=\"@Ognl@isNotBlank("+c.getCol()+")\">").append("\n");
				sb.append("    and "+preffix+StringConvert.camelhumpToUnderline(c.getCol())+" like CONCAT( '%' , #{"+c.getCol()+"}, '%') ").append("\n");
				sb.append("</if>").append("\n");
			}
		}
		return sb.toString();
	}

}
