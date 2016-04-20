package com.krm.dbaudit.web.api.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.api.model.SysApi;
import com.krm.dbaudit.web.api.mapper.SysApiMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("sysApiService")
public class SysApiService extends ServiceMybatis<SysApi>{

	@Resource
	private SysApiMapper sysApiMapper;

	
	public int saveSysApi(SysApi sysApi){
		int count = 0;
		if(sysApi.getId() == null){
			count = this.insertSelective(sysApi);
		}else{
			count = this.updateByPrimaryKeySelective(sysApi);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteSysApi(Long id){
		return this.updateDelFlagToDelStatusById(SysApi.class, id);
	}
	
	
	public Integer deleteSysApi(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteSysApi(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<SysApi> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SysApi> list = sysApiMapper.findPageInfo(params);
		return new PageInfo<SysApi>(list);
	}
	
}
