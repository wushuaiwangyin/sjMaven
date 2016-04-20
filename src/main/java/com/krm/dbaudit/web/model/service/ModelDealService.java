package com.krm.dbaudit.web.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.github.abel533.sql.SqlMapper;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.model.mapper.ModelDealMapper;
import com.krm.dbaudit.web.model.model.ModelDeal;

@Service("modelDealService")
public class ModelDealService extends ServiceMybatis<ModelDeal>{
	
	@Resource
	private ModelDealMapper modelDealMapper;
	
	public List<ModelDeal> findModelDealInfo(long model_id){
		return modelDealMapper.findModelDealInfo(model_id);
	}
	
	public int insertModelDealLog(Map<String, Object> params){
		return modelDealMapper.insertModelDealLog(params);
	}
	
	public int updateModelDeal(Map<String, Object> params){
		return modelDealMapper.updateModelDeal(params);
	}
}
