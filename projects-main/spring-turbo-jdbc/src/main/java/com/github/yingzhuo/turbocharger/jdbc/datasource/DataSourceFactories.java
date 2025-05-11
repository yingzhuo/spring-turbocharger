/*
 *
 * Copyright 2022-present the original author or authors.
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
 *
 */
package com.github.yingzhuo.turbocharger.jdbc.datasource;

import com.github.yingzhuo.turbocharger.jdbc.HikariProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;

/**
 * 数据源创建工具
 *
 * @author 应卓
 * @see HikariDataSource
 * @since 3.4.1
 */
@SuppressWarnings("unchecked")
public final class DataSourceFactories {

	/**
	 * 私有构造方法
	 */
	private DataSourceFactories() {
		super();
	}

	public static <T> T createDataSource(JdbcConnectionDetails connectionDetails, Class<? extends DataSource> type, @Nullable ClassLoader classLoader) {
		// @formatter:off
        classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
        return (T) DataSourceBuilder.create(classLoader)
                .type(type)
                .driverClassName(connectionDetails.getDriverClassName())
                .url(connectionDetails.getJdbcUrl())
                .username(connectionDetails.getUsername())
                .password(connectionDetails.getPassword())
                .build();
        // @formatter:on
	}

	// Hikari
	// -----------------------------------------------------------------------------------------------------------------

	public static HikariDataSource createHikariDataSource(HikariProperties connectionDetails, @Nullable ClassLoader classLoader) {
		return createHikariDataSource((JdbcConnectionDetails) connectionDetails, classLoader);
	}

	public static HikariDataSource createHikariDataSource(JdbcConnectionDetails connectionDetails, @Nullable ClassLoader classLoader) {
		final HikariDataSource ds = createDataSource(connectionDetails, HikariDataSource.class, classLoader);
		if (connectionDetails instanceof HikariProperties dataSourceConfig) {
			ds.setPoolName(dataSourceConfig.getPoolName());
			ds.setMinimumIdle(dataSourceConfig.getMinimumIdle());
			ds.setMaximumPoolSize(dataSourceConfig.getMaximumPoolSize());
			ds.setAutoCommit(dataSourceConfig.isAutoCommit());
			ds.setIdleTimeout(dataSourceConfig.getIdleTimeout());
			ds.setMaxLifetime(dataSourceConfig.getMaxLifetime());
			ds.setConnectionTimeout(dataSourceConfig.getConnectionTimeout());
			ds.setConnectionTestQuery(dataSourceConfig.getConnectionTestQuery());
			ds.setValidationTimeout(dataSourceConfig.getValidationTimeout());
			ds.setConnectionInitSql(dataSourceConfig.getConnectionInitSql());
			ds.setInitializationFailTimeout(dataSourceConfig.getInitializationFailTimeout());
		}
		return ds;
	}

}
