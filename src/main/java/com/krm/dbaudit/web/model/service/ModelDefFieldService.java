package com.krm.dbaudit.web.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.krm.dbaudit.web.model.mapper.ModelDefFieldMapper;
import com.krm.dbaudit.web.model.model.ModelFieldDef;

@Service("modelDefFieldService")
public class ModelDefFieldService {
	@Resource
	private ModelDefFieldMapper modelDefFieldMapper;
	
	public List<ModelFieldDef> findFieldByModelId(Long modelid){
		return modelDefFieldMapper.findFieldByModelId(modelid);
	}
}
