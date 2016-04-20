package com.krm.dbaudit.web.api.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.api.model.SysApi;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface SysApiMapper extends Mapper<SysApi>{

	public List<SysApi> findPageInfo(Map<String, Object> params); 
}
