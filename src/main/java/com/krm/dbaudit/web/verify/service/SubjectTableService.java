package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.verify.model.SubjectTable;
import com.krm.dbaudit.web.verify.mapper.SubjectTableMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("subjectTableService")
public class SubjectTableService extends ServiceMybatis<SubjectTable>{

	@Resource
	private SubjectTableMapper subjectTableMapper;

	
	public int saveSubjectTable(SubjectTable subjectTable){
		int count = 0;
		if(subjectTable.getId() == null){
			count = this.insertSelective(subjectTable);
		}else{
			count = this.updateByPrimaryKeySelective(subjectTable);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteSubjectTable(Long id){
		return this.updateDelFlagToDelStatusById(SubjectTable.class, id);
	}
	
	
	public Integer deleteSubjectTable(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteSubjectTable(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<SubjectTable> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SubjectTable> list = subjectTableMapper.findPageInfo(params);
		return new PageInfo<SubjectTable>(list);
	}
	
}
