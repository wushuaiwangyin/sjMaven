package com.krm.dbaudit.web.util;

import org.apache.commons.lang3.StringUtils;

public class Test {

	public static void main(String[] args) {
		Long[] roles = new Long[]{16L,18L};
		
		System.out.println(StringUtils.join(roles,','));
	}
}
