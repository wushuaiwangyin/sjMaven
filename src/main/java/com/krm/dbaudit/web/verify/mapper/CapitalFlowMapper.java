package com.krm.dbaudit.web.verify.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.verify.model.CapitalFlow;
import com.krm.dbaudit.web.verify.model.CapitalFlowDps;
import com.krm.dbaudit.web.verify.model.OutData;

public interface CapitalFlowMapper extends Mapper<CapitalFlow>{
	public String callProSerachFlow(Map<String,Object> maps);
	public List<CapitalFlow> listCapitalFlow(Map<String,Object> maps);
	public Integer getSearchFlowMaxStep(Map<String,Object> maps);
	public List<Map<String,Object>> getSearchFlowNodes(Map<String,Object> maps);
	public List<CapitalFlow> getDataByAcctno(Map<String, Object> params);
	public String callProSerachFlowNotAcctno(Map<String, Object> maps);
	public List<CapitalFlowDps> getDpsList(Map<String, Object> params1);
	public String callProSerachFlowDps(Map<String, Object> maps);
	public List<CapitalFlowDps> getDpsSearchFlowGroup(Map<String, Object> params2);
	public List<CapitalFlowDps> getDpsSearchFlowGroup2(Map<String, Object> params2);
}
