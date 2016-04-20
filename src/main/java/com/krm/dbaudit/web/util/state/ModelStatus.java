package com.krm.dbaudit.web.util.state;

/**
 * 
 * 模型状态 枚举类型.
 * 
 * @author taosq on 2015-08-13
 * 
 */
public enum ModelStatus {
	noSubmit(1, "未提交"),
	submited(2, "已提交"),
	validateFail(3, "验证失败"),
	validated(4, "已验证"),
	approveFail(5, "审批失败"),
	approved(6, "已审批"),
	publishFail(7, "发布失败"),
	published(8, "已发布");

	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	ModelStatus(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * 获取值
	 * @return value
	 */
	public Integer getValue() {
		return value;
	}

	/**
     * 获取描述信息
     * @return description
     */
	public String getDescription() {
		return description;
	}

	public static ModelStatus getModelStatus(Integer value) {
		if (null == value)
			return null;
		for (ModelStatus _enum : ModelStatus.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static ModelStatus getModelStatus(String description) {
		if (null == description)
			return null;
		for (ModelStatus _enum : ModelStatus.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}