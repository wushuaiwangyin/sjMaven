package com.krm.dbaudit.web.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.model.model.ModelStep;
import com.krm.dbaudit.web.model.mapper.ModelStepMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("modelStepService")
public class ModelStepService extends ServiceMybatis<ModelStep>{

	@Resource
	private ModelStepMapper modelStepMapper;

	
	public int saveModelStep(ModelStep modelStep){
		int count = 0;
		if(modelStep.getId() == null){
			count = this.insertSelective(modelStep);
		}else{
			count = this.updateByPrimaryKeySelective(modelStep);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteModelStep(Long id){
		return this.updateDelFlagToDelStatusById(ModelStep.class, id);
	}
	
	
	public Integer deleteModelStep(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteModelStep(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<ModelStep> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ModelStep> list = modelStepMapper.findPageInfo(params);
		return new PageInfo<ModelStep>(list);
	}
	
}
