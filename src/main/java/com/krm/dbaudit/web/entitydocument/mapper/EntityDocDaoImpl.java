package com.krm.dbaudit.web.entitydocument.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.krm.dbaudit.web.entitydocument.model.LinkManInfo;
import com.krm.dbaudit.web.entitydocument.model.OtherThings;
@Repository("entityDocManDao")
public class EntityDocDaoImpl implements EntityDocDao {
@Autowired
private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
}

public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
}

	@Override
	public List<Map<String, Object>> getAllOtherIds() {
		
		return null;
	}

	@Override
	public List<Map<String, Object>> getAllLinkIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveOrUpdateOthers(List<OtherThings> updateList,
			List<OtherThings> saveList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveOrUpdateLinks(List<LinkManInfo> updateList,
			List<LinkManInfo> saveList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getZbjyList(Map<String, Object> params) {
		String sql="select * from SYS360_USER_ORG where data_date between '"+params.get("startDate").toString().replaceAll("-", "")+"' and '"+params.get("endDate").toString().replaceAll("-", "")+"'  order by name,data_date";
		return jdbcTemplate.queryForList(sql);
	}

}
