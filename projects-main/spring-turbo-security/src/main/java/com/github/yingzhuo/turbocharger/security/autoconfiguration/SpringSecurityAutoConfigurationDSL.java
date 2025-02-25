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
package com.github.yingzhuo.turbocharger.security.autoconfiguration;

import com.github.yingzhuo.turbocharger.security.FilterConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * SpringSecurity DSL
 *
 * @author 应卓
 * @since 1.3.0
 */
@SuppressWarnings("unchecked")
public class SpringSecurityAutoConfigurationDSL extends AbstractHttpConfigurer<SpringSecurityAutoConfigurationDSL, HttpSecurity> {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		var ctx = http.getSharedObject(ApplicationContext.class);

		// 核心配置 (多个)
		var configurations = ctx.getBeansOfType(FilterConfiguration.class).values();

		for (var configuration : configurations) {

			// 获取过滤器实例
			var filter = configuration.create();
			if (filter == null) {
				continue;
			}

			// 尝试初始化
			if (filter instanceof InitializingBean initializingBean) {
				initializingBean.afterPropertiesSet();
			}

			var position = configuration.positionInChain();
			var beforeOrAfter = configuration.position();

			if (position == null || beforeOrAfter == null) {
				continue;
			} else {
				switch (beforeOrAfter) {
					case BEFORE -> http.addFilterBefore(filter, position);
					case AFTER -> http.addFilterAfter(filter, position);
					case REPLACE -> http.addFilterAt(filter, position);
				}
			}
		}
	}

}
