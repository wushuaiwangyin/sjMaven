package com.krm.dbaudit.web.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.model.model.AuditType;
import com.krm.dbaudit.web.modeltool.oracle.dao.ToolShowTableDataImpl;
import com.krm.dbaudit.web.sys.mapper.SrcDataMapper;
import com.krm.dbaudit.web.sys.model.SrcDataTable;
import com.krm.dbaudit.web.sys.model.SrcDataType;

@Service("srcDataService")
@CacheConfig(cacheNames="srcData_cache")
public class SrcDataService extends ServiceMybatis<SrcDataType> {
	
	@Resource
	private SrcDataMapper srcDataMapper;
	
	@Resource
	private ToolShowTableDataImpl toolShowTableDataImpl;

	public List<SrcDataType> findAllSrcDataType() {
		
		SrcDataType srcDataType = new SrcDataType();
		srcDataType.setTypeFlag((long)0);
		return this.select(srcDataType,"sort");
	}

	public Integer deleteSrcDataTypeById(Long id) {
		return srcDataMapper.deleteSrcDataTypeById(id);
	}

	
	public int saveSrcDataType(SrcDataType srcDataType) {
		int count = 0;
		if(srcDataType.getId() == null){
			count = this.insertSelective(srcDataType);
		}else{
			count = this.updateByPrimaryKeySelective(srcDataType);
		}
		return count;
	}

	public PageInfo<SrcDataType> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SrcDataType> list = srcDataMapper.findPageInfo(params);
		return new PageInfo<SrcDataType>(list);
	}

	public SrcDataTable findSrcTableByName(String srctalbename) {
		return srcDataMapper.findSrcTableByName(srctalbename);
	}

	public Integer saveSrcTable(SrcDataTable srcDataTable) {
		return srcDataMapper.saveSrcTable(srcDataTable);
	}

	public List<SrcDataType> findSrcTable(Map<String, Object> params) {
		return srcDataMapper.findSrcTable(params);
	}
	
	private String rtnmsg(String code,String msg,String data){
		String result = "[{\"return_flag\":\""+code+"\"," +
				"\"return_msg\":\""+msg+"\","+
				"\"return_data\":"+data+"}]";
		return result;
	}
	
	public String savenewtable(Map<String, Object> params) {
		
		String tablename = "";
		try{
		String strJson = params.get("jsonmemo").toString();
		String result = "";
		
		JSONArray ja = JSONArray.parseArray(strJson);
		JSONObject jo = ja.getJSONObject(0);
		String actionid = jo.getString("action_id").toString().toLowerCase();
		ja = jo.getJSONArray("action_data");
		jo = ja.getJSONObject(0);
		ja = jo.getJSONArray("field");
		if(ja.size()==0)
			return rtnmsg("1","字段不能为空","null");
		
		tablename = jo.getString("tablename");
		String tablealias = jo.getString("tablealias");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tablename", tablename);
		map.put("tablealias", tablealias);
		map.put("tabletype", jo.getString("tabletype"));
		map.put("createby",params.get("createBy").toString());
		
		//表重复
		if(srcDataMapper.checkTableName(map)>0)
			return rtnmsg("1","表名重复","null");
		
		//表重复
		if(srcDataMapper.checkTableAlias(map)>0)
			return rtnmsg("1","表别名重复","null");
		
		//实体表存在，提示不能创建
		if(toolShowTableDataImpl.getTableCount(tablename)>0)
			return rtnmsg("1","实体表存在。","null");			
		
		if(srcDataMapper.writeTableInfo(map)<1)
			return rtnmsg("1","表信息写入错误","null");
		
		String createsql = "";
		for(int i=0;i<ja.size();i++)
		{
			jo = ja.getJSONObject(i);
			String fieldname = jo.getString("fieldname");
			String fieldalias = jo.getString("fieldalias");
			String fieldtype = jo.getString("fieldtype");
			String fieldlength = jo.getString("fieldlength");
			map.put("fieldname", fieldname);
			map.put("fieldalias", fieldalias);
			map.put("fieldtype", fieldtype);
			map.put("fieldlength", fieldlength);
			map.put("fieldorder", i+1);
			srcDataMapper.writeFieldInfo(map);
			
			if(fieldtype.equals("3"))
				createsql = createsql + fieldname + " number,";
			else
				createsql = createsql + fieldname + " varchar2("+fieldlength+"),";
		}
		createsql = createsql.substring(0,createsql.length()-1);
		createsql = "create table "+tablename+"("+createsql+")";
				
		//创建实体表
		toolShowTableDataImpl.runSql(createsql);
		
		//表创建完成，数据同步至MODEL正式模型配置中
		srcDataMapper.synvTableInfo(tablename);
		srcDataMapper.synvTableFieldInfo(tablename);
		
		}catch(Exception e){
			//异常清除过程数据
			if(!tablename.equals(""))
			{
				srcDataMapper.clearTable(tablename);
				srcDataMapper.clearTableField(tablename);
			}
			return rtnmsg("2","表创建异常","null");
		}
		return rtnmsg("0","表创建成功","null");
	}

}
