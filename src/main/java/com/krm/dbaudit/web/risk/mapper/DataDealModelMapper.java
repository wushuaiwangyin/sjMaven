package com.krm.dbaudit.web.risk.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.risk.model.DataDealModel;

public interface DataDealModelMapper extends Mapper<DataDealModel>
{
	public int fastDeal(DataDealModel dataDealModel);
	
	public DataDealModel findBydataId(Integer dataId);
	
	public DataDealModel findById(Integer id);
	
	public DataDealModel checkIsDealed(Integer dataId);

	public List<DataDealModel> findPageInfo(Map<String, Object> params);

	public Long generateId();
	
}
