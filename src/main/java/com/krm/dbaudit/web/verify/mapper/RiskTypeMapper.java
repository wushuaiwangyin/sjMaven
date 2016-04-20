package com.krm.dbaudit.web.verify.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.verify.model.RiskType;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface RiskTypeMapper extends Mapper<RiskType>{

	public List<RiskType> findPageInfo(Map<String, Object> params); 
}
