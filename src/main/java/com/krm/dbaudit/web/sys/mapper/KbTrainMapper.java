package com.krm.dbaudit.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.sys.model.KbTrain;

public interface KbTrainMapper extends Mapper<KbTrain>
{
	public List<KbTrain> findPageInfo(Map<String, Object> params); 
	
	public KbTrain findById(Long id);
}
