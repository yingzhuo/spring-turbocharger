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
