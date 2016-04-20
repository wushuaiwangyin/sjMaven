package com.krm.dbaudit.web.model.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.model.model.ModelApprove;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface ModelApproveMapper extends Mapper<ModelApprove>{

	public List<ModelApprove> findPageInfo(Map<String, Object> params); 
}
