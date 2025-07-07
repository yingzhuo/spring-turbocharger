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
package com.github.yingzhuo.turbocharger.security.filter.factory;

import com.github.yingzhuo.turbocharger.security.FilterConfiguration;
import com.github.yingzhuo.turbocharger.security.authentication.TokenToUserConverter;
import com.github.yingzhuo.turbocharger.security.exception.SecurityExceptionHandler;
import com.github.yingzhuo.turbocharger.security.filter.JwtTokenAuthenticationFilter;
import com.github.yingzhuo.turbocharger.security.token.resolver.BearerTokenResolver;
import com.github.yingzhuo.turbocharger.security.token.resolver.TokenResolver;
import com.github.yingzhuo.turbocharger.security.token.blacklist.TokenBlacklistManager;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @since 3.3.1
 */
public class JwtTokenAuthenticationFilterFactoryBean implements FactoryBean<FilterConfiguration<Filter>>, InitializingBean {

	private FilterConfiguration.Position position = FilterConfiguration.Position.AFTER;
	private Class<? extends Filter> positionInChain = org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class;
	private TokenResolver tokenResolver = new BearerTokenResolver();
	private @Nullable TokenToUserConverter tokenToUserConverter;
	private @Nullable ApplicationEventPublisher applicationEventPublisher;
	private @Nullable AuthenticationEntryPoint authenticationEntryPoint;
	private @Nullable RememberMeServices rememberMeServices;
	private @Nullable TokenBlacklistManager tokenBlacklistManager;

	/**
	 * 默认构造方法
	 */
	public JwtTokenAuthenticationFilterFactoryBean() {
	}

	@Override
	@SuppressWarnings("DuplicatedCode")
	public FilterConfiguration<Filter> getObject() {
		Assert.notNull(tokenToUserConverter, "tokenToUserConverter is required");

		var filter = new JwtTokenAuthenticationFilter();
		filter.setTokenResolver(tokenResolver);
		filter.setTokenToUserConverter(tokenToUserConverter);
		filter.setApplicationEventPublisher(applicationEventPublisher);
		filter.setAuthenticationEntryPoint(authenticationEntryPoint);
		filter.setRememberMeServices(rememberMeServices);
		filter.setTokenBlacklistManager(tokenBlacklistManager);
		return new FilterConfiguration.Default(filter, positionInChain, position);
	}

	@Override
	public Class<?> getObjectType() {
		return FilterConfiguration.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(tokenToUserConverter, "tokenToUserConverter is required");
	}

	public void setPosition(FilterConfiguration.Position position) {
		this.position = position;
	}

	public void setPositionInChain(Class<? extends Filter> positionInChain) {
		this.positionInChain = positionInChain;
	}

	public void setTokenResolver(TokenResolver tokenResolver) {
		this.tokenResolver = tokenResolver;
	}

	public void setTokenToUserConverter(TokenToUserConverter tokenToUserConverter) {
		this.tokenToUserConverter = tokenToUserConverter;
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	public void setSecurityExceptionHandler(SecurityExceptionHandler securityExceptionHandler) {
		this.setAuthenticationEntryPoint((AuthenticationEntryPoint) securityExceptionHandler);
	}

	public void setRememberMeServices(RememberMeServices rememberMeServices) {
		this.rememberMeServices = rememberMeServices;
	}

	public void setTokenBlacklistManager(TokenBlacklistManager tokenBlacklistManager) {
		this.tokenBlacklistManager = tokenBlacklistManager;
	}

}
