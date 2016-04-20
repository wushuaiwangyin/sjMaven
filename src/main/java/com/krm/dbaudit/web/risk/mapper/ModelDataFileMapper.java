package com.krm.dbaudit.web.risk.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.risk.model.ModelDataFile;

public interface ModelDataFileMapper extends Mapper<ModelDataFile>
{
	public int saveFile(ModelDataFile modelDataFile);
	
	public Long generateId();
}
