package com.krm.dbaudit.web.risk.mapper;


import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.risk.model.DataNoticeModel;


public interface DataNoticeModelMapper  extends Mapper<DataNoticeModel>
{
	public DataNoticeModel findByPkId(Integer id);
	
	public DataNoticeModel checkIsReapted(Map<String, Object> params);
	
	public List<DataNoticeModel> findPageInfo(Map<String, Object> params);

	public int endNoticeStatus(Map<String, Object> params);
	
	public int updateNoticeStatus(Map<String, Object> params);

	public List<DataNoticeModel> findSendNoticePageInfo(Map<String, Object> params);

	public List<DataNoticeModel> findNoticeByNumber(Map<String, Object> params);
}

