package com.krm.dbaudit.common.beetl.function;

import org.springframework.stereotype.Component;

import com.krm.dbaudit.common.utils.FileUtils;

@Component
public class FileFunction {
	
	public String formatFileSize(Long size){
		if(size == null){
			return "未知";
		}
		return FileUtils.getHumanSize(size);
	}
}
