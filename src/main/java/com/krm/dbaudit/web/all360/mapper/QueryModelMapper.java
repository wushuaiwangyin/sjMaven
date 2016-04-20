package com.krm.dbaudit.web.all360.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.all360.model.QueryModel;

public interface QueryModelMapper extends Mapper<QueryModel> {
public List<QueryModel>  findPageInfo (Map<String, Object> params);

public void saveQueryModel(QueryModel qm);
}
