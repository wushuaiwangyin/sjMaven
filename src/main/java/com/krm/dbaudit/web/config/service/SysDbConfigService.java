package com.krm.dbaudit.web.config.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.config.model.SysDbConfig;
import com.krm.dbaudit.web.config.mapper.SysDbConfigMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("sysDbConfigService")
public class SysDbConfigService extends ServiceMybatis<SysDbConfig>{

	@Resource
	private SysDbConfigMapper sysDbConfigMapper;

	
	public int saveSysDbConfig(SysDbConfig sysDbConfig){
		int count = 0;
		if(sysDbConfig.getId() == null){
			count = this.insertSelective(sysDbConfig);
		}else{
			count = this.updateByPrimaryKeySelective(sysDbConfig);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteSysDbConfig(Long id){
		return this.updateDelFlagToDelStatusById(SysDbConfig.class, id);
	}
	
	
	public Integer deleteSysDbConfig(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteSysDbConfig(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<SysDbConfig> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SysDbConfig> list = sysDbConfigMapper.findPageInfo(params);
		return new PageInfo<SysDbConfig>(list);
	}
	
}
