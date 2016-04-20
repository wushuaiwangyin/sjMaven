package com.krm.dbaudit.web.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.model.mapper.ModelPropertyMapper;
import com.krm.dbaudit.web.model.model.ModelProperty;



/**
* @author taosq on 2015-08-13
*/

@Service("modelPropertyService")
@CacheConfig(cacheNames="modelProperty_cache")
public class ModelPropertyService extends ServiceMybatis<ModelProperty>{

	@Resource
	private ModelPropertyMapper modelPropertyMapper;

	@CacheEvict(allEntries=true)
	public int saveModelProperty(ModelProperty modelProperty){
		int count = 0;
		if(modelProperty.getId() == null){
			count = this.insertSelective(modelProperty);
		}else{
			count = this.updateByPrimaryKeySelective(modelProperty);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	@CacheEvict(allEntries=true)
	public int deleteModelProperty(Long id){
		return this.updateDelFlagToDelStatusById(ModelProperty.class, id);
	}
	
	public Integer deleteModelProperty(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteModelProperty(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<ModelProperty> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ModelProperty> list = modelPropertyMapper.findPageInfo(params);
		return new PageInfo<ModelProperty>(list);
	}
	
	@Cacheable(key="'model_project_all'")
	public List<ModelProperty> findAllProperty(){
		return this.select(new ModelProperty(),"sort");
	}
	
	@Cacheable(key="'model_project_all_condition'")
	public List<ModelProperty> findAllProperty(ModelProperty mp){
		return this.select(mp,"sort");
	}
	
}
