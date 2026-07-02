package com.github.yingzhuo.turbocharger.util.collection;

import org.jspecify.annotations.Nullable;

import java.util.Properties;

public final class PropertiesUtils {

	private PropertiesUtils() {
		super();
	}

	public static boolean isEmpty(@Nullable Properties properties) {
		return properties == null || properties.isEmpty();
	}

	public static boolean isNotEmpty(@Nullable Properties properties) {
		return !isEmpty(properties);
	}

	public static int size(@Nullable Properties properties) {
		return properties == null ? 0 : properties.size();
	}

}
