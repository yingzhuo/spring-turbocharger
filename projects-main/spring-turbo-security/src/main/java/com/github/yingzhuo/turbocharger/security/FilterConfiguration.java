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
package com.github.yingzhuo.turbocharger.security;

import jakarta.servlet.Filter;
import org.springframework.lang.Nullable;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * {@link Filter} 配置单元
 * <p>
 * 本类用来配置SpringSecurity扩展过滤器，必须把此类的实现加入 {@link org.springframework.context.ApplicationContext}。
 *
 * @param <T> {@link Filter} 的类型
 * @author 应卓
 * @see org.springframework.security.web.SecurityFilterChain
 * @since 1.0.0
 */
@FunctionalInterface
public interface FilterConfiguration<T extends Filter> {

	@Nullable
	public T create();

	@Nullable
	public default Class<? extends Filter> positionInChain() {
		return BasicAuthenticationFilter.class;
	}

	/**
	 * 需要配置的{@link Filter}在{@link org.springframework.security.web.SecurityFilterChain}中的位置
	 */
	@Nullable
	public default Position position() {
		return Position.AFTER;
	}

	/**
	 * 需要配置的{@link Filter}在{@link org.springframework.security.web.SecurityFilterChain}中的位置
	 */
	public enum Position {
		BEFORE, AFTER, REPLACE
	}

	// -----------------------------------------------------------------------------------------------------------------

	record Default(Filter filter,
				   @Nullable Class<? extends Filter> positionInChain,
				   @Nullable Position position) implements FilterConfiguration<Filter> {

		@Override
		public Filter create() {
			return filter;
		}

		@Nullable
		@Override
		public Class<? extends Filter> positionInChain() {
			return positionInChain;
		}

		@Nullable
		@Override
		public Position position() {
			return this.position;
		}
	}

}
