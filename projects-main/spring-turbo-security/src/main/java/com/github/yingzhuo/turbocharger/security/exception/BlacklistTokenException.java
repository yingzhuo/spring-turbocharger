/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.security.exception;

import com.github.yingzhuo.turbocharger.security.token.blacklist.TokenBlacklistManager;

/**
 * 令牌已被加入黑名单 不可以再使用
 *
 * @author 应卓
 * @see TokenBlacklistManager
 * @since 2.0.5
 */
public class BlacklistTokenException extends BadTokenException {

	public BlacklistTokenException(String msg) {
		super(msg);
	}

	public BlacklistTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
