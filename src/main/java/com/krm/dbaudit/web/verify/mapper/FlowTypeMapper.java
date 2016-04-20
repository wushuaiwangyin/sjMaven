package com.krm.dbaudit.web.verify.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.verify.model.FlowType;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface FlowTypeMapper extends Mapper<FlowType>{

	public List<FlowType> findPageInfo(Map<String, Object> params);


	public int deleteFlowTypeByRootId(Long id);

	public List<FlowType> findFlowTree(); 
}
