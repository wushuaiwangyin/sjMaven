package com.krm.dbaudit.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.sys.model.KbStdEntry;

public interface KbStdEntryMapper extends Mapper<KbStdEntry>
{

	List<KbStdEntry> findPageInfo(Map<String, Object> params); 

}
