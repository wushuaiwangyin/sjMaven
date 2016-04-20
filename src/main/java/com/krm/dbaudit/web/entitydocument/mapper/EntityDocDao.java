package com.krm.dbaudit.web.entitydocument.mapper;

import java.util.List;
import java.util.Map;

import com.krm.dbaudit.web.entitydocument.model.LinkManInfo;
import com.krm.dbaudit.web.entitydocument.model.OtherThings;

public interface EntityDocDao  {
public List<Map<String, Object>> getAllOtherIds();
public List<Map<String, Object>> getAllLinkIds();
/**
 * @param updateList
 * @param saveList
 * @return
 */
public String saveOrUpdateOthers(List<OtherThings> updateList,List<OtherThings> saveList);
public String saveOrUpdateLinks(List<LinkManInfo> updateList,List<LinkManInfo> saveList);

public List<Map<String,Object>> getZbjyList(Map<String,Object> params);
}
