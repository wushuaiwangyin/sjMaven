package com.krm.dbaudit.web.all360.model;

import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

@Table(name = "SYS360_PHYTABLES_CONFIG")
public class PhyTableCngModel extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3878845371993887601L;
	private long pkid;
	private String phyTable;
	private String isPublic;

	public long getPkid() {
		return pkid;
	}

	public void setPkid(long pkid) {
		this.pkid = pkid;
	}

	public String getPhyTable() {
		return phyTable;
	}

	public void setPhyTable(String phyTable) {
		this.phyTable = phyTable;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

}
