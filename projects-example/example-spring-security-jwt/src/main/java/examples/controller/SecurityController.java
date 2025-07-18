/*
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
 */
package examples.controller;

import com.github.yingzhuo.turbocharger.jwt.JwtData;
import com.github.yingzhuo.turbocharger.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/security")
public class SecurityController {

	public final JwtService jwtService;

	@PostMapping("/login")
	public Object login(@RequestParam("username") String username, @RequestParam("password") String password) {
		if (username.equals("admin") && password.equals("admin")) {
			var jwtData = JwtData.newInstance()
				.addPayload("userId", 1L)
				.addPayload("username", "admin")
				.addPayload("roles", List.of("ROLE_ADMIN", "ROLE_USER"));

			var jwt = jwtService.createToken(jwtData);
			return Map.of(
				"token", jwt
			);
		}

		return Map.of(
			"error", "登录失败"
		);
	}

	@PostMapping("/show")
	public Object show(Authentication authentication) {
		log.debug("authorities: {}", authentication.getAuthorities());
		return Map.of();
	}

}
