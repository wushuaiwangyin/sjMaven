package com.krm.dbaudit.web.risk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.abel533.sql.SqlMapper;
import com.krm.dbaudit.web.risk.mapper.SecondRiskModelMapper;

@Service("secondRiskService")
public class SecondRiskService
{
	@Resource
	private SecondRiskModelMapper secondRiskModelMapper;
	
	@Resource
	private SqlMapper sqlMapper;

	public SqlMapper getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(SqlMapper sqlMapper) {
		this.sqlMapper = sqlMapper;
	}
	
	public List<Map<String, Object>> findResultByMonth(Map<String, Object> params){
		return secondRiskModelMapper.findResultByMonth(params);
	}
	
	public List<Map<String, Object>> findResultByDay(Map<String, Object> params){
		return secondRiskModelMapper.findResultByDay(params);
	}
	
	public List<Map<String, Object>> findDetails(Map<String, Object> params){
		return secondRiskModelMapper.findDetails(params);
	}
	
	/**
	 * 每日扫描获取客户号
	 * @return
	 */
	public List<Map<String, Object>> scan(){
		String sql = "select a.custNo as custNo,count(0) as count from("+
				"select  trim(m.CUST_NO) as custNo, m.MODEL_ID from MODEL_DATA m where trim(m.CUST_NO) is not null "+
				" and m.DATA_DATE >= trunc(sysdate, 'dd')-0.5"+
                " and m.DATA_DATE <= trunc(sysdate, 'dd')"+
				" group by m.CUST_NO, m.MODEL_ID)a "+
				" group by a.custNo having  count(0) >=3";
		return sqlMapper.selectList(sql);
	}
	
	
	/**
	 * 通过客户号获取相关的模型id
	 * @param custNo
	 * @return
	 */
	public List<Map<String, Object>> getSecondRiskModel(String custNo)
	{
		String sql = "select distinct m.MODEL_ID as modelId,m.CUST_NO as custNo,c.CUSTOMNAME as custName,sysdate as dataDate from MODEL_DATA m "+
	    "left join ciscustomerinfo c on TRIM(m.CUST_NO) = TRIM(c.CUSTOMID)"+
	    "where TRIM(m.CUST_NO) ='"+custNo+"'";
		return sqlMapper.selectList(sql);
	}
	
	/**
	 * 判断客户号是否存在
	 * @param custNo
	 * @return
	 */
	public Map<String, Object> findCustomer(String custNo){
		String sql = "select * from ciscustomerinfo where trim(customid)  = '"+custNo+"'";
		return sqlMapper.selectOne(sql);
	}
	
	/**
	 * 插入二次分析模型数据
	 * @param list
	 * @return
	 */
	public int save(List<Map<String, Object>> list){
		return secondRiskModelMapper.save(list);
	}

	
}
