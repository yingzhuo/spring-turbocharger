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
package examples.security;

import com.github.yingzhuo.turbocharger.jackson.util.JsonUtils;
import com.github.yingzhuo.turbocharger.jwt.JwtService;
import com.github.yingzhuo.turbocharger.security.authentication.TokenToUserConverter;
import com.github.yingzhuo.turbocharger.security.jwt.AbstractJwtTokenToUserConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class JwtTokenToUserConverter extends AbstractJwtTokenToUserConverter implements TokenToUserConverter {

	public JwtTokenToUserConverter(JwtService jwtService) {
		super(jwtService);
	}

	@Nullable
	@Override
	protected UserDetails doAuthenticate(String rawToken, String headerJson, String payloadJson) throws AuthenticationException {
		var ua = JsonUtils.parseJson(payloadJson, UserAttributes.class);

		var rolesToUse = Stream.of(ua.roles)
			.map(role -> {
				if (role.startsWith("ROLE_")) {
					return role.substring("ROLE_".length());
				} else {
					return role;
				}
			})
			.toList()
			.toArray(new String[0]);

		return User.builder()
			.username(ua.getUsername())
			.password("WhatEver")
			.roles(rolesToUse)
			.build();
	}

	// -----------------------------------------------------------------------------------------------------------------

	@Getter
	@Setter
	public static class UserAttributes {
		private String userId;
		private String username;
		private String[] roles;
	}

}
