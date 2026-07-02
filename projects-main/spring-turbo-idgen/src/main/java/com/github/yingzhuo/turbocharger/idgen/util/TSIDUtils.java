package com.github.yingzhuo.turbocharger.idgen.util;

import com.github.yingzhuo.turbocharger.core.SpringUtils;
import com.github.yingzhuo.turbocharger.idgen.TSIDGenerator;

/**
 * TSID生成工具
 *
 * @author 应卓
 * @since 3.4.3
 */
public final class TSIDUtils {

	/**
	 * 私有构造方法
	 */
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
