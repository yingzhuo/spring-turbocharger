/*
 * Copyright 2025-present the original author or authors.
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
package com.github.yingzhuo.turbocharger.jackson.autoconfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.turbocharger.jackson.util.JsonUtils;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 初始化 {@link JsonUtils}
 *
 * @author 应卓
 * @since 3.3.1
 */
@ConditionalOnClass(name = {
	"com.fasterxml.jackson.databind.ObjectMapper",
	"com.jayway.jsonpath.Configuration"
})
@ConditionalOnBean(ObjectMapper.class)
public class JsonPathAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public Configuration jsonPathConfiguration(ObjectMapper om) {
		return Configuration.builder()
			.jsonProvider(new JacksonJsonProvider(om))
			.mappingProvider(new JacksonMappingProvider(om))
			.build();
	}

}
