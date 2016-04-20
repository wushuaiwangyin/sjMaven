package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.verify.model.RiskType;
import com.krm.dbaudit.web.verify.mapper.RiskTypeMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("riskTypeService")
public class RiskTypeService extends ServiceMybatis<RiskType>{

	@Resource
	private RiskTypeMapper riskTypeMapper;

	
	public int saveRiskType(RiskType riskType){
		int count = 0;
		if(riskType.getId() == null){
			count = this.insertSelective(riskType);
		}else{
			count = this.updateByPrimaryKeySelective(riskType);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteRiskType(Long id){
		return this.updateDelFlagToDelStatusById(RiskType.class, id);
	}
	
	
	public Integer deleteRiskType(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteRiskType(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<RiskType> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<RiskType> list = riskTypeMapper.findPageInfo(params);
		return new PageInfo<RiskType>(list);
	}
	
}
