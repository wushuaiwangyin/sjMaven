package com.krm.dbaudit.web.config.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.config.model.ColumnDef;
import com.krm.dbaudit.web.config.mapper.ColumnDefMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("columnDefService")
public class ColumnDefService extends ServiceMybatis<ColumnDef>{

	@Resource
	private ColumnDefMapper columnDefMapper;

	
	public int saveColumnDef(ColumnDef columnDef){
		int count = 0;
		if(columnDef.getId() == null){
			count = this.insertSelective(columnDef);
		}else{
			count = this.updateByPrimaryKeySelective(columnDef);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteColumnDef(Long id){
		return this.updateDelFlagToDelStatusById(ColumnDef.class, id);
	}
	
	
	public Integer deleteColumnDef(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteColumnDef(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<ColumnDef> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ColumnDef> list = columnDefMapper.findPageInfo(params);
		return new PageInfo<ColumnDef>(list);
	}
	
}
