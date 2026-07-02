package com.github.yingzhuo.turbocharger.jdbc.datasource;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

public final class RoutingDataSourceLookup {

	private static final ThreadLocal<String> DS_NAME_HOLDER = ThreadLocal.withInitial(() -> null);

	private RoutingDataSourceLookup() {
		super();
	}

	public static void set(String dataSourceName) {
		Assert.hasText(dataSourceName, "dataSourceName is required");
		DS_NAME_HOLDER.set(dataSourceName);
	}

	@Nullable
	public static String get() {
		return DS_NAME_HOLDER.get();
	}

	public static void remove() {
		DS_NAME_HOLDER.remove();
	}

}
