package com.krm.dbaudit.web.all360.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.krm.dbaudit.web.all360.model.TabColsDetailModel;

public class JsonTools {
/**
 * @param colsList 英文列名
 * @param headers 中文列名
 * @param data 数据集合
 * @return string
 */
public static String makeJsonStr(TabColsDetailModel tab,List<Map<String, Object>> data ){
	JSONObject jObject = new JSONObject();
	JSONArray jArray = (JSONArray) JSONArray.toJSON(data);
	jObject.put("cols", JSONObject.toJSON(tab.getColsMap()));
	jObject.put("list",jArray);
	jObject.put("total", data.size());
	return jObject.toString();
}
}
