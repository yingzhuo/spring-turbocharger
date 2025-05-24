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
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class JwtTokenToUserConverter extends AbstractJwtTokenToUserConverter implements TokenToUserConverter {

	public JwtTokenToUserConverter(JwtService jwtService) {
		super(jwtService);
	}

	@Nullable
	@Override
	protected UserDetails doAuthenticate(String rawToken, String headerJson, String payloadJson) throws AuthenticationException {
		return JsonUtils.parseJson(payloadJson, UserAttributes.class);
	}

	// -----------------------------------------------------------------------------------------------------------------

	@Data
	public static class UserAttributes implements UserDetails {

		private static final String NO_PASS = UserAttributes.class.getName() + "#NO_PASS";

		private String userId;
		private String username;
		private String[] roles;

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			var roles = this.getRoles();
			if (roles == null) {
				return List.of();
			}

			return Arrays.stream(roles)
				.map(SimpleGrantedAuthority::new)
				.toList();
		}

		@Override
		public String getPassword() {
			return NO_PASS;
		}
	}

}
