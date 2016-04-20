package com.krm.dbaudit.web.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.model.model.ModelRun;
import com.krm.dbaudit.web.model.mapper.ModelRunMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("modelRunService")
public class ModelRunService extends ServiceMybatis<ModelRun>{

	@Resource
	private ModelRunMapper modelRunMapper;

	
	public int saveModelRun(ModelRun modelRun){
		int count = 0;
		if(modelRun.getId() == null){
			count = this.insertSelective(modelRun);
		}else{
			count = this.updateByPrimaryKeySelective(modelRun);
		}
		return count;
	}
		
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<ModelRun> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ModelRun> list = modelRunMapper.findPageInfo(params);
		return new PageInfo<ModelRun>(list);
	}
	
	public List<ModelRun> selectByModelId(long modelid){
		return modelRunMapper.selectByModelId(modelid);
	}
}
