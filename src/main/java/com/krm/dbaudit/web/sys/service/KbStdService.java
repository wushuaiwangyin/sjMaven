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
import com.krm.dbaudit.web.sys.mapper.KbStdMapper;
import com.krm.dbaudit.web.sys.model.KbStd;
import com.krm.dbaudit.web.util.SysUserUtils;

/**
 * 
 * @author Parker
 *
 */
@Service("kbStdService")
public class KbStdService extends ServiceMybatis<KbStd>
{
	@Resource
	private KbStdMapper kbStdMapper;
	@Resource
	private SqlMapper sqlMapper;

	public SqlMapper getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(SqlMapper sqlMapper) {
		this.sqlMapper = sqlMapper;
	}
	/**
	 * 依据文件列表
	 * @param params
	 * @return
	 */
	public PageInfo<KbStd> findPageInfo(Map<String,Object> params) {
        PageHelper.startPage(params);
        List<KbStd> list=kbStdMapper.findPageInfo(params);
        for (KbStd kbStd : list)
		{
			String fileName = kbStd.getFileName().toString();
        	int length = fileName.length();
			if(length > 40){
				String name = fileName.substring(0,40)+"……"+fileName.substring(length-6, length);
				kbStd.setFileName(name);
			}
		}
        return new PageInfo<KbStd>(list);
	}
	
	/**
	 * 查询一条审计依据
	 * @param id
	 * @return
	 */
	public KbStd findById(Long id){
		return kbStdMapper.findById(id);
	}
	
	/**
	 * 保存或更改审计依据
	 */
	public int save(KbStd kbStd){
		int count = 0;
		if(null == kbStd.getStdId()){
			kbStd.setCreatorId(SysUserUtils.getCacheLoginUser().getName()); 
			kbStd.setCreateTime(DateUtils.formatDateTime(new Date()));
			count = this.insertSelective(kbStd);
		}else{
			kbStd.setEditorId(SysUserUtils.getCacheLoginUser().getName()); 
			kbStd.setEditTime(DateUtils.formatDateTime(new Date()));
			count = this.updateByPrimaryKeySelective(kbStd);
		}
		return count;
	}

	/**
	 * 根据审计依据编码前缀查找相同前缀的编码
	 * @param stdCodePre
	 * @return
	 */
	public List<Map<String,Object>> findCodes(String codePre){
		String sql = "select std_code as code from kb_std where std_code like '%"+codePre+"%'";
		return sqlMapper.selectList(sql);
	}

	/**
	 * 删除审计依据
	 * @param id
	 * @return
	 */
	public Integer deleteKbstdByRootId(Long id)
	{
		KbStd kbStd = kbStdMapper.findById(id);
		if(kbStd != null){
			return this.deleteByPrimaryKey(id);
		}
		return null;
	}
	
	
	/**
	 * 删除附件时，审计依据数据的文档id设为空
	 * @param docId
	 * @return
	 */
	public Integer updateByDocId(Long docId){
		String sql = "update kb_std set doc_id = null where doc_id = "+ docId;
		return sqlMapper.update(sql);
	}
}
