package com.krm.dbaudit.web.model.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.model.model.ModelRun;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface ModelRunMapper extends Mapper<ModelRun>{

	public List<ModelRun> findPageInfo(Map<String, Object> params); 
	
	public List<ModelRun> selectByModelId(long modelid);
}
