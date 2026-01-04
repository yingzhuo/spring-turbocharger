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
package com.github.yingzhuo.turbocharger.security.authentication;

import com.github.yingzhuo.turbocharger.security.token.StringToken;
import com.github.yingzhuo.turbocharger.security.token.Token;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * {@link Token} 对象到 {@link UserDetails} 的转换器
 *
 * @author 应卓
 * @see Token
 * @see StringToken
 * @see UserDetails
 * @see org.springframework.security.core.userdetails.AuthenticationUserDetailsService
 * @since 1.0.0
 */
@FunctionalInterface
public interface TokenToUserConverter extends Converter<Token, UserDetails> {

	/**
	 * 将令牌转换为UserDetails
	 *
	 * @param token 令牌实体
	 * @return {@link UserDetails} 实例，为 {@code null}时，等同于认证失败
	 * @throws AuthenticationException 认证失败
	 */
	@Nullable
	public UserDetails convert(@Nullable Token token) throws AuthenticationException;

}
