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
package com.github.yingzhuo.turbocharger.jdbc.autoconfiguration;

import com.github.yingzhuo.turbocharger.jdbc.datasource.DataSourceFactories;
import com.github.yingzhuo.turbocharger.jdbc.datasource.DataSourceSwitchAdvice;
import com.github.yingzhuo.turbocharger.jdbc.datasource.RoutingDataSource;
import com.github.yingzhuo.turbocharger.jdbc.datasource.RoutingDataSourceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author 应卓
 * @since 3.4.1
 */
@EnableConfigurationProperties(RoutingDataSourceProperties.class)
@ConditionalOnProperty(prefix = "springturbo.routing-data-source", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RoutingDataSourceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(RoutingDataSource.class)
	public DataSource dataSource(RoutingDataSourceProperties props) {
		final var targetDataSources = new HashMap<String, DataSource>();

		props.getHikariDataSources().forEach((dataSourceName, dataSourceConfig) -> {
			var dataSource = DataSourceFactories.createHikariDataSource(dataSourceConfig, null);
			targetDataSources.put(dataSourceName, dataSource);
		});

		var defaultDataSource = targetDataSources.get(props.getDefaultDataSourceName());
		Assert.notNull(defaultDataSource, "unable to find defaultDataSource");

		return new RoutingDataSource(defaultDataSource, targetDataSources);
	}

	@Bean
	@ConditionalOnClass(name = "org.aspectj.lang.annotation.Aspect")
	@ConditionalOnMissingBean(DataSourceSwitchAdvice.class)
	public DataSourceSwitchAdvice dataSourceSwitchAdvice() {
		return new DataSourceSwitchAdvice(Ordered.HIGHEST_PRECEDENCE);
	}

}
