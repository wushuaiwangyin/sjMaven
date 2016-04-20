package com.krm.dbaudit.web.sys.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.sys.model.SysAccessory;

public interface SysAccessoryMapper extends Mapper<SysAccessory>
{
	public SysAccessory findById(Long id);
}
