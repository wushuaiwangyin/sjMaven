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
import com.krm.dbaudit.web.model.model.AuditType;
import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.model.model.ModelSubject;
import com.krm.dbaudit.web.model.mapper.AuditModelMapper;
import com.krm.dbaudit.web.model.mapper.AuditTypeMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("auditTypeService")
@CacheConfig(cacheNames="auditType_cache")
public class AuditTypeService extends ServiceMybatis<AuditType>{

	@Resource
	private AuditTypeMapper auditTypeMapper;
	@Resource
	private AuditModelMapper auditModelMapper;

	
	@CacheEvict(allEntries=true)
	public int saveAuditType(AuditType auditType){
		int count = 0;
		if(auditType.getId() == null){
			count = this.insertSelective(auditType);
		}else{
			count = this.updateByPrimaryKeySelective(auditType);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	@CacheEvict(allEntries=true)
	public int deleteAuditType(Long id){
		//删除相关的模型叛关联（递归）
		//auditModelMapper.deleteModelByRootTypeId(id);
		return auditTypeMapper.deleteAuditTypeByRootId(id);
	}
	
	public int deleteAuditModelByType(Long type){
		return auditTypeMapper.deleteAuditModelByType(type);
	}
	
	
	
	public Integer deleteAuditType(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteAuditType(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<AuditType> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<AuditType> list = auditTypeMapper.findPageInfo(params);
		return new PageInfo<AuditType>(list);
	}

	
	public AuditType findById(Long id) {
		return auditTypeMapper.findById(id);
	}
	
	
	public List<AuditType> findAllAuditType(){
		return this.select(new AuditType(),"sort");
	}

	public int batchInsert(List<Map<String, Object>> list) {
		int count = 0;
		count = auditTypeMapper.batchInsert(list);
		return count;
	}
	
}
