package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.verify.mapper.CapitalFlowMapper;
import com.krm.dbaudit.web.verify.model.CapitalFlow;
import com.krm.dbaudit.web.verify.model.CapitalFlowDps;
import com.krm.dbaudit.web.verify.model.OutData;

@Service("capitalFlowService")
public class CapitalFlowService extends ServiceMybatis<CapitalFlow> {
	@Resource
	private CapitalFlowMapper capitalFlowMapper;
	
	public String callProSerachFlow(Map<String,Object> maps){
		String searchId = null;
		 capitalFlowMapper.callProSerachFlow(maps);
		return searchId;
	}
	
	public List<CapitalFlow> listCapitalFlow(Map<String,Object> maps){
		return capitalFlowMapper.listCapitalFlow(maps);
	}
	
	public Integer getSearchFlowMaxStep(Map<String,Object> maps){
		return capitalFlowMapper.getSearchFlowMaxStep(maps);
	}
	public List<Map<String,Object>> getSearchFlowNodes(Map<String,Object> maps){
		return capitalFlowMapper.getSearchFlowNodes(maps);
	}

	public PageInfo<CapitalFlow> getDataByAcctno(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<CapitalFlow> list = capitalFlowMapper.getDataByAcctno(params);
		return new PageInfo<CapitalFlow>(list);
	}

	public String callProSerachFlowNotAcctno(Map<String, Object> maps) {
		String searchId = null;
		capitalFlowMapper.callProSerachFlowNotAcctno(maps);
		return searchId;
	}

	public List<CapitalFlowDps> getDpsList(Map<String, Object> params1) {
		return capitalFlowMapper.getDpsList(params1);
	}

	public String callProSerachFlowDps(Map<String, Object> maps) {
		String searchId = null;
		capitalFlowMapper.callProSerachFlowDps(maps);
		return searchId;
	}

	public List<CapitalFlowDps> getDpsSearchFlowGroup(Map<String, Object> params2) {
		return capitalFlowMapper.getDpsSearchFlowGroup(params2);
	}

	public List<CapitalFlowDps> getDpsSearchFlowGroup2(Map<String, Object> params2) {
		return capitalFlowMapper.getDpsSearchFlowGroup2(params2);
	}
	
	
}
