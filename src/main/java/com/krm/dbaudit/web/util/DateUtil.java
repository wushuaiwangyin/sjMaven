package com.krm.dbaudit.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time pattern. Minutes should be mm not MM (MM is month).
 * @version $Revision: 1.3 $ $Date: 2007/08/17 01:01:10 $
 */
public class DateUtil {
	// ~ Static fields/initializers
	// =============================================
	private static Log log = LogFactory.getLog(DateUtil.class);
	private static String defaultDatePattern = null;
	private static String timePattern = "yyyy-MM-dd HH:mm:ss";
	private static String timePatternString = "yyyyMMddHHmmss";
	private static String timePatternEnd = "yyyy-MM-dd 23:59:59";
	private static String timeYYPattern= "yy";
	private static String timeYYYYPattern= "yyyy";
	private static String timePatternHmsString = "ddHHmmss";
	private static String timePatternCHN="yyyy年MM月dd日";
	private static String timePatternReportDate = "yyyyMM";

	// ~ Methods
	// ================================================================
	/**
	 * Return default datePattern (yyyy-MM-dd)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static synchronized String getDatePattern() {
		defaultDatePattern = "yyyy-MM-dd";
		return defaultDatePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date to yyyy-MM-dd.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}
	
	/**
	 * This method attempts to convert an Oracle-formatted date to yyyy年MM月dd日
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDateCHN(Date aDate){
		SimpleDateFormat df = null;
		String returnValue = "";
		if(aDate != null){
			df = new SimpleDateFormat(timePatternCHN);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}
	
	/**
	 * This method attempts to convert an Oracle-formatted date to yyyy年MM月dd日
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDateReportDate(Date aDate){
		SimpleDateFormat df = null;
		String returnValue = "";
		if(aDate != null){
			df = new SimpleDateFormat(timePatternReportDate);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}
		try {
			date = df.parse(strDate);
		}
		catch (ParseException pe) {
			log.error("ParseException: " + pe);
		}
		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	public static String getTimeEndNow(Date theTime) {
		return getDateTime(timePatternEnd, theTime);
	}

	public static String getTimeNowString(Date theTime) {
		return getDateTime(timePatternString, theTime);
	}
	
	public static String getTimeYYNow(Date theTime) {
		return getDateTime(timeYYPattern, theTime);
	}
	
	public static String getTimeYYYYNow(Date theTime) {
		return getDateTime(timeYYYYPattern, theTime);
	}
	
	
	public static String getTimeHmsNow(Date theTime) {
		return getDateTime(timePatternHmsString, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate == null) {
			log.error("aDate is null!");
		}
		else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}
	
	/**
	 * Return default datePattern (yyyy-MM-dd HH:mm:ss)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static final String convertTimeToString(Date aDate) {
		return getDateTime("yyyy-MM-dd HH:mm:ss", aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate){
		Date aDate = null;
		if (log.isDebugEnabled()) {
			log.debug("converting date with pattern: " + getDatePattern());
		}
		aDate = convertStringToDate(getDatePattern(), strDate);
		return aDate;
	}

	/**
	 * @return
	 */
	public static Date getTodayFrom() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * @return
	 */
	public static Date getTodayTo() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	
	public static String getTodayForWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayForWeek = 0;
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		}
		if(dayForWeek == 1)
			return "星期一";
		else if(dayForWeek == 2)
			return "星期二";
		else if(dayForWeek == 3)
			return "星期三";
		else if(dayForWeek == 4)
			return "星期四";
		else if(dayForWeek == 5)
			return "星期五";
		else if(dayForWeek == 6)
			return "星期六";
		else
			return "星期";
	}

	/**
	 *format yyyy-MM-dd为yyyyMM00
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String formatDate(String strDate) {
		if (strDate.length() <= 8) {
			return strDate;
		}
		String s = strDate.replaceAll("-", "");
		String returnValue = s.substring(0, 6) + "00";
		return (returnValue);
	}

	/**
	 * format yyyyMMdd为yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String formatDate2(String strDate) {
		String returnValue = strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate.substring(6, 8);
		return (returnValue);
	}
	public static List<Integer> getElevenYear(){
		List<Integer> result=new ArrayList<Integer>();
		String year=getTimeYYYYNow(new Date());
		int y=Integer.parseInt(year);
		for(int i=y-5;i<=y+5;i++){
			result.add(i);
		}
		return result;
	}
	
	/**
	 * 获得本年及本年之后十年的年份
	 * @return
	 */
	public static List<Integer> getAfterTenYear(){
		List<Integer> result=new ArrayList<Integer>();
		String year=getTimeYYYYNow(new Date());
		int y=Integer.parseInt(year);
		for(int i=y;i<=y+10;i++){
			result.add(i);
		}
		return result;
	}
	

	public static List<Integer> getLastTenYear(){
		List<Integer> result=new ArrayList<Integer>();
		String year=getTimeYYYYNow(new Date());
		int y=Integer.parseInt(year);
		for(int i=y-10;i<=y;i++){
			result.add(i);
		}
		return result;
	}
	
	/**
	 * 返回指定日期的前N天或后N天
	 * @param dateStr
	 * @param addDay
	 * @return
	 */
	public static String dateAdd(String dateStr,int addDay){
		String dateReturn = "";
		if(!TypeChecker.isEmpty(dateStr)&&!TypeChecker.isEmpty(addDay)){
			Calendar cal = Calendar.getInstance();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.setTime(date);
	        cal.add(Calendar.DATE,addDay);
	        Date yestoday = cal.getTime();
			dateReturn = DateUtil.getDateTime("yyyy-MM-dd",yestoday);
		}
		return dateReturn;
	}
}
