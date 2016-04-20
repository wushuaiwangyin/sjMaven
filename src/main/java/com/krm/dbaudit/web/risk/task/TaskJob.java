package com.krm.dbaudit.web.risk.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.krm.dbaudit.web.risk.service.SecondRiskService;

/**
 * 二次分析模型扫描任务
 * @author Parker
 *
 */
@Component
public class TaskJob
{
	@Resource
	private SecondRiskService secondRiskService;
	
	@Scheduled(cron="0 0 0 * * ?")    
    public void run() {  
		//获取客户号
		List<Map<String, Object>> list = secondRiskService.scan();
		List<Map<String, Object>> secondRiskModelList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : list)
		{
			//先判断客户号是否存在，存在就进入操作
			Map<String, Object> customer = secondRiskService.findCustomer(map.get("custno").toString().trim());
			if(customer != null && customer.size() > 0){
				secondRiskModelList = secondRiskService.getSecondRiskModel(map.get("custno").toString().trim());
				if(secondRiskModelList != null){
					secondRiskService.save(secondRiskModelList);
				}
			}
		}
    }  
}
