package com.krm.dbaudit.web.sys.service;

import java.util.ArrayList;
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
import com.krm.dbaudit.web.sys.mapper.KbStdEntryMapper;
import com.krm.dbaudit.web.sys.model.KbStd;
import com.krm.dbaudit.web.sys.model.KbStdEntry;
import com.krm.dbaudit.web.util.SysUserUtils;

@Service("kbStdEntryService")
public class KbStdEntryService extends ServiceMybatis<KbStdEntry>
{
	@Resource
	private KbStdEntryMapper kbStdEntryMapper;
	@Resource
	private SqlMapper sqlMapper;

	public SqlMapper getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(SqlMapper sqlMapper) {
		this.sqlMapper = sqlMapper;
	}
	
	/**
	 * 审计依据条目列表
	 * @param params
	 * @return
	 */
	public List<KbStdEntry> findPageInfo(Map<String,Object> params){
//		 PageHelper.startPage(params);
		 return kbStdEntryMapper.findPageInfo(params);
	}
	/**
	 * 根据审计依据编码查找依据条目
	 * @param stdCodePre
	 * @return
	 */
	public List<Map<String,Object>> findEntryCodes(String stdCodePre){
		String sql = "select entry_code as entryCode from kb_std_entry where std_code = '"+stdCodePre+"'";
		return sqlMapper.selectList(sql);
	}
	
	/**
	 * 产生依据条目
	 * @param stdCode
	 * @return
	 */
	public String getEntryCode(String stdCode) {
		//获取所有该审计依据下的所有审计依据条目集合
		List<Map<String,Object>> list = this.findEntryCodes(stdCode);
		//定义审计依据条目编码的集合
		List<Integer> entryCodeList = new ArrayList<Integer>();
		if(list != null){
			for (Map<String, Object> map : list)
			{
				String str = map.get("entrycode").toString();
				int entryCode1 = Integer.parseInt(str);
				entryCodeList.add(entryCode1);
			}
		}
		String entryCode = null;
		for (int i = 1; i < 1000; i++) {
			if(!entryCodeList.contains(i)){
				entryCode = String.format("%03d", i);
				return entryCode;
			}
		}
		return entryCode;
	}
	
	
	/**
	 * 保存或编辑依据条目
	 * @param kbStdEntry
	 * @return
	 */
	public int saveKbstdEntry(KbStdEntry kbStdEntry){
		int count = 0;
		if(null == kbStdEntry.getEntryId()){
			kbStdEntry.setCreatorId(SysUserUtils.getCacheLoginUser().getName());
			kbStdEntry.setCreateTime(DateUtils.formatDateTime(new Date()));
			count = this.insertSelective(kbStdEntry);
		}else{
			kbStdEntry.setEditorId(SysUserUtils.getCacheLoginUser().getName());
			kbStdEntry.setEditTime(DateUtils.formatDateTime(new Date()));
			count = this.updateByPrimaryKeySelective(kbStdEntry);
		}
		return count;
	}
	
	/**
	 * 删除依据条目
	 * @param id
	 * @return
	 */
	public Integer deleteKbstdEntryByRootId(Long id){
		KbStdEntry kbStdEntry = this.selectByPrimaryKey(id);
		if(kbStdEntry != null){
			return this.deleteByPrimaryKey(id);
		}
		return null;
	}
}
