package com.krm.dbaudit.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.sys.model.KbStd;

public interface KbStdMapper extends Mapper<KbStd>
{
	public List<KbStd> findPageInfo(Map<String, Object> params);
	
	public KbStd findById(Long id);
	
	public int save(Map<String, Object> params);
}
