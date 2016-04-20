package com.krm.dbaudit.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhangyu
 * 用于合并list中重复的元素
 */
public class ListUtil
{
	/**
	 * 合并相同的Map
	 * @param list
	 * @return
	 */
	public static List<Map<String,Object>> mergeList(List<Map<String,Object>> list){
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			String  key= (String) map.get("key");
			Integer value1 = Integer.parseInt(map.get("value1").toString());
			Integer value2 = Integer.parseInt(map.get("value2").toString());
			Integer sort = Integer.parseInt(map.get("sort").toString());
			Map<String,Object> newMap=isExistSame(i,key,value1,value2,sort,list);
			if(null==newMap){
				retList.add(map);
			}
			else{
				list.remove(map.get(i));
				retList.add(newMap);
			}
		}
		return retList;
	}
	
	private static Map<String,Object> isExistSame(int i,String key,Integer value1,Integer value2,Integer sort, List<Map<String,Object>> list){
		Map<String,Object> newMap = null;
		for(int j=i+1;j<list.size();j++){
			Map<String,Object> innerMap = list.get(j);
			String  key_1= (String) innerMap.get("key");
			Integer value_1 = Integer.parseInt(innerMap.get("value1").toString());
			Integer value_2 = Integer.parseInt(innerMap.get("value2").toString());
			if(key_1.equals(key)){
				newMap = new HashMap<String,Object>();
				newMap.put("key", key);
				newMap.put("value1",value1+value_1);
				newMap.put("value2",value2+value_2);
				newMap.put("sort",sort);
				list.remove(j);
			}
		}
		return newMap;
	}
}
