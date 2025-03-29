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

import com.github.yingzhuo.turbocharger.jwt.alg.JwtSignerFactories;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class JwtServiceTest {

	@Test
	void test() {
		var service = new JwtServiceImpl(JwtSignerFactories.createFromBase64EncodedString("3mDk7egxOtYe3oDEiZAhdZ2+ZdfPu8zsYtSl500l004="));

		var token = service.createToken(
			JwtData.newInstance()
				.addPayload("name", "应卓")
				.addPayloadExpiresAtFuture(Duration.ofHours(1L))
		);

		var result = service.validateToken(token, JwtAssertions.newInstance().addAssertion("hello", "world"));
		Assertions.assertEquals(ValidatingResult.INVALID_CLAIM, result);
	}

}
