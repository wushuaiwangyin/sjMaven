package com.krm.dbaudit.web.risk.tabelMapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.Page;
import com.krm.dbaudit.web.risk.tabelMapper.QueryDetailInfoDao;

@Repository("queryDetailInfoDaoImpl")
public class QueryDetailInfoDaoImpl implements QueryDetailInfoDao
{
	@Resource
	private SqlMapper sqlMapper;

	public SqlMapper getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(SqlMapper sqlMapper) {
		this.sqlMapper = sqlMapper;
	}
	
	@Override
	public List<Map<String, Object>> queryHeaders(Map<String, Object> params)
	{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		sb.append("select m.pkid as dataId, m.model_id as modelId, m.organ_id as organId, "
//				+ "m.data_date as dataDate, sy.label as dealStatus, sd.label as noticeStatus, m.cust_no as custNo,  ");
//		map.put("dataId", "dataId");     map.put("modelId", "modelId");   
//		map.put("dataDate", "会计日期");   map.put("organId", "机构编号");
//		map.put("dealStatus", "处理状态"); map.put("noticeStatus", "通知状态"); 
//		map.put("custNo", "客户号");
//		list.add(map);
		
		StringBuffer sb = new StringBuffer();
		String modelId = null;
		String status = null;
		if(params.containsKey("modelId")){
			modelId = params.get("modelId").toString();
		}
		if(params.containsKey("status")){
			status = params.get("status").toString();
		}
		
		sb.append("select 'itemvalue'||field_column as itemvalues,field_alias as fields from "
				+ "MODEL_DEF_FIELD where 1=1 ");
		if(modelId != null && modelId.length() != 0){
			sb.append(" and model_id = "+ modelId);
		}else if(status != null && status.length() != 0){
			sb.append(" and status = "+ status);
		}
		sb.append("  order by field_order");
		List<Map<String, Object>> list1 = sqlMapper.selectList(sb.toString());
		list.addAll(list1);
		return list;
	}

	@Override
	public Page<Map<String, Object>> findPageInfo(Map<String, Object> params)
	{
		Page<Map<String, Object>> contentsList = new Page<Map<String,Object>>();
		List<Map<String, Object>> headerList = this.queryHeaders(params);
		Map<String,Object> headersMap = new HashMap<String, Object>();
		Map<String,Object> contentsMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer("SELECT * FROM (	SELECT tmp_page.*, rownum row_id FROM (");
		Integer pageSize = Integer.parseInt(params.get("pageSize").toString());
		Integer offset = Integer.parseInt(params.get("offset").toString());
		Integer pageNum = Integer.parseInt(params.get("pageNum").toString());
		String field = null;
		String value1 = null;
		String value2 = null;
		String condition = null;
		String dataId = null;
		String organId = null;
		String custNo = null;
		String noticeStatus = null;
		String dealStatus = null;	//模型处理状态
//		String dealStatus1 = null;	//数据内部处理状态
		//模型没有数据时，表头默认有数据编号，模型编号，机构编号，会计日期，且长度为1
		if(headerList.size() != 1){
			//进入详细前，默认有客户号，处理状态，机构号进行一次过滤
			if(params.containsKey("organId")){
				organId = params.get("organId").toString().trim();
			}if(params.containsKey("custNo")){
				custNo = params.get("custNo").toString().trim();
			}if(params.containsKey("dealStatus")){
				dealStatus = params.get("dealStatus").toString();
			}
			
			//进入数据详细界面，有字段提供过滤数据
			if(params.containsKey("field") && params.containsKey("value1") 
					&& params.containsKey("value2") &&params.containsKey("condition")){
				field = params.get("field").toString();
				value1 = params.get("value1").toString();
				value2 = params.get("value2").toString();
				condition = params.get("condition").toString();
				noticeStatus = params.get("noticeStatus").toString();
				dealStatus = params.get("dealStatus").toString();
			}//双击数据和导出数据时，获取数据id
			if(params.containsKey("dataId")){
				dataId = params.get("dataId").toString();
			}
			sb.append("select m.pkid as dataId, m.model_id as modelId, m.organ_id as organId, "
					+ "m.data_date as dataDate, sy.label as dealStatus, sd.label as noticeStatus, m.cust_no as custNo,  ");
			for (int i = 1; i < headerList.size(); i++)
			{
				headersMap = headerList.get(i);
				if(i < headerList.size()-1){
					sb.append("m."+headersMap.get("itemvalues") + ", ");
				}else{
					sb.append("m."+headersMap.get("itemvalues") + " ");
				}
			}
			StringBuffer wheresb = new StringBuffer();
			wheresb.append(" from MODEL_DATA m left join sys_dict sy on m.deal_status = sy.value and sy.type = 'model_deal_status' "
					+"left join sys_dict sd on m.notice_status = sd.value and sd.type = 'data_status' "
					+ " where model_id = " + params.get("modelId")+ " ");
			//第一次过滤数据sql
			if(organId != null && organId.length() != 0){
				wheresb.append(" and m.organ_id in "
						+ "(select id from sys_office o start with o.id="+organId+" connect by prior  o.ID=o.PARENT_ID )");
			}if(custNo != null && custNo.length() != 0){
				wheresb.append(" and trim(m.cust_no) = '"+custNo+"'");
			}
//			if(dealStatus != null && dealStatus.length() != 0){
//				if(dealStatus.equals("1") || dealStatus.equals("2")){
//					wheresb.append(" and m.deal_status = '"+dealStatus+"'");
//				}
//			}    
//			+ " as " + headersMap.get("fields")
			//第二次过滤数据sql
			if(field != null  && condition.length() != 0 ){
				if(field.equals("data_date")){
					value1 = "to_date('"+value1+"','yyyy-mm-dd')";
					value2 = "to_date('"+value2+"','yyyy-mm-dd')";
				}
				if(field.equals("noticeStatus")) {
					field = "notice_status";
					value1 = noticeStatus;
				}
				if(field.equals("dealStatus")) {
					field = "deal_status";
					value1 = dealStatus;
				}
				if(field.equals("organId")) field = "organ_id";
				if(field.equals("dataDate")) field = "data_date";
				if(field.equals("custNo")) field = "cust_no";
				if(condition.equals("equals")){
					wheresb.append(" and trim(m."+field+") = "+value1+"");
				}else if(condition.equals("greater")){
					wheresb.append(" and trim(m."+field+") > "+value1+"");
				}else if(condition.equals("less")){
					wheresb.append(" and trim(m."+field+") < "+value1+"");
				}else if(condition.equals("greaterOrEquals")){
					wheresb.append(" and trim(m."+field+") >= "+value1+"");
				}else if(condition.equals("lessOrEquals")){
					wheresb.append(" and trim(m."+field+") <= "+value1+"");
				}else if(condition.equals("between")){
					wheresb.append(" and trim(m."+field+") > "+value1+" and "+field+" < "+value2+"");
				}else if(condition.equals("include")){
					wheresb.append(" and trim(m."+field+") in ("+value1+")");
				}else if(condition.equals("exclusive")){
					wheresb.append(" and trim(m."+field+") not in ("+value1+")");
				}
			}//查询单条数据sql
			wheresb.append(" and trim(m.organ_id) is not null ");
			if(dataId != null && dataId.length() != 0){
				wheresb.append(" and m.pkid in ("+ dataId +") ");
			}
			wheresb.append(" order by  m.notice_status desc, m.deal_status desc ");
			sb.append(wheresb);
			sb.append(") tmp_page WHERE rownum <= "+pageSize * pageNum+" ) WHERE row_id > "+ offset);
			Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNum, pageSize,true);
			page.addAll(sqlMapper.selectList(sb.toString()));
			Map<String,Object> map = sqlMapper.selectOne("select count(1) as count "+wheresb.toString());
			Integer count = Integer.parseInt(map.get("count").toString());
			page.setTotal(count);
			return page;
		}
		else{
			contentsMap.put("温馨提示：", "无相关数据！");
			contentsList.add(contentsMap);
			return contentsList;
		}
	}

	
	@Override
	public List<Map<String, Object>> queryContents(Map<String, Object> params)
	{
		List<Map<String, Object>> headerList = this.queryHeaders(params);
		Map<String,Object> headersMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		String dataId = null;
		if(params.containsKey("dataId")){
			dataId = params.get("dataId").toString();
		}
		sb.append("select m.pkid as 数据编号, m.model_id as 模型编号, m.organ_id as 机构编号, "
				+ "m.data_date as 会计日期, m.cust_no as 客户号,  sy.label as 处理状态, sd.label as 通知状态, ");
		for (int i = 1; i < headerList.size(); i++)
		{
			headersMap = headerList.get(i);
			if(i < headerList.size()-1){
				sb.append("m."+headersMap.get("itemvalues") + " as " + headersMap.get("fields") + ", ");
			}else{
				sb.append("m."+headersMap.get("itemvalues") + " as " + headersMap.get("fields") + " ");
			}
		}
		sb.append(" from MODEL_DATA m left join sys_dict sy on m.deal_status = sy.value and sy.type = 'model_deal_status' "
				+"left join sys_dict sd on m.notice_status = sd.value and sd.type = 'data_status' "
				+ " where model_id = " + params.get("modelId")+ " ");
		sb.append(" and trim(m.organ_id) is not null ");
		if(dataId != null && dataId.length() != 0){
			sb.append(" and m.pkid in ("+ dataId +") ");
		}
		sb.append(" order by  m.notice_status desc, m.deal_status desc ");
		return sqlMapper.selectList(sb.toString());
	}
	
	
	/**
	 * 数据下发后，更改源数据状态
	 */
	public int updateNoticeStatus(Integer dataId, Integer status){
		if(dataId != null && status != null){
			String sql = "update MODEL_DATA set notice_status = " + status + "where pkid = "+ dataId;
			return sqlMapper.update(sql);
		}
		return -1;
	}
	
	
	/**
	 * 数据核实后，更改源数据状态
	 */
	public int updateDealStatus(Integer dataId, Integer status){
		if(dataId != null && status != null){
			String sql = "update MODEL_DATA set deal_status = " + status + "where pkid = "+ dataId;
			return sqlMapper.update(sql);
		}
		return -1;
	}

}
