package com.krm.dbaudit.web.verify.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.verify.model.SubjectTable;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface SubjectTableMapper extends Mapper<SubjectTable>{

	public List<SubjectTable> findPageInfo(Map<String, Object> params); 
}
