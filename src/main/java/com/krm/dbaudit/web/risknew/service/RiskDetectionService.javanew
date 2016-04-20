/*package com.krm.dbaudit.web.risk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.model.model.AuditModel;
import com.krm.dbaudit.web.risk.mapper.RiskDetectionMapper;
import com.krm.dbaudit.web.risk.model.ModelData;
import com.krm.dbaudit.web.risk.model.RiskModelBase;
import com.krm.dbaudit.web.risk.service.RiskDetectionService;

@Service("riskDetectionService")
public class RiskDetectionService extends ServiceMybatis<RiskModelBase> 
{
	@Resource
	private RiskDetectionMapper riskDetectionMapper;
	
	@Resource
	private SqlMapper sqlMapper;

	public SqlMapper getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(SqlMapper sqlMapper) {
		this.sqlMapper = sqlMapper;
	}
	
	*//**
	 * 查询模型列表根据审计专项类型
	 * @param params
	 * @return
	 *//*
	public PageInfo<RiskModelBase> findPageInfoByAudit(Map<String, Object> params)
	{
		PageHelper.startPage(params);
		List<RiskModelBase> list = riskDetectionMapper.findPageInfoByAudit(params);
		Map<String,String> temp = new HashMap<String, String>();
		for (RiskModelBase riskModelBase : list)
		{
			String sql = "select m.MODEL_ID,M.DEAL_STATUS from MODEL_DATA m where MODEL_ID = "+riskModelBase.getId()
					+ " GROUP BY MODEL_ID,M.DEAL_STATUS ";
			List<Map<String,Object>> status = sqlMapper.selectList(sql);
			if(status.size() != 0){
				for (Map<String, Object> map : status)
				{
					String str = map.get("dealStatus").toString();
					temp.put(str, str);
				}
				if(temp.containsKey("1") && temp.containsKey("2")){
					riskModelBase.set("dealStatus", "处理中");
				}if(temp.containsKey("1") && !temp.containsKey("2")){
					riskModelBase.set("dealStatus", "未处理");
				}if(!temp.containsKey("1") && temp.containsKey("2")){
					riskModelBase.set("dealStatus", "已处理");
				}if(!temp.containsKey("1") && !temp.containsKey("2")){
					riskModelBase.set("dealStatus", null);
				}
				temp.clear();
			}
		}
		return new PageInfo<RiskModelBase>(list);
	}
	
	
	*//**
	 * 查询模型列表
	 * @param params
	 * @return
	 *//*
	public PageInfo<RiskModelBase> findPageInfo(Map<String, Object> params)
	{
		PageHelper.startPage(params);
		List<RiskModelBase> list = riskDetectionMapper.findPageInfo(params);
		Map<String,String> temp = new HashMap<String, String>();
		for (RiskModelBase riskModelBase : list)
		{
			String sql = "select m.MODEL_ID,M.DEAL_STATUS from MODEL_DATA m where MODEL_ID = "+riskModelBase.getId()
					+ " GROUP BY MODEL_ID,M.DEAL_STATUS ";
			List<Map<String,Object>> status = sqlMapper.selectList(sql);
			if(status.size() != 0){
				for (Map<String, Object> map : status)
				{
					String str = map.get("dealStatus").toString();
					temp.put(str, str);
				}
				if(temp.containsKey("1") && temp.containsKey("2")){
					riskModelBase.set("dealStatus", "处理中");
				}if(temp.containsKey("1") && !temp.containsKey("2")){
					riskModelBase.set("dealStatus", "未处理");
				}if(!temp.containsKey("1") && temp.containsKey("2")){
					riskModelBase.set("dealStatus", "已处理");
				}if(!temp.containsKey("1") && !temp.containsKey("2")){
					riskModelBase.set("dealStatus", null);
				}
				temp.clear();
			}
			
		}
		return new PageInfo<RiskModelBase>(list);
	}
	
	*//**
	 * 通过id查询
	 * @param id
	 * @return
	 *//*
	public RiskModelBase findById(Long id)
	{
		return riskDetectionMapper.findById(id);
	}
	
	*//**
	 * 统计上月数据处理情况
	 * @param params
	 * @return
	 *//*
	
	public List<Map<String,Object>> findLastMonthByStatus(Map<String, Object> params){
		return riskDetectionMapper.findLastMonthByStatus(params);
	}
	
	*//**
	 * 统计昨日数据处理情况
	 * @param params
	 * @return
	 *//*
	
	public List<Map<String,Object>> findYesterdayByStatus(Map<String, Object> params){
		return riskDetectionMapper.findYesterdayByStatus(params);
	}
	
	*//**
	 * 柱状图数据
	 * @return
	 *//*
	
	public List<Map<String,Object>> countByBar(Map<String, Object> params){
		return riskDetectionMapper.countByBar(params);
	}
	
	*//**
	 * 柱状图机构新增模型上月排行榜
	 * @param params
	 * @return
	 *//*
	
	public List<Map<String,Object>> findLastMonthByOrgan(Map<String, Object> params){
		return riskDetectionMapper.findLastMonthByOrgan(params);
	}
	
	*//**
	 * 柱状图机构新增模型昨日排行榜
	 * @param params
	 * @return
	 *//*
	
	public List<Map<String,Object>> findYesterdayByOrgan(Map<String, Object> params){
		return riskDetectionMapper.findYesterdayByOrgan(params);
	}

	*//**
	 * 查询跑批模型数量
	 * @param params
	 * @return
	 *//*
	public List<Map<String, Object>> findRunAmount(Map<String, Object> params)
	{
		Integer flag = Integer.parseInt(params.get("flag").toString());
		List<Map<String,Object>> list = null;
		if(flag == 1){
			list = riskDetectionMapper.findLastRunByMonth(params);
		}else if (flag == 0){
			list = riskDetectionMapper.findLastRunByYesterday(params);
		}
		return list;
	}
	
	*//**
	 * 查询模型属性值
	 * @param name
	 * @return
	 *//*
	public Long getProperty(String name){
		String sql = "select ID from MODEL_BUZ_PROPERTY where NAME = '"+name+"'";
		Long property = Long.parseLong(sqlMapper.selectOne(sql).get("id").toString());
		return property;
	}
	
	public ModelData findModelDataByPkid(Integer id){
		return riskDetectionMapper.findModelDataByPkid(id);
	}

	public List<AuditModel> findAllAuditModel()
	{
		return riskDetectionMapper.findAllAuditModel();
	}
	
}
*/