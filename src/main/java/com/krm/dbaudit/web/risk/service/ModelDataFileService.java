package com.krm.dbaudit.web.risk.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.abel533.sql.SqlMapper;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.risk.mapper.ModelDataFileMapper;
import com.krm.dbaudit.web.risk.model.ModelDataFile;

@Service("modelDataFileService")
public class ModelDataFileService extends ServiceMybatis<ModelDataFile>
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
	private ModelDataFileMapper modelDataFileMapper;
	
	
	public Long generateId(){
		return modelDataFileMapper.generateId();
	}
	
	public int saveFile(ModelDataFile modelDataFile){
		return modelDataFileMapper.saveFile(modelDataFile);
	}
	
	public ModelDataFile findById(Long id){
		return this.selectByPrimaryKey(id);
	}
	
	public Map<String,Object> getFile(Long id){
		String sql = "select * from model_data_file where id = "+id;
		return sqlMapper.selectOne(sql);
	}
}
