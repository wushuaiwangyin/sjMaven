package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.verify.model.DataModelType;
import com.krm.dbaudit.web.verify.mapper.DataModelTypeMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("dataModelTypeService")
public class DataModelTypeService extends ServiceMybatis<DataModelType>{

	@Resource
	private DataModelTypeMapper dataModelTypeMapper;

	
	public int saveDataModelType(DataModelType dataModelType){
		int count = 0;
		if(dataModelType.getId() == null){
			count = this.insertSelective(dataModelType);
		}else{
			count = this.updateByPrimaryKeySelective(dataModelType);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteDataModelType(Long id){
		return this.updateDelFlagToDelStatusById(DataModelType.class, id);
	}
	
	
	public Integer deleteDataModelType(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteDataModelType(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<DataModelType> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<DataModelType> list = dataModelTypeMapper.findPageInfo(params);
		return new PageInfo<DataModelType>(list);
	}
	
}
