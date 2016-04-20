package com.krm.dbaudit.web.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.web.model.mapper.ModelBaseMapper;
import com.krm.dbaudit.web.model.mapper.ModelPropertyMapper;
import com.krm.dbaudit.web.model.model.ModelBase;
import com.krm.dbaudit.web.model.model.ModelProperty;
import com.krm.dbaudit.web.util.SysUserUtils;
import com.krm.dbaudit.web.util.state.ModelStatus;



/**
* @author taosq on 2015-08-13
*/

@Service("modelBaseService")
public class ModelBaseService extends ServiceMybatis<ModelBase>{

	@Resource
	private ModelBaseMapper modelBaseMapper;
	
	@Resource
	private ModelPropertyMapper modelPropertyMapper;
	
	public int saveModelBase(ModelBase modelBase){
		
		//解决属性一致问题
		ModelProperty mp = modelPropertyMapper.selectByPrimaryKey(modelBase.getProperty());
		if(mp == null){
			return -1;
		}
		if(mp.getHaveLine() == 0){
			modelBase.setBuzLine("");
		}
		if(mp.getHaveSubject() == 0){
			modelBase.setSubject(null);
		}
		
		int count = 0;
		if(modelBase.getId() == null){
			modelBase.setStatus(ModelStatus.noSubmit.getValue());
			count = this.insertSelective(modelBase);
		}else{
			count = this.updateByPrimaryKeySelective(modelBase);
		}
		return count;
	}
	
	/**
	 * 删除
	* @param id
	* @return
	 */
	public int deleteModelBase(Long id){
		return this.updateDelFlagToDelStatusById(ModelBase.class, id);
	}
	
	
	public Integer deleteModelBase(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteModelBase(id);
		}
		return count;
	}
	
	
	/**
	 * 列表
	* @param params
	* @return
	 */
	public PageInfo<ModelBase> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ModelBase> list = modelBaseMapper.findPageInfo(params);
		return new PageInfo<ModelBase>(list);
	}
	
	public ModelBase findById(Long id){
		return modelBaseMapper.findById(id);
	}
	
	public List<ModelBase> findByIds(Map<String, Object> params){
		return modelBaseMapper.findByIds(params);
	}

	public List<ModelBase> findModelList(Map<String, Object> params) {
		return  modelBaseMapper.findPageInfo(params);
	}
	

	
	//提交(old)
	public Integer submit(Long[] ids) {
		Long uid = SysUserUtils.getCacheLoginUser().getId();
		int count = 0;
		for (Long id : ids) {
			ModelBase model = modelBaseMapper.selectByPrimaryKey(id);
			if(model.getCreateBy().split(",")[0].equals(uid+"")){
				ModelBase m = new ModelBase();
				m.setStatus(ModelStatus.submited.getValue());
				m.setId(id);
				count += this.updateByPrimaryKeySelective(m);
			}else{
				return -1;//没权限
			}
		}
		return count;
	}
	
	//提交
	public int submismodel(Map<String, Object> params) {
		if(modelBaseMapper.checkmodelcode(params)>0)
			return -1;	//编号重复
		if(modelBaseMapper.checkmodelname(params)>0)
			return -2;
		return  modelBaseMapper.submismodels(params);
	}
}
