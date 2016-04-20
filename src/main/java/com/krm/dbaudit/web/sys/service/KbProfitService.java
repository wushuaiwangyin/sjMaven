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
import com.krm.dbaudit.web.sys.mapper.KbProfitMapper;
import com.krm.dbaudit.web.sys.model.KbProfit;
import com.krm.dbaudit.web.util.SysUserUtils;

@Service("kbProfitService")
public class KbProfitService extends ServiceMybatis<KbProfit>
{
	@Resource
	private KbProfitMapper kbProfitMapper;
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
	public PageInfo<KbProfit> findPageInfo(Map<String, Object> params){
		 PageHelper.startPage(params);
		 List<KbProfit> list = kbProfitMapper.findPageInfo(params);
		 return new PageInfo<KbProfit>(list);
	}
	
	/**
	 * 单条数据
	 * @param id
	 * @return
	 */
	public KbProfit findById(Long id){
		return kbProfitMapper.findById(id);
	}
	
	/**
	 * 根据审计成果编码前缀查找相同前缀的编码
	 * @param stdCodePre
	 * @return
	 */
	public List<Map<String,Object>> findCodes(String codePre){
		String sql = "select profit_code as code from kb_profit where profit_code like '%"+codePre+"%'";
		return sqlMapper.selectList(sql);
	}
	
	
	/**
	 * 保存或更改审计成果
	 */
	public int save(KbProfit kbProfit){
		int count = 0;
		if(null == kbProfit.getProfitId()){
			kbProfit.setCreatorId(SysUserUtils.getCacheLoginUser().getName()); 
			kbProfit.setCreateTime(DateUtils.formatDateTime(new Date()));
			count = this.insertSelective(kbProfit);
		}else{
			kbProfit.setEditorId(SysUserUtils.getCacheLoginUser().getName()); 
			kbProfit.setEditTime(DateUtils.formatDateTime(new Date()));
			count = this.updateByPrimaryKeySelective(kbProfit);
		}
		return count;
	}
	
	/**
	 * 删除审计成果
	 * @param id
	 * @return
	 */
	public Integer deleteByRootId(Long id)
	{
		KbProfit kbProfit = this.selectByPrimaryKey(id);
		if(kbProfit != null){
			return this.deleteByPrimaryKey(id);
		}
		return null;
	}
	
	
	/**
	 * 删除附件时，审计成果数据的文档id设为空
	 * @param docId
	 * @return
	 */
	public Integer updateByDocId(Long docId){
		String sql = "update kb_profit set doc_id = null where doc_id = "+ docId;
		return sqlMapper.update(sql);
	}
}
