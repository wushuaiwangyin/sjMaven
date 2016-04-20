package com.krm.dbaudit.common.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 函数操作功能根据
 * 解析函数支持，进行解析并转换，脚本验证
 */

public class FunctionUtils {
	
	public static String sourcestr = "";
	
	/**
	 * 解析函数，并进行函数转换
	 * @param script 公式脚本  
	 * @param rule 转换规则
	 * @param result 转换后的公式脚本
	 * @return 如果解析成功，则返回true，否则返回false
	 */
	public static boolean analyzeFun(String script,Map<String,Object> rule,String result){
		sourcestr = script;
		if(!checkBrackets(script))
			return false;
		
		splitFunStr(script);
		return true;
	}
	
	/**
	 * 分解公式，提取出函数字符串
	 * @param 公式
	 * @return 函数组
	 */
	private static String[] splitFunStr(String script){
		String regex = "[A-Za-z0-9]*?\\(([A-Za-z0-9,()']+)\\)";
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(script);
		int i=0;
		while(m.find()){
			i++;
		}
		
		String[] s=new String[i];
		m.reset();
		i=0;
		while(m.find()){
			s[i]=m.group();
			splitParams(s[i]);
			i++;
		}
		return s;
	}
	
	/**
	 * 验证括号是否匹配,简单验证，双向数量是否相等,以后再扩展,比较麻烦
	 * @param 公式
	 * @return 函数组
	 */
	private static boolean checkBrackets(String script){
		if(script.replace("(", "").length()==script.replace(")", "").length())
			return true;
		else
			return false;
	}
	
	/**
	 * 函数部分，分割参数
	 * @param 函数
	 * @return 分割后的参数数组
	 */
	private static String[] splitParams(String fun){
		String funname = fun.substring(0,fun.indexOf('(')-1);
		String s = fun.substring(fun.indexOf('(')+1,fun.length()-1);
		int n1 = 0;	//统计括号(
		int n2 = 0;	//统计单引号等
		//括号内、单引号内的逗号，不能作为分隔符。
		String tempparam = "";
		System.out.println("函数："+funname);
		for(char ch:s.toCharArray()){
			//System.out.println(ch);
			switch(ch){
				case '(':
					n1++;
					tempparam += ch;
					break;
				case '\'':
					n2 = n2>0 ? 0:1;
					tempparam += ch;
					break;
				case ')':
					n1--;
					tempparam += ch;
					break;
				case ',':	//分隔符
					if((n1>0)||(n2>0))	//括号内或单引号内的逗号
						tempparam += ch;
					else	//用于分割
					{
						System.out.println("参数:"+tempparam);
						tempparam  = "";
					}
					break;
				default:
					tempparam += ch;
					break;
			}	
		}
		return null;
	}
}
