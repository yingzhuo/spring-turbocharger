/*
 * Copyright 2022-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.turbocharger.jdbc.datasource;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * 路由数据源查找策略
 *
 * @author 应卓
 * @since 3.4.1
 */
public final class RoutingDataSourceLookup {

	private static final ThreadLocal<String> DS_NAME_HOLDER = ThreadLocal.withInitial(() -> null);

	/**
	 * 私有构造方法
	 */
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
