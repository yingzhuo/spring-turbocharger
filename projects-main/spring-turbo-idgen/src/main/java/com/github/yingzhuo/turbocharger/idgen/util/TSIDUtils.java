package com.github.yingzhuo.turbocharger.idgen.util;

import com.github.yingzhuo.turbocharger.core.SpringUtils;
import com.github.yingzhuo.turbocharger.idgen.TSIDGenerator;

public final class TSIDUtils {

	private TSIDUtils() {
		super();
	}

	public static long generateLong() {
		return SpringUtils.getRequiredBean(TSIDGenerator.class)
			.generateLong();
	}

	public static String generateString() {
		return SpringUtils.getRequiredBean(TSIDGenerator.class)
			.generateString();
	}

}
