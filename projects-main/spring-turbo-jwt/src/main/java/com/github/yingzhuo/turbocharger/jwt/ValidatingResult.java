/*
 * Copyright 2025-present the original author or authors.
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
package com.github.yingzhuo.turbocharger.jwt;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 验证结果
 *
 * @author 应卓
 * @see JwtService#validateToken(String)
 * @since 3.3.2
 */
public enum ValidatingResult implements Serializable {

	/**
	 * 没有错误
	 */
	OK,

	/**
	 * 令牌格式不合法
	 */
	INVALID_JWT_FORMAT,

	/**
	 * 令牌签名不合法
	 */
	INVALID_SIGNATURE,

	/**
	 * 令牌相关时间不合法
	 *
	 * @see JwtData#addPayloadExpiresAt(LocalDateTime)
	 * @see JwtData#addPayloadExpiresAtFuture(Duration)
	 * @see JwtData#addPayloadNotBefore(LocalDateTime)
	 * @see JwtData#addPayloadNotBeforeAtFuture(Duration)
	 */
	INVALID_TIME,

	/**
	 * 被断言的Claim缺失或不正确
	 */
	INVALID_CLAIM

}
