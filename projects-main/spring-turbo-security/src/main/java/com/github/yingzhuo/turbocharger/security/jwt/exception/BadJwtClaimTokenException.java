/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.security.jwt.exception;

import com.github.yingzhuo.turbocharger.security.exception.BadTokenException;

/**
 * 令牌携带的Claim错误
 *
 * @author 应卓
 * @since 3.3.2
 */
public class BadJwtClaimTokenException extends BadTokenException {

	public BadJwtClaimTokenException(String msg) {
		super(msg);
	}

	public BadJwtClaimTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
