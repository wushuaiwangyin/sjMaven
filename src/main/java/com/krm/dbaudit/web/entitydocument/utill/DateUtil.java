package com.krm.dbaudit.web.entitydocument.utill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DateUtil {
/**计算起始日期与结束日期之间的月份差值（闭区间）所以+1
 * @param date1 起始日期
 * @param date2 结束日期
 * @param pattern 日期格式
 * @return 
 * @throws ParseException 
 */
public static int countMonth(String date1,String date2,String pattern) throws ParseException {
	SimpleDateFormat sdf=new SimpleDateFormat(pattern);
	Calendar c1=Calendar.getInstance();
	Calendar c2=Calendar.getInstance();
	c1.setTime(sdf.parse(date1));
	c2.setTime(sdf.parse(date2));
	int year=c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);//结束日期年份-起始日期年份
	if(year<0){//开始日期小于结束日期
		year=-year;
		return year*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH)+1;//闭区间所以加1
	}
	return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH)+1;//闭区间所以加1
}

/**获取日期的年份
 * @param date1
 * @param pattern
 * @return
 * @throws ParseException
 */
public static int getYear(String date1,String pattern) throws ParseException {
	SimpleDateFormat sdf=new SimpleDateFormat(pattern);
	Calendar c1=Calendar.getInstance();
	c1.setTime(sdf.parse(date1));
	return  c1.get(Calendar.YEAR);
}

/**计算跨年的那个月份按数轴位置组成的数组排列所在的下标位置
 * 
 * 	0	1	2	3	4	5	6	7	8	9	10	11	12
	1	2	3	4	5	6	7	8	9	10	11	12	1
	4	5	6	7	8	9	10	11	12	1	2	3	4
 * @param date1,date2
 * @param pattern
 * @return
 * @throws ParseException 
 */
public static int countAcrossYear(String date1,String date2,String pattern) throws ParseException {
	SimpleDateFormat sdf=new SimpleDateFormat(pattern);
	Calendar c1=Calendar.getInstance();
	Calendar c2=Calendar.getInstance();
	c1.setTime(sdf.parse(date1));
	c2.setTime(sdf.parse(date2));
	int startMonth=c1.get(Calendar.MONTH);//起始月份
	int endMonth=c2.get(Calendar.MONTH);
	int countMonth=0;
	int acrossMonth=0;//跨年的那个月份按数轴位置所在的下标位置
	int year=c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);//结束日期年份-起始日期年份
	if(year<0){//开始日期小于结束日期
		year=-year;
		countMonth= year*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
	}else{
		 countMonth=year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);	
	}
	
	if(year==0){
		acrossMonth=0;
	}else{
		acrossMonth=12-startMonth+1;//12个月份-当前月份+1即为下个月份的脚标
	}
	return acrossMonth;
}

public static int getMonth(String date1,String pattern) throws ParseException {
	SimpleDateFormat sdf=new SimpleDateFormat(pattern);
	Calendar c1=Calendar.getInstance();
	c1.setTime(sdf.parse(date1));
	return  c1.get(Calendar.MONTH)+1;
}

public static String concatMonthStr(int month) {
	if(month<10){
		return "0"+String.valueOf(month);
	}else{
		return String.valueOf(month);
	}
}
public static void main(String[] args) {
	String start="20151001";
	String end="20161004";
	String pattern="yyyyMMdd";
	try {
		System.out.println(countMonth(start, end, pattern));
		System.out.println(getYear(start, pattern));
		System.out.println(countAcrossYear(start, end, pattern));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
