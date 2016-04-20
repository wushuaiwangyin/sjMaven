package com.krm.dbaudit.web.verify.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.sys.model.SysArea;
import com.krm.dbaudit.web.sys.model.SysOffice;
import com.krm.dbaudit.web.verify.model.FlowChart;
import com.krm.dbaudit.web.verify.model.FlowType;
import com.krm.dbaudit.web.verify.mapper.FlowChartMapper;
import com.krm.dbaudit.web.verify.mapper.FlowTypeMapper;



/**
* @author taosq on 2015-08-13
*/

@Service("flowTypeService")
public class FlowTypeService extends ServiceMybatis<FlowType>{

	@Resource
	private FlowTypeMapper flowTypeMapper;
	
	@Resource
	private FlowChartMapper flowChartMapper;

	
	public int saveFlowType(FlowType flowType){
		int count = 0;
		if(flowType.getId() == null){
			count = this.insertSelective(flowType);
		}else{
			count = this.updateByPrimaryKeySelective(flowType);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteFlowType(Long id){
		int count = this.beforeDeleteTreeStructure(id,"type",FlowChart.class,FlowType.class);
		return count==-1?-1:flowTypeMapper.deleteFlowTypeByRootId(id);
	}
	
	
	public Integer deleteFlowType(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteFlowType(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<FlowType> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<FlowType> list = flowTypeMapper.findPageInfo(params);
		return new PageInfo<FlowType>(list);
	}

	//查询所有及流程
	public List<FlowType> findFlowTree() {
		List<FlowType> fts = flowTypeMapper.findFlowTree();
		
		//将流程图加在后面
		List<FlowChart> fcs = flowChartMapper.findAllFlowChart();
		for (FlowChart fc : fcs) {
			FlowType ft = new FlowType();
			ft.set("id", "flow_"+fc.getId());
			ft.setParentId(fc.getType());
			ft.setName(fc.getName());
			ft.set("iconSkin", "flow");
			fts.add(ft);
		}
		return fts;
	}

	public List<FlowType> findAllFlowType() {
		return this.select(new FlowType());
	}
	
	
}
