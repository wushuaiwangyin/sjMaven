package com.krm.dbaudit.common.beetl.function;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.krm.dbaudit.common.constant.Constant;
import com.krm.dbaudit.common.utils.CacheUtils;
import com.krm.dbaudit.common.utils.StringConvert;
import com.krm.dbaudit.web.sys.model.SysDict;
import com.krm.dbaudit.web.sys.model.SysOffice;
import com.krm.dbaudit.web.sys.service.SysDictService;
import com.krm.dbaudit.web.sys.service.SysOfficeService;
import com.krm.dbaudit.web.util.SysUserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
public class OfficeFunctions {
	
	@Resource
	private SysOfficeService sysOfficeService;
	@Resource
	private SysDictService sysDictService;
	
	/**
	 * 全部机构 key:机构id  value:机构对象
	* @return
	 */
	public Map<Long, SysOffice> getAllOfficeMap(){
		Map<Long, SysOffice> allOfficeMap = CacheUtils.get(Constant.CACHE_SYS_OFFICE, "allOffice");
		if(allOfficeMap == null){
			allOfficeMap = Maps.newHashMap();
			List<SysOffice> list = sysOfficeService.select(new SysOffice());
			if(list!=null && list.size()>0){
				for(SysOffice so : list){
					allOfficeMap.put(so.getId(), so);
				}
			}
			CacheUtils.put(Constant.CACHE_SYS_OFFICE, "allOffice", allOfficeMap);
		}
		return allOfficeMap;
	}
	
	/**
	 * 根据部门id拼接所属机构层级字符
	* @param officeId 部门id
	* @param offices 全部机构map
	* @return
	 */
	public String getOfficeStrByOfficeId(Long officeId){
		Map<Long, SysOffice> offices = getAllOfficeMap();
		String str = "";
		if(offices!=null){
			if(offices.get(officeId) != null){
				
				Long pid = offices.get(officeId).getParentId();
				while(pid != null){
					SysOffice so = (SysOffice)offices.get(StringConvert.toLong(pid));
					if(so!=null)str+=so.getName()+" - ";
					pid = offices.get(pid) == null ? null : offices.get(pid).getParentId();
				}
				
				//mysql 有这个字段，oracle中不需要这个字段
//				String[] pids = ((SysOffice)offices.get(officeId)).getParentIds().split(",");
//				for(String id : pids){
//					SysOffice so = (SysOffice)offices.get(StringConvert.toLong(id));
//					if(so!=null)str+=so.getName()+" - ";
//				}
				SysOffice so = (SysOffice)offices.get(officeId);
				str+=so.getName();
			}
		}
		return str;
	}
	
	/**
	 * 得到用户机构
	* @return
	 */
	public List<SysOffice> getUserOfficeList(){
		return SysUserUtils.getUserOffice();
	}
	
	/**
	 * 用户持有数据范围
	 */
	public List<SysDict> getUserDataScope(){
		List<String> values = SysUserUtils.getUserDataScope();
		Collection<SysDict> dicts = sysDictService.findAllMultimap().get("sys_data_scope");
		List<SysDict> resultList = Lists.newArrayList();
		for(SysDict dict : dicts){
			if(values.contains(dict.getValue())){
				resultList.add(dict);
			}
		}
		return resultList;
	}
	
}
