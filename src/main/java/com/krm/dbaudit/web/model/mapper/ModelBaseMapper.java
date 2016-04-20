package com.krm.dbaudit.web.model.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.model.model.ModelBase;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface ModelBaseMapper extends Mapper<ModelBase>{

	public List<ModelBase> findPageInfo(Map<String, Object> params); 
	
	public List<ModelBase> findByIds(Map<String, Object> params); 
	
	public ModelBase findById(Long id);
	
	public int submismodels(Map<String, Object> params);
	
	public int checkmodelcode(Map<String, Object> params);
	
	public int checkmodelname(Map<String, Object> params);
}
