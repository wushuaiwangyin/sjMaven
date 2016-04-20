package com.krm.dbaudit.web.modeltool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.krm.dbaudit.web.modeltool.mapper.ToolModelMapper;

@Service("toolModelService")
public class ToolModelService {
	@Resource
	private ToolModelMapper toolModelMapper;
	
	public boolean saveModel(Map<String, Object> params){
		try{
			String tablename = params.get("tablename").toString();
			String modelid = toolModelMapper.intiModelId();
			params.put("modelid", modelid);		
			String s = toolModelMapper.getToolFlowTree(tablename);
			List list=new ArrayList();
	        StringTokenizer line=new StringTokenizer(s,";");
	        while(line.hasMoreTokens()){
	        	List<String> itemlist=new ArrayList<String>();
	        	StringTokenizer item=new StringTokenizer(line.nextToken(),",");
	        	while(item.hasMoreTokens()){
	        		itemlist.add(item.nextToken());
	        	}
	        	if(!itemlist.get(0).equals("0"))
	        	{
	        		String flowtable = itemlist.get(1).toString();
	        		params.put("tablename", flowtable);
	        		toolModelMapper.deleteModelTableFlow(params);
	        		toolModelMapper.insertModelTableFlow(params);
	        	}
	        }
	        toolModelMapper.InsertModelInfo(params);
	        toolModelMapper.relationModelField(modelid);
		}catch(Exception e){
			return false;
		}
		return true;
	}
}
