package com.krm.dbaudit.web.risk.tabelMapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;



public interface QueryDetailInfoDao
{
	public List<Map<String, Object>> queryHeaders(Map<String, Object> params);
	
	public Page<Map<String, Object>> findPageInfo(Map<String, Object> params);
	
	public List<Map<String, Object>> queryContents(Map<String, Object> params);
	
	public int updateNoticeStatus(Integer dataId, Integer status);
	
	public int updateDealStatus(Integer dataId, Integer status);
	
}
