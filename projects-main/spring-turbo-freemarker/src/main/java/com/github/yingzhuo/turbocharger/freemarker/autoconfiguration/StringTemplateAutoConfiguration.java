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
package com.github.yingzhuo.turbocharger.freemarker.autoconfiguration;

import com.github.yingzhuo.turbocharger.freemarker.StringTemplateRenderer;
import com.github.yingzhuo.turbocharger.freemarker.StringTemplateRendererImpl;
import com.github.yingzhuo.turbocharger.freemarker.StringTemplateRendererProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 3.4.3
 */
@AutoConfiguration
@EnableConfigurationProperties(StringTemplateRendererProperties.class)
@ConditionalOnProperty(prefix = "springturbo.stringtemplate", name = "enabled", havingValue = "true", matchIfMissing = true)
public class StringTemplateAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public StringTemplateRenderer stringTemplateRenderer(StringTemplateRendererProperties properties) {
		var bean = new StringTemplateRendererImpl();
		bean.setSuffix(properties.getSuffix());
		bean.setDefaultEncoding(properties.getDefaultEncoding());
		bean.setTemplateLoaderPaths(properties.getTemplateLoaderPaths());
		return bean;
	}

}
