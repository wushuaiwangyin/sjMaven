package com.krm.dbaudit.web.sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.common.utils.DateUtils;
import com.krm.dbaudit.web.sys.mapper.KbAudtiMapper;
import com.krm.dbaudit.web.sys.model.KbAudit;
import com.krm.dbaudit.web.util.SysUserUtils;

/**
 * 
 * @author Parker
 *
 */
@Service("kbAuditService")
public class KbAuditService extends ServiceMybatis<KbAudit>
{
	@Resource
	private KbAudtiMapper kbAudtiMapper;
	@Resource
	private SqlMapper sqlMapper;

	public SqlMapper getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(SqlMapper sqlMapper) {
		this.sqlMapper = sqlMapper;
	}
	
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	public PageInfo<KbAudit> findPageInfo(Map<String, Object> params){
		 PageHelper.startPage(params);
		 List<KbAudit> list = kbAudtiMapper.findPageInfo(params);
		 return new PageInfo<KbAudit>(list);
	}
	
	/**
	 * 单条数据
	 * @param id
	 * @return
	 */
	public KbAudit findById(Long id){
		return kbAudtiMapper.findById(id);
	}
	
	/**
	 * 保存或更改审计程序数据
	 */
	public int save(KbAudit kbAudit){
		int count = 0;
		if(null == kbAudit.getAuditId()){
			kbAudit.setCreatorId(SysUserUtils.getCacheLoginUser().getName()); 
			kbAudit.setCreateTime(DateUtils.formatDateTime(new Date()));
			count = this.insertSelective(kbAudit);
		}else{
			kbAudit.setEditorId(SysUserUtils.getCacheLoginUser().getName()); 
			kbAudit.setEditTime(DateUtils.formatDateTime(new Date()));
			count = this.updateByPrimaryKeySelective(kbAudit);
		}
		return count;
	}
	
	/**
	 * 删除审计程序数据
	 * @param id
	 * @return
	 */
	public Integer deleteByRootId(Long id)
	{
		KbAudit kbAudit = this.selectByPrimaryKey(id);
		if(kbAudit != null){
			return this.deleteByPrimaryKey(id);
		}
		return null;
	}
	
	
	/**
	 * 删除附件时，审计程序数据的文档id设为空
	 * @param docId
	 * @return
	 */
	public Integer updateByDocId(Long docId){
		String sql = "update kb_audit set doc_id = null where doc_id = "+ docId;
		return sqlMapper.update(sql);
	}

	
	/**
	 * 根据审计程序前缀查找相同前缀的编码
	 * @param stdCodePre
	 * @return
	 */
	public List<Map<String, Object>> findCodes(String codePre)
	{
		String sql = "select audit_code as code from kb_audit where audit_code like '%"+codePre+"%'";
		return sqlMapper.selectList(sql);
	}
}
