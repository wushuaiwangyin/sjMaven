package com.krm.dbaudit.web.config.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.config.model.TableDef;
import com.krm.dbaudit.web.config.mapper.TableDefMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("tableDefService")
public class TableDefService extends ServiceMybatis<TableDef>{

	@Resource
	private TableDefMapper tableDefMapper;

	
	public int saveTableDef(TableDef tableDef){
		int count = 0;
		if(tableDef.getId() == null){
			count = this.insertSelective(tableDef);
		}else{
			count = this.updateByPrimaryKeySelective(tableDef);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteTableDef(Long id){
		return this.updateDelFlagToDelStatusById(TableDef.class, id);
	}
	
	
	public Integer deleteTableDef(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteTableDef(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<TableDef> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<TableDef> list = tableDefMapper.findPageInfo(params);
		return new PageInfo<TableDef>(list);
	}
	
}
