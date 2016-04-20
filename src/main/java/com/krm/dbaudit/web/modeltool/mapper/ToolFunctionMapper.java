package com.krm.dbaudit.web.modeltool.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.modeltool.model.ToolFunction;
import com.krm.dbaudit.web.modeltool.model.ToolFunctionParam;

public interface ToolFunctionMapper extends Mapper<ToolFunction>{
	public List<ToolFunction> listFunction();
	public String checkFunction(Map<String, Object> params);
	public ToolFunction getFunInfoById(Map<String, Object> params);
	public List<ToolFunctionParam> getFunctionParams(Map<String, Object> params);
}
