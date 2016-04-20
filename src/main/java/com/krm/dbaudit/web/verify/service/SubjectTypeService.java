package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.verify.model.SubjectType;
import com.krm.dbaudit.web.verify.mapper.SubjectTypeMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("subjectTypeService")
public class SubjectTypeService extends ServiceMybatis<SubjectType>{

	@Resource
	private SubjectTypeMapper subjectTypeMapper;

	
	public int saveSubjectType(SubjectType subjectType){
		int count = 0;
		if(subjectType.getId() == null){
			count = this.insertSelective(subjectType);
		}else{
			count = this.updateByPrimaryKeySelective(subjectType);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteSubjectType(Long id){
		return this.updateDelFlagToDelStatusById(SubjectType.class, id);
	}
	
	
	public Integer deleteSubjectType(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteSubjectType(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<SubjectType> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SubjectType> list = subjectTypeMapper.findPageInfo(params);
		return new PageInfo<SubjectType>(list);
	}
	
}
