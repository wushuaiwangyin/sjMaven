package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.verify.mapper.CusGuarFlowMapper;
import com.krm.dbaudit.web.verify.model.CapitalFlow;
import com.krm.dbaudit.web.verify.model.CapitalFlowDps;
import com.krm.dbaudit.web.verify.model.CusGuarFlow;
import com.krm.dbaudit.web.verify.model.CusGuarFlowLoan;

@Service("cusGuarFlowService")
public class CusGuarFlowService extends ServiceMybatis<CusGuarFlow>{
	@Resource
	private CusGuarFlowMapper cusGuarFlowMapper;
	
	public String callProSerachFlow(Map<String, Object> maps) {
		String searchId = null;
		cusGuarFlowMapper.callProSerachFlow(maps);
		return searchId;
	}

	public Integer getSearchFlowMaxStep(Map<String, Object> maps) {
		return cusGuarFlowMapper.getSearchFlowMaxStep(maps);
	}

	public List<CusGuarFlowLoan> getDpsSearchFlowGroup2(Map<String, Object> maps) {
		return cusGuarFlowMapper.getDpsSearchFlowGroup2(maps);
	}

	public List<CusGuarFlow> listCusGuarFlow(Map<String, Object> sm) {
		return cusGuarFlowMapper.listCusGuarFlow(sm);
	}

	public List getSearchFlowNodes(Map<String, Object> sm) {
		return cusGuarFlowMapper.getSearchFlowNodes(sm);
	}

	public PageInfo<CusGuarFlow> getDataByGuarNo(Map<String, Object> map) {
		PageHelper.startPage(map);
		List<CusGuarFlow> list = cusGuarFlowMapper.getDataByGuarNo(map);
		return new PageInfo<CusGuarFlow>(list);
	}

	public String callProSerachFlowNotCusId(Map<String, Object> maps) {
		String searchId = null;
		cusGuarFlowMapper.callProSerachFlowNotCusId(maps);
		return searchId;
	}

	public List<CusGuarFlowLoan> getGuarList(Map<String, Object> params1) {
		return cusGuarFlowMapper.getGuarList(params1);
	}

	public String callProSerachFlowGuar(Map<String, Object> map) {
		String searchId = null;
		cusGuarFlowMapper.callProSerachFlowGuar(map);
		return searchId;
	}

	public List<CusGuarFlowLoan> getGuarSearchFlowGroup(Map<String, Object> params2) {
		return cusGuarFlowMapper.getGuarSearchFlowGroup(params2);
	}

}
