package com.krm.dbaudit.web.all360.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.all360.mapper.QueryModelMapper;
import com.krm.dbaudit.web.all360.model.QueryModel;
import com.krm.dbaudit.web.all360.model.TableModelColumns;

@Service("modelManageService")
public class ModelmanageService  {
	@Resource
	private QueryModelMapper queryModelMapper;

	public PageInfo<QueryModel> getModelList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<QueryModel> list =  queryModelMapper.findPageInfo(params);
		return new PageInfo<QueryModel>(list);
	}

	public String saveModel(QueryModel qm) {
		String succ = "创建失败！";
		try {
			queryModelMapper.saveQueryModel(qm);
			succ = "创建成功!";
		} catch (Exception e) {
			succ = "创建失败！";
			e.printStackTrace();
		}
		return succ;
	}
	

}
