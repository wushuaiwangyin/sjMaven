package com.krm.dbaudit.web.model.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.model.model.ModelDeal;

public interface ModelDealMapper extends Mapper<ModelDeal>{
	public List<ModelDeal> findModelDealInfo(long model_id); 
	public int insertModelDealLog(Map<String, Object> params);
	public int updateModelDeal(Map<String, Object> params);
}
