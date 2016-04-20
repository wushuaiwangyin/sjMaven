package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.verify.model.QueryLog;
import com.krm.dbaudit.web.verify.mapper.QueryLogMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("queryLogService")
public class QueryLogService extends ServiceMybatis<QueryLog>{

	@Resource
	private QueryLogMapper queryLogMapper;

	
	public int saveQueryLog(QueryLog queryLog){
		int count = 0;
		if(queryLog.getId() == null){
			count = this.insertSelective(queryLog);
		}else{
			count = this.updateByPrimaryKeySelective(queryLog);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteQueryLog(Long id){
		return this.updateDelFlagToDelStatusById(QueryLog.class, id);
	}
	
	
	public Integer deleteQueryLog(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteQueryLog(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<QueryLog> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<QueryLog> list = queryLogMapper.findPageInfo(params);
		return new PageInfo<QueryLog>(list);
	}
	
}
