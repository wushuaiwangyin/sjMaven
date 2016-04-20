package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;

import com.krm.dbaudit.web.verify.model.GoldFlow;
import com.krm.dbaudit.web.verify.mapper.GoldFlowMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("goldFlowService")
public class GoldFlowService extends ServiceMybatis<GoldFlow>{

	@Resource
	private GoldFlowMapper goldFlowMapper;

	
	public int saveGoldFlow(GoldFlow goldFlow){
		int count = 0;
		if(goldFlow.getId() == null){
			count = this.insertSelective(goldFlow);
		}else{
			count = this.updateByPrimaryKeySelective(goldFlow);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteGoldFlow(Long id){
		return this.updateDelFlagToDelStatusById(GoldFlow.class, id);
	}
	
	
	public Integer deleteGoldFlow(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteGoldFlow(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<GoldFlow> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<GoldFlow> list = goldFlowMapper.findPageInfo(params);
		return new PageInfo<GoldFlow>(list);
	}
	
}
