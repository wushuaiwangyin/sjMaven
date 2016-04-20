package com.krm.dbaudit.web.model.mapper;

import java.util.List;

import com.krm.dbaudit.web.model.model.ModelFieldDef;

public interface ModelDefFieldMapper {
	public List<ModelFieldDef> findFieldByModelId(Long modelid);
}
