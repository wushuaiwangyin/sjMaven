package com.krm.dbaudit.web.sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.abel533.sql.SqlMapper;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.common.utils.DateUtils;
import com.krm.dbaudit.web.sys.mapper.SysAccessoryMapper;
import com.krm.dbaudit.web.sys.model.SysAccessory;
import com.krm.dbaudit.web.util.SysUserUtils;

@Service("sysAccessoryService")
public class SysAccessoryService extends ServiceMybatis<SysAccessory>
{
	@Resource
	private SqlMapper sqlMapper;

	public SqlMapper getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(SqlMapper sqlMapper) {
		this.sqlMapper = sqlMapper;
	}
	@Resource
	private SysAccessoryMapper sysAccessoryMapper;
	/**
	 * save accessory file
	 * @param sysAccessory
	 * @return
	 */
	public int saveFile(SysAccessory sysAccessory)
	{
		int count = 0;
		if(null == sysAccessory.getDocId()){
			sysAccessory.setCreatorId(SysUserUtils.getCacheLoginUser().getName());
			sysAccessory.setCreateTime(DateUtils.formatDateTime(new Date()));
			count = this.insertSelective(sysAccessory);
		}else{
			sysAccessory.setEditorId(SysUserUtils.getCacheLoginUser().getName());
			sysAccessory.setEditTime(DateUtils.formatDateTime(new Date()));
			count = this.updateByPrimaryKeySelective(sysAccessory);
		}
		return count;
	}
	
	/**
	 * 获取刚插入数据的id
	 * @return
	 */
	public Long getDocId(){
		String sql = "select SEQ_SYS_ACCESSORY.NEXTVAL as docId from dual ";
		Map<String, Object> map = sqlMapper.selectOne(sql);
		return Long.parseLong(map.get("docid").toString());
	}
	
	/**
	 * 通过id查找附件
	 * @param id
	 * @return
	 */
	public SysAccessory findById(Long id){
		return sysAccessoryMapper.findById(id);
	}
	
	
	public Map<String,Object> getSysAccessory(Long id){
		String sql = "select * from sys_accessory sy where sy.doc_id = "+id;
		return sqlMapper.selectOne(sql);
	}
	
	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	public Integer deleteAccessoryByRootId(Long id){
		SysAccessory sysAccessory = sysAccessoryMapper.findById(id);
		if(sysAccessory != null){
			return this.deleteByPrimaryKey(id);
		}
		return null;
	}
}
