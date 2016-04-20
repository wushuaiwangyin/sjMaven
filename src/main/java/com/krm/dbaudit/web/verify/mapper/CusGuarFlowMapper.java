package com.krm.dbaudit.web.verify.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.verify.model.CapitalFlowDps;
import com.krm.dbaudit.web.verify.model.CusGuarFlow;
import com.krm.dbaudit.web.verify.model.CusGuarFlowLoan;

public interface CusGuarFlowMapper extends Mapper<CusGuarFlow>{

	public String callProSerachFlow(Map<String, Object> maps);

	public Integer getSearchFlowMaxStep(Map<String, Object> maps);

	public List<CusGuarFlowLoan> getDpsSearchFlowGroup2(Map<String, Object> maps);

	public List<CusGuarFlow> listCusGuarFlow(Map<String, Object> sm);

	public List getSearchFlowNodes(Map<String, Object> sm);

	public List<CusGuarFlow> getDataByGuarNo(Map<String, Object> map);

	public String callProSerachFlowNotCusId(Map<String, Object> maps);

	public List<CusGuarFlowLoan> getGuarList(Map<String, Object> params1);

	public String callProSerachFlowGuar(Map<String, Object> map);

	public List<CusGuarFlowLoan> getGuarSearchFlowGroup(Map<String, Object> params2);

}
