package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.verify.model.DataModel;
import com.krm.dbaudit.web.verify.mapper.DataModelMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("dataModelService")
public class DataModelService extends ServiceMybatis<DataModel>{

	@Resource
	private DataModelMapper dataModelMapper;

	
	public int saveDataModel(DataModel dataModel){
		int count = 0;
		if(dataModel.getId() == null){
			count = this.insertSelective(dataModel);
		}else{
			count = this.updateByPrimaryKeySelective(dataModel);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteDataModel(Long id){
		return this.updateDelFlagToDelStatusById(DataModel.class, id);
	}
	
	
	public Integer deleteDataModel(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteDataModel(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<DataModel> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<DataModel> list = dataModelMapper.findPageInfo(params);
		return new PageInfo<DataModel>(list);
	}
	
}
