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

/**
 * Jwt相关常量
 *
 * @author 应卓
 * @since 3.3.2
 */
public final class JwtConstants {

	// headers
	// -----------------------------------------------------------------------------------------------------------------
	public static final String HEADER_TYPE = "typ";
	public static final String HEADER_KEY_ID = "kid";
	public static final String HEADER_ALGORITHM = "alg";
	public static final String HEADER_CONTENT_TYPE = "cty";

	// payload
	// -----------------------------------------------------------------------------------------------------------------
	public static final String PAYLOAD_ISSUER = "iss";
	public static final String PAYLOAD_SUBJECT = "sub";
	public static final String PAYLOAD_AUDIENCE = "aud";
	public static final String PAYLOAD_EXPIRES = "exp";
	public static final String PAYLOAD_NOT_BEFORE = "nbf";
	public static final String PAYLOAD_ISSUED_AT = "iat";
	public static final String PAYLOAD_JWT_ID = "jti";

	/**
	 * 私有构造方法
	 */
	private JwtConstants() {
		super();
	}

}
