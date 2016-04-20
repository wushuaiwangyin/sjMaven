package com.krm.dbaudit.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.sys.model.KbProfit;

public interface KbProfitMapper extends Mapper<KbProfit>
{
	public List<KbProfit> findPageInfo(Map<String, Object> params); 
	
	public KbProfit findById(Long id);
}
