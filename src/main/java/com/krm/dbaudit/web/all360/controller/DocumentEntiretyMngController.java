package com.krm.dbaudit.web.all360.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.krm.dbaudit.web.util.ResponseUtils;
@Controller
@RequestMapping("cust360/doc/mang")
public class DocumentEntiretyMngController {
	@RequestMapping(value = "enter", method = RequestMethod.GET)
	public String enterMain(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model) {
		return "verify/360/sjdocument/test";
	}
	@RequestMapping(value = "getData", method = RequestMethod.POST)
	public void getData(@RequestParam Map<String, Object> params,
			HttpServletResponse response, Model model){
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> map=null;
		for(int i=1;i<11;i++){
			map=new HashMap<String, Object>();
			map.put("id", i);
			map.put("name", i+"sasa");
			map.put("price", "$100");
			list.add(map);
		}
		JSONObject json=new JSONObject();
		JSONArray jsonArray=(JSONArray) JSONArray.toJSON(list);
		json.put("total", list.size());
		json.put("rows", jsonArray);
		ResponseUtils.renderJson(response,json);
	}
	
}
