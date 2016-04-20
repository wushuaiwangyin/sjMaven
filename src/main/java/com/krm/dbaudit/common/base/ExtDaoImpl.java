package com.krm.dbaudit.common.base;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

public class ExtDaoImpl implements ExtDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public ExtDaoImpl()
    {
    }

    public JdbcTemplate getJdbcTemplate()
    {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void executeBySQLText(String sqlText)
    {
        jdbcTemplate.execute(sqlText);
    }
    
    public void insertBySQLText(String sqlText)
    {
        jdbcTemplate.execute(sqlText);
    }

    public List queryBySQLText(String sqlText, Object args[])
    {
        return jdbcTemplate.queryForList(sqlText, args);
    }

    public List queryBySQLText(String sqlText)
    {
        return jdbcTemplate.queryForList(sqlText);
    }

    public int updateBySQLText(String sqlText, Object args[])
    {
        return jdbcTemplate.update(sqlText, args);
    }

    public int updateBySQLText(String sqlText)
    {
        return jdbcTemplate.update(sqlText);
    }

    public String queryForString(String sqlText, String str, Object args[])
    {
        return ((Map)jdbcTemplate.queryForList(sqlText, args).get(0)).get(str).toString();
    }

    public String queryForString(String sqlText, String str)
    {
    	List resultlist = jdbcTemplate.queryForList(sqlText);
    	if((resultlist!=null)&&(resultlist.size()>0))
    		return ((Map)resultlist.get(0)).get(str).toString();
    	else
    		return "";
    }

    public int queryForInt(String sqlText, Object args[])
    {
        return jdbcTemplate.queryForInt(sqlText, args);
    }

    public int queryForInt(String sqlText)
    {
        return jdbcTemplate.queryForInt(sqlText);
    }

    public long queryForLong(String sqlText, Object args[])
    {
        return jdbcTemplate.queryForLong(sqlText, args);
    }

    public long queryForLong(String sqlText)
    {
        return jdbcTemplate.queryForLong(sqlText);
    }
    
    public void commit()
    {
    	try {
			jdbcTemplate.getDataSource().getConnection().commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
