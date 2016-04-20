package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.verify.model.RiskModel;
import com.krm.dbaudit.web.verify.mapper.RiskModelMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("riskModelService")
public class RiskModelService extends ServiceMybatis<RiskModel>{

	@Resource
	private RiskModelMapper riskModelMapper;

	
	public int saveRiskModel(RiskModel riskModel){
		int count = 0;
		if(riskModel.getId() == null){
			count = this.insertSelective(riskModel);
		}else{
			count = this.updateByPrimaryKeySelective(riskModel);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteRiskModel(Long id){
		return this.updateDelFlagToDelStatusById(RiskModel.class, id);
	}
	
	
	public Integer deleteRiskModel(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteRiskModel(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<RiskModel> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<RiskModel> list = riskModelMapper.findPageInfo(params);
		return new PageInfo<RiskModel>(list);
	}
	
}
