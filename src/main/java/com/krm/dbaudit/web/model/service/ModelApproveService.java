package com.krm.dbaudit.web.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.model.model.ModelApprove;
import com.krm.dbaudit.web.model.mapper.ModelApproveMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("modelApproveService")
public class ModelApproveService extends ServiceMybatis<ModelApprove>{

	@Resource
	private ModelApproveMapper modelApproveMapper;

	
	public int saveModelApprove(ModelApprove modelApprove){
		int count = 0;
		if(modelApprove.getId() == null){
			count = this.insertSelective(modelApprove);
		}else{
			count = this.updateByPrimaryKeySelective(modelApprove);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteModelApprove(Long id){
		return this.updateDelFlagToDelStatusById(ModelApprove.class, id);
	}
	
	
	public Integer deleteModelApprove(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteModelApprove(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<ModelApprove> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ModelApprove> list = modelApproveMapper.findPageInfo(params);
		return new PageInfo<ModelApprove>(list);
	}
	
}
