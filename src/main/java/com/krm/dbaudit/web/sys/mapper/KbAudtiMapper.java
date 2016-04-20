package com.krm.dbaudit.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.sys.model.KbAudit;

public interface KbAudtiMapper extends Mapper<KbAudit>
{
	public List<KbAudit> findPageInfo(Map<String, Object> params); 
	
	public KbAudit findById(Long id);
}
