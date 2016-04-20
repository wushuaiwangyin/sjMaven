package com.krm.dbaudit.web.model.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.model.model.ModelSubject;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface ModelSubjectMapper extends Mapper<ModelSubject>{

	public List<ModelSubject> findPageInfo(Map<String, Object> params);


	public void updateBuzLineByRootId(Long id, String buzLine);

	public int deleteSubjectByRootId(Long id); 
	
	public ModelSubject findById(Long id);
}
