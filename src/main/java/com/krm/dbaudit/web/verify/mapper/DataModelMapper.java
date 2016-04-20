package com.krm.dbaudit.web.verify.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.verify.model.DataModel;

import java.util.List;
import java.util.Map;

/**
* @author taosq on 2015-08-13
*/
public interface DataModelMapper extends Mapper<DataModel>{

	public List<DataModel> findPageInfo(Map<String, Object> params); 
}
