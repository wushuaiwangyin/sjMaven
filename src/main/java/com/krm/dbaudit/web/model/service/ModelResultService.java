package com.krm.dbaudit.web.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.model.model.ModelResult;
import com.krm.dbaudit.web.model.mapper.ModelResultMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("modelResultService")
public class ModelResultService extends ServiceMybatis<ModelResult>{

	@Resource
	private ModelResultMapper modelResultMapper;

	
	public int saveModelResult(ModelResult modelResult){
		int count = 0;
		if(modelResult.getId() == null){
			count = this.insertSelective(modelResult);
		}else{
			count = this.updateByPrimaryKeySelective(modelResult);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteModelResult(Long id){
		return this.updateDelFlagToDelStatusById(ModelResult.class, id);
	}
	
	
	public Integer deleteModelResult(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteModelResult(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<ModelResult> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ModelResult> list = modelResultMapper.findPageInfo(params);
		return new PageInfo<ModelResult>(list);
	}
	
}
