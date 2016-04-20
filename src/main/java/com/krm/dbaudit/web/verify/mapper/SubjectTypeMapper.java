package com.krm.dbaudit.web.verify.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.verify.model.SubjectType;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface SubjectTypeMapper extends Mapper<SubjectType>{

	public List<SubjectType> findPageInfo(Map<String, Object> params); 
}
