package com.krm.dbaudit.web.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.model.mapper.ModelSubjectMapper;
import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.model.ModelSubject;
import com.krm.dbaudit.web.sys.model.SysArea;
import com.krm.dbaudit.web.sys.model.SysOffice;



/**
* @author taosq on 2015-08-13
*/

@Service("modelSubjectService")
@CacheConfig(cacheNames="modelSubject_cache")
public class ModelSubjectService extends ServiceMybatis<ModelSubject>{

	@Resource
	private ModelSubjectMapper modelSubjectMapper;

	@CacheEvict(allEntries=true)
	public int saveModelSubject(ModelSubject modelSubject){
		int count = 0;
		if(modelSubject.getId() == null){
			count = this.insertSelective(modelSubject);
		}else{
			count = this.updateByPrimaryKeySelective(modelSubject);
			//更新子节点的业务条线
			if(StringUtils.isNotBlank(modelSubject.getBuzLine())){
				modelSubjectMapper.updateBuzLineByRootId(modelSubject.getId(),modelSubject.getBuzLine());
			}
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	@CacheEvict(allEntries=true)
	public int deleteModelSubject(Long id){
		//检查否有关联的模型
		int count = this.beforeDeleteTreeStructure(id,"subject",ModelBase.class,ModelSubject.class);
		//删除当前节点及子节点
		return count==-1?-1:modelSubjectMapper.deleteSubjectByRootId(id);
	}
	
	
	public ModelSubject findById(Long id){
		return modelSubjectMapper.findById(id);
	}
	
	
	public Integer deleteModelSubject(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteModelSubject(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<ModelSubject> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ModelSubject> list = modelSubjectMapper.findPageInfo(params);
		return new PageInfo<ModelSubject>(list);
	}
	
	
	@Cacheable(key="'model_subject_all'")
	public List<ModelSubject> findAllSubject(){
		return this.select(new ModelSubject(),"sort");
	}
	
}
