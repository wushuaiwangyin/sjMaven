package com.krm.dbaudit.common.beetl.function;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.krm.dbaudit.web.risk.service.RiskDetectionService;
import com.krm.dbaudit.web.risk.util.TimeUtil;

@Component
public class RiskFunctions
{
	@Resource
	private RiskDetectionService riskDetectionService;
	
	/**
	 * 首页跑批模型数量列表
	 */
	public List<Map<String,Object>>  findRunAmount(Integer flag)
	{
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("flag", flag);
		params.put("status", 8);
		List<Map<String,Object>> list = riskDetectionService.findRunAmount(params);
		Integer total = 0;
		Map<String,Object> account = new HashMap<String, Object>();
		for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();)
		{
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			if(map.entrySet().size() <2)
			{
				total =  Integer.parseInt(map.get("value").toString());
				iterator.remove();
			}
		}
		account.put("total", total);
		list.add(account);
		return list;
	}
}
