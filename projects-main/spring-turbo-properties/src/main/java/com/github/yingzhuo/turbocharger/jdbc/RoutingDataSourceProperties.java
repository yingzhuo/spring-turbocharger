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
package com.github.yingzhuo.turbocharger.jdbc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 应卓
 * @since 3.4.1
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "springturbo.routing-data-source")
public class RoutingDataSourceProperties implements Serializable, InitializingBean {

	private boolean enabled = true;
	private String defaultDataSourceName;
	private Map<String, HikariProperties> hikariDataSources;

	@Override
	public void afterPropertiesSet() {
		Assert.hasText(defaultDataSourceName, "defaultDataSourceName is required");
		Assert.notEmpty(hikariDataSources, "hikariDataSources is empty");
	}

}
