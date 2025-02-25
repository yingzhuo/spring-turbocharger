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

import java.util.HashMap;
import java.util.Map;

import static com.github.yingzhuo.turbocharger.jwt.JwtConstants.*;

/**
 * 验证JWT时，对Claim断言
 *
 * @author 应卓
 * @see #newInstance()
 * @see JwtConstants
 * @see ValidatingResult#INVALID_CLAIM
 * @since 3.3.2
 */
public final class JwtAssertions extends HashMap<String, Object> implements Map<String, Object> {

	/**
	 * 私有构造方法
	 */
	private JwtAssertions() {
		super();
	}

	/**
	 * 创建对象
	 *
	 * @return JwtAssertions实例
	 */
	public static JwtAssertions newInstance() {
		return new JwtAssertions();
	}

	/**
	 * 添加断言 jti为期望值
	 *
	 * @param expectedId 期望值
	 * @return this
	 */
	public JwtAssertions requireId(String expectedId) {
		return addAssertion(PAYLOAD_JWT_ID, expectedId);
	}

	/**
	 * 添加断言 sub为期望值
	 *
	 * @param expectedSubject 期望值
	 * @return this
	 */
	public JwtAssertions requireSubject(Object expectedSubject) {
		return addAssertion(PAYLOAD_SUBJECT, expectedSubject);
	}

	/**
	 * 添加断言 iss为期望值
	 *
	 * @param expectedIssuer 期望值
	 * @return this
	 */
	public JwtAssertions requireIssuer(Object expectedIssuer) {
		return addAssertion(PAYLOAD_ISSUER, expectedIssuer);
	}

	/**
	 * 添加断言
	 *
	 * @param claimName  字段名
	 * @param claimValue 字段值
	 * @return this
	 */
	public JwtAssertions addAssertion(String claimName, Object claimValue) {
		this.put(claimName, claimValue);
		return this;
	}

}
