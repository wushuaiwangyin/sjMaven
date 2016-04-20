package com.krm.dbaudit.web.risk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil
{
	/**
	 * 获取昨天开始时间
	 * @return
	 */
	public static Date getYesterdayBegin(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 24:00:00"); 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date yesterday = null;
		try
		{
			yesterday = format.parse(format.format(calendar.getTime()));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return yesterday;
	}
	
	/**
	 * 获取昨天结束时间
	 * @return
	 */
	public static Date getYesterdayEnd(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 24:00:00"); 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 0);
		Date yesterday = null;
		try
		{
			yesterday = format.parse(format.format(calendar.getTime()));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return yesterday;
	}
	
	/**
	 * 获取上月第一天
	 * @return
	 */
	public static Date getFirsday(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 24:00:00"); 
		Calendar cal_1=Calendar.getInstance();
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH,1);
		Date firstDay = null;
		try
		{
			firstDay = format.parse((format.format(cal_1.getTime())));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return firstDay;
	}
	
	/**
	 * 获取当月第一天
	 * @return
	 */
	public static Date getLastday(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 24:00:00"); 
		Calendar cal_1=Calendar.getInstance();
		cal_1.add(Calendar.MONTH, 0);
		cal_1.set(Calendar.DAY_OF_MONTH,1);
		Date firstDay = null;
		try
		{
			firstDay = format.parse(format.format(cal_1.getTime()));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return firstDay;
	}
	
	/**
	 * 获取当月最后一天
	 * @return
	 */
	public static Date getThisMonthEnd(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 24:00:00"); 
		Calendar cal_1=Calendar.getInstance();
		cal_1.add(Calendar.MONTH, 1);
		cal_1.set(Calendar.DAY_OF_MONTH,0);
		Date firstDay = null;
		try
		{
			firstDay = format.parse(format.format(cal_1.getTime()));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return firstDay;
	}
	
	
	
}
