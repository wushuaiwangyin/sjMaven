package com.krm.dbaudit.web.all360.model;

import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;
@Table(name="SYS360_MODELS_CONFIG")
public class QueryModel extends BaseEntity{

	/**
	 * 查询模型配置表
	 */
	private static final long serialVersionUID = 3840153807628634605L;
	private long modelId;
	private String modelName;
	private String modelPath;
	public long getModelId() {
		return modelId;
	}
	public void setModelId(long modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelPath() {
		return modelPath;
	}
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}


}
