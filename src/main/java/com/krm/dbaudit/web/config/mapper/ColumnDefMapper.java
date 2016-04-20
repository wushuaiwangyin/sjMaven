package com.krm.dbaudit.web.config.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.config.model.ColumnDef;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface ColumnDefMapper extends Mapper<ColumnDef>{

	public List<ColumnDef> findPageInfo(Map<String, Object> params); 
}
