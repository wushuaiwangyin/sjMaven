package com.krm.dbaudit.web.upload.mapper;


import java.util.List;
import java.util.Map;

/**
* @author Parker
* 
*/
public interface UploadMapper {
	
	public Integer saveTable(List<Map<String, Object>> list);
	
	public Integer saveTableAlians(List<Map<String, Object>> list);
	
	public Long generateId();
	
}
