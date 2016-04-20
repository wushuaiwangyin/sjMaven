package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.verify.mapper.OutDataMapper;
import com.krm.dbaudit.web.verify.model.OutData;

@Service("outDataService")
public class OutDataService extends ServiceMybatis<OutData> {
	@Resource
	private OutDataMapper outDataMapper;
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<OutData> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<OutData> list = outDataMapper.listOutData(params);
		return new PageInfo<OutData>(list);
	}
	
	public void insertBatch(List<Map<String, Object>> outdatas){
		outDataMapper.insertBatch(outdatas);
	}
	
	public void insertOutData(Map<String, Object> map){
		outDataMapper.insertOutData(map);
	}

	public List<OutData> listOutData(Map<String, Object> params) {
		return outDataMapper.listOutData(params);
	}
}
