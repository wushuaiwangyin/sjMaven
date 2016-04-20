package com.krm.dbaudit.web.model.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.model.model.ModelResult;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface ModelResultMapper extends Mapper<ModelResult>{

	public List<ModelResult> findPageInfo(Map<String, Object> params); 
}
