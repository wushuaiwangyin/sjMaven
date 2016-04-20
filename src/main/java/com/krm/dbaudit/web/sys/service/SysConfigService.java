package com.krm.dbaudit.web.sys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.sys.mapper.SysConfigMapper;
import com.krm.dbaudit.web.sys.model.SysConfig;

@Service("sysConfigService")
@CacheConfig(cacheNames="sysConfig_cache")
public class SysConfigService extends ServiceMybatis<SysConfig>{

	@Resource
	private SysConfigMapper sysConfigMapper;
	
	/**
	 * 保存或更新
	 * 
	 * @param config
	 * @return
	 */
	@CacheEvict(allEntries = true)
	public int saveSysdict(SysConfig config) {
		return this.save(config);
	}
	
	
	@Cacheable(key="'allConfigMultimap'")
	public Multimap<String, SysConfig> findAllMultimap(){
		List<SysConfig> configs = this.select(new SysConfig());
		Multimap<String, SysConfig> multimap = ArrayListMultimap.create();
		for(SysConfig config : configs){
			multimap.put(config.getLabel(), config);
		}
		return multimap;
	}

	public String findByKey(String key){
		List<SysConfig> configs = (List<SysConfig>) findAllMultimap().get(key);
		if(!configs.isEmpty()){
			return configs.get(0).getValue();
		}
		return null;
	}
	
	
}
