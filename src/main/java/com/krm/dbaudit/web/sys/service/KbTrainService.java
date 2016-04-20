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
import com.krm.dbaudit.web.sys.mapper.KbTrainMapper;
import com.krm.dbaudit.web.sys.model.KbTrain;
import com.krm.dbaudit.web.util.SysUserUtils;

@Service("kbTrainService")
public class KbTrainService extends ServiceMybatis<KbTrain>
{
	@Resource
	private KbTrainMapper kbTrainMapper;
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
	public PageInfo<KbTrain> findPageInfo(Map<String, Object> params){
		 PageHelper.startPage(params);
		 List<KbTrain> list = kbTrainMapper.findPageInfo(params); 
		 return new PageInfo<KbTrain>(list);
	}
	
	/**
	 * 单条数据
	 * @param id
	 * @return
	 */
	public KbTrain findById(Long id){
		return this.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据审计成果编码前缀查找相同前缀的编码
	 * @param stdCodePre
	 * @return
	 */
	public List<Map<String,Object>> findCodes(String codePre){
		String sql = "select train_code as code from kb_train where train_code like '%"+codePre+"%'";
		return sqlMapper.selectList(sql);
	}
	
	
	/**
	 * 保存或更改审计成果
	 */
	public int save(KbTrain kbTrain){
		int count = 0;
		if(null == kbTrain.getTrainId()){
			kbTrain.setCreatorId(SysUserUtils.getCacheLoginUser().getName()); 
			kbTrain.setCreateTime(DateUtils.formatDateTime(new Date()));
			count = this.insertSelective(kbTrain);
		}else{
			kbTrain.setEditorId(SysUserUtils.getCacheLoginUser().getName()); 
			kbTrain.setEditTime(DateUtils.formatDateTime(new Date()));
			count = this.updateByPrimaryKeySelective(kbTrain);
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
		KbTrain kbTrain = this.selectByPrimaryKey(id);
		if(kbTrain != null){
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
