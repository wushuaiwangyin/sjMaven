package com.krm.dbaudit.web.verify.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.common.constant.Constant;
import com.krm.dbaudit.web.util.SysUserUtils;
import com.krm.dbaudit.web.verify.model.FlowChart;
import com.krm.dbaudit.web.verify.mapper.FlowChartMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("flowChartService")
public class FlowChartService extends ServiceMybatis<FlowChart>{

	@Resource
	private FlowChartMapper flowChartMapper;

	
	public long saveFlowChart(FlowChart flowChart){
		if(flowChart.getId() == null){
			flowChart.set("createDate",new Date() );
			flowChart.set("updateDate", new Date());
			flowChart.set("delFlag", Constant.DEL_FLAG_NORMAL);
			flowChart.set("createBy", SysUserUtils.getCacheLoginUser().getId()+","+
					SysUserUtils.getCacheLoginUser().getName());
			flowChartMapper.insertFlowChart(flowChart);
		}else{
			this.updateByPrimaryKeySelective(flowChart);
		}
		return flowChart.getId();
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteFlowChart(Long id){
		return this.updateDelFlagToDelStatusById(FlowChart.class, id);
	}
	
	
	public Integer deleteFlowChart(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteFlowChart(id);
		}
		return count;
	}
	
	
	/**
	 * 根据ID查询，XML可使用
	 * @param id
	 * @return
	 */
	public FlowChart getById(Long id){
		return flowChartMapper.getById(id);
	}
	
	/**
	 * 列表，XML(clob)没有转换
	* @param params
	* @return
	 */
	public PageInfo<FlowChart> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<FlowChart> list = flowChartMapper.findPageInfo(params);
		return new PageInfo<FlowChart>(list);
	}
	
}
