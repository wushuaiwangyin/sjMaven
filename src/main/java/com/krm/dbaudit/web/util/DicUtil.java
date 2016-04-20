package com.krm.dbaudit.web.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.krm.dbaudit.web.sys.model.SysDict;


/**
 * 
 * @author taosq
 * 解决现行文件数据字典可以动态查询
 */
public class DicUtil {
	
	/**
	 * 为了解决每一个现在行文件对应的同个字段使用不同的数据字典，因为key必须为字段+类型
	 * @param configs
	 * @param dics
	 * @return
	 */
	public static Map<String,List<SysDict>> formatter(List<ColumnConfig> configs,List<SysDict> dics){
		//为了提高查询效率，先将dics转成map结构
		Multimap<String, SysDict> multimap = ArrayListMultimap.create();
		for(SysDict dict : dics){
			multimap.put(dict.getType(), dict);
		}
		
		Map<String,List<SysDict>> map = new HashMap<String, List<SysDict>>();
		for (ColumnConfig c : configs) {
			if(multimap.containsKey(c.getDic())){
				map.put(c.getCol()+ "_" + c.getType(), (List<SysDict>)multimap.get(c.getDic()));
			}
		}
		
		return map;
	}
	
}
