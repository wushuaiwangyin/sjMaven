package com.krm.dbaudit.web.model.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.model.model.ModelProperty;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface ModelPropertyMapper extends Mapper<ModelProperty>{

	public List<ModelProperty> findPageInfo(Map<String, Object> params); 
}
