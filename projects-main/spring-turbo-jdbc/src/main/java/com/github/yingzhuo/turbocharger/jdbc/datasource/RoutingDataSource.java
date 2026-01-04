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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由数据源
 *
 * @author 应卓
 * @see AbstractRoutingDataSource
 * @see RoutingDataSourceLookup
 * @since 3.4.1
 */
public class RoutingDataSource extends AbstractRoutingDataSource implements DataSource, InitializingBean {

	public RoutingDataSource(DataSource defaultDataSource, Map<String, DataSource> targetDataSources) {
		Assert.notNull(defaultDataSource, "defaultDataSource is required");
		Assert.notEmpty(targetDataSources, "targetDataSources is null or empty");

		super.setDefaultTargetDataSource(defaultDataSource);
		super.setTargetDataSources(new HashMap<>(targetDataSources));
		super.setDataSourceLookup(new MapDataSourceLookup());
	}

	@Nullable
	@Override
	protected Object determineCurrentLookupKey() {
		return RoutingDataSourceLookup.get();
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
	}

}
