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
package com.github.yingzhuo.turbocharger.security.authentication;

import com.github.yingzhuo.turbocharger.security.token.StringToken;
import com.github.yingzhuo.turbocharger.security.token.Token;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.github.yingzhuo.turbocharger.util.collection.CollectionUtils.nullSafeAddAll;

/**
 * @author 应卓
 * @since 2.2.2
 */
public class MapTokenToUserConverter implements TokenToUserConverter {

	private final Map<String, UserDetails> tokenToUserMap = new HashMap<>();

	/**
	 * 默认构造方法
	 */
	public MapTokenToUserConverter() {
	}

	public MapTokenToUserConverter(String rawToken, UserDetails userDetails) {
		this(Map.of(rawToken, userDetails));
	}

	public MapTokenToUserConverter(Map<String, UserDetails> tokenToUserMap) {
		nullSafeAddAll(this.tokenToUserMap, tokenToUserMap);
	}

	public MapTokenToUserConverter add(String rawToken, UserDetails userDetails) {
		this.tokenToUserMap.put(rawToken, userDetails);
		return this;
	}

	@Nullable
	@Override
	public UserDetails convert(@Nullable Token token) throws AuthenticationException {
		if (tokenToUserMap.isEmpty()) {
			return null;
		}

		if (token instanceof StringToken stringToken) {
			return this.tokenToUserMap.get(stringToken.asString());
		}
		return null;
	}

	public Map<String, UserDetails> getTokenToUserMap() {
		return Collections.unmodifiableMap(tokenToUserMap);
	}

}
