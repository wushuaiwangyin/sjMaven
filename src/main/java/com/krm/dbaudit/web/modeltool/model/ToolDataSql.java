package com.krm.dbaudit.web.modeltool.model;

import com.krm.dbaudit.common.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Table;

/**
* @author chenwei on 2015-11-13
*/
@SuppressWarnings({ "unused"})
public class ToolDataSql extends BaseEntity{
	private String runsql;

	public String getRunsql(){
		return this.getString("runsql");
	}
	public void setRunsql(String runsql) {
		this.set("runsql",runsql);
	}
}
