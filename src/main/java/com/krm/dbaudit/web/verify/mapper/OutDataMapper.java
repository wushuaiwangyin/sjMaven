package com.krm.dbaudit.web.verify.mapper;

import java.util.List;
import java.util.Map;


import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.verify.model.OutData;

public interface OutDataMapper extends Mapper<OutData> {
	public List<OutData> listOutData(Map<String, Object> params);
	public void insertBatch(List<Map<String, Object>> outdatas);
	public void insertOutData(Map<String, Object> map);
}
