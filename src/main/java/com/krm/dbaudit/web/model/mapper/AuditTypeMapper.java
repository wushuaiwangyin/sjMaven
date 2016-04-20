package com.krm.dbaudit.web.model.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.model.model.AuditType;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface AuditTypeMapper extends Mapper<AuditType>{

	public List<AuditType> findPageInfo(Map<String, Object> params);

	public int deleteAuditTypeByRootId(Long id);
	
	public int deleteAuditModelByType(Long type);

	public AuditType findById(Long id);

	public int batchInsert(List<Map<String, Object>> list); 
}
