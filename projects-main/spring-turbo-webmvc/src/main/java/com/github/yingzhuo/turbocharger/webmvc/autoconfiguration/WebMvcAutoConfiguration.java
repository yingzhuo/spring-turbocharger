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
package com.github.yingzhuo.turbocharger.webmvc.autoconfiguration;

import com.github.yingzhuo.turbocharger.webmvc.SpringTurboWebMvcProperties;
import com.github.yingzhuo.turbocharger.webmvc.databinding.DataBinderInitializingAdvice;
import com.github.yingzhuo.turbocharger.webmvc.support.argument.RemoteAddressHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import java.util.List;

/**
 * @author 应卓
 * @since 1.3.0
 */
@EnableConfigurationProperties(SpringTurboWebMvcProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

	@Autowired(required = false)
	public void configBeanNameViewResolver(@Nullable BeanNameViewResolver resolver) {
		if (resolver != null) {
			resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new RemoteAddressHandlerMethodArgumentResolver());
	}

	/**
	 * @since 2024-06-30
	 */
	@Bean
	@ConditionalOnProperty(prefix = "springturbo.webmvc", name = "data-binder-initializing-advice", havingValue = "true", matchIfMissing = true)
	public DataBinderInitializingAdvice dataBinderInitializingAdvice() {
		return new DataBinderInitializingAdvice();
	}

}
