package com.krm.dbaudit.web.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.model.model.AuditModel;
import com.krm.dbaudit.web.model.mapper.AuditModelMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("auditModelService")
public class AuditModelService extends ServiceMybatis<AuditModel>{

	@Resource
	private AuditModelMapper auditModelMapper;

	
	public int saveAuditModel(AuditModel auditModel){
		int count = 0;
		if(auditModel.getId() == null){
			count = this.insertSelective(auditModel);
		}else{
			count = this.updateByPrimaryKeySelective(auditModel);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteAuditModel(Long id){
		return this.updateDelFlagToDelStatusById(AuditModel.class, id);
	}
	
	
	public Integer deleteAuditModel(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteAuditModel(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<AuditModel> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<AuditModel> list = auditModelMapper.findPageInfo(params);
		return new PageInfo<AuditModel>(list);
	}

	public List<AuditModel> findAuditModel(Map<String, Object> params) {
		return auditModelMapper.findAuditModel(params);
	}
	
}
