package com.krm.dbaudit.web.model.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.model.model.AuditModel;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface AuditModelMapper extends Mapper<AuditModel>{

	public List<AuditModel> findPageInfo(Map<String, Object> params);

	public void deleteModelByRootTypeId(Long id);

	public List<AuditModel> findAuditModel(Map<String, Object> params); 
}
