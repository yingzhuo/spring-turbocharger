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
package com.github.yingzhuo.turbocharger.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class JwtServiceTest {

	@Test
	void test() {
		Algorithm algorithm = Algorithm.HMAC256("secret");

		var service = new JwtServiceImpl(algorithm);
		var jwtData = JwtData.newInstance()
			.addPayloadExpiresAtFuture(Duration.ofHours(24))
			.addPayload("foo", "foo")
			.addPayload("bar", "bar")
			;

		var token = service.createToken(jwtData);

		System.out.println("---");
		System.out.println(token);
		System.out.println("---");
	}

}
