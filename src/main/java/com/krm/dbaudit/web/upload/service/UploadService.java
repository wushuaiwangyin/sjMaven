package com.krm.dbaudit.web.upload.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.krm.dbaudit.web.upload.mapper.UploadMapper;

/**
* @author Parker
* 
*/

@Service("uploadService")
public class UploadService {
	@Resource
	private UploadMapper uploadMapper;

	
	
	public Integer saveTable(List<Map<String, Object>> list){
		return uploadMapper.saveTable(list);
	}
	
	public Integer saveTableAlians(List<Map<String, Object>> list){
		return uploadMapper.saveTableAlians(list);
	}
	
	public Long generateId(){
		return uploadMapper.generateId();
	}
}
