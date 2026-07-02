package com.github.yingzhuo.turbocharger.util.collection;

import org.jspecify.annotations.Nullable;

import java.util.Properties;

/**
 * @author 应卓
 * @since 3.4.4
 */
public final class PropertiesUtils {

	/**
	 * 私有构造方法
	 */
	private PropertiesUtils() {
		super();
	}

	/**
	 * 判断Properties实例是否为空
	 *
	 * @param properties Properties实例
	 * @return 为空时返回true
	 */
	public static boolean isEmpty(@Nullable Properties properties) {
		return properties == null || properties.isEmpty();
	}

	/**
	 * 判断Properties实例是否为不空
	 *
	 * @param properties Properties实例
	 * @return 不空时返回true
	 */
	public static boolean isNotEmpty(@Nullable Properties properties) {
		return !isEmpty(properties);
	}

	/**
	 * 得出Properties实例包含的条目数
	 *
	 * @param properties Properties实例
	 * @return 条目数
	 */
	public static int size(@Nullable Properties properties) {
		return properties == null ? 0 : properties.size();
	}

}
