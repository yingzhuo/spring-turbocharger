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
package com.github.yingzhuo.turbocharger.security.token.blacklist;

import com.github.yingzhuo.turbocharger.security.exception.BlacklistTokenException;
import com.github.yingzhuo.turbocharger.security.token.Token;

/**
 * @author 应卓
 * @since 2.0.6
 */
public final class AlwaysBlacklistedTokenBlacklistedManager implements TokenBlacklistManager {

	/**
	 * 私有构造方法
	 */
	private AlwaysBlacklistedTokenBlacklistedManager() {
	}

	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static AlwaysBlacklistedTokenBlacklistedManager getInstance() {
		return SyncAvoid.INSTANCE;
	}

	@Override
	public void save(Token token) {
		// nop
	}

	@Override
	public void verify(Token token) throws BlacklistTokenException {
		throw new BlacklistTokenException("");
	}

	// 延迟加载
	private static final class SyncAvoid {
		private static final AlwaysBlacklistedTokenBlacklistedManager INSTANCE = new AlwaysBlacklistedTokenBlacklistedManager();
	}

}
