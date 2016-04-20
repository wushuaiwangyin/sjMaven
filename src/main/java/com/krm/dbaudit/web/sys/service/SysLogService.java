

package com.krm.dbaudit.web.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.sys.mapper.SysLogMapper;
import com.krm.dbaudit.web.sys.model.SysLog;
import com.krm.dbaudit.web.sys.model.SysUser;

@Service("sysLogService")
public class SysLogService extends ServiceMybatis<SysLog> {

	@Resource
	private SysLogMapper sysLogMapper;
	
	/**
	 *新增或更新SysLog
	 */
	public int saveSysLog(SysLog sysLog){
		int count = 0;
		if(null == sysLog.getId()){
			count = this.insertSelective(sysLog);
		}else{
			count = this.updateByPrimaryKeySelective(sysLog);
		}
	    return count;
	}
	
	/**
	 * 根据条件分页查询SysLog列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public List<SysLog> findSysLogList(Map<String,Object> params) {
        List<SysLog> list = sysLogMapper.findSysLogList(params);
        return list;
	}

	public PageInfo<SysLog> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SysLog> list = sysLogMapper.findPageInfo(params);
		return new PageInfo<SysLog>(list);
	}

	public SysLog getById(Long id) {
		return sysLogMapper.getById(id);
	}
	

}
