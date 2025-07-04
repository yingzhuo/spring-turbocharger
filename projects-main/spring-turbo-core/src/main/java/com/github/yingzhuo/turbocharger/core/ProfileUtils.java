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
package com.github.yingzhuo.turbocharger.core;

import org.springframework.core.env.Profiles;

/**
 * Profiles相关工具
 *
 * @author 应卓
 * @see SpringUtils
 * @see org.springframework.core.env.Environment
 * @see Profiles
 * @see Profiles#of(String...)
 * @since 1.0.13
 */
@Deprecated(forRemoval = true, since = "3.5.3")
public final class ProfileUtils {

	/**
	 * 私有构造方法
	 */
	private ProfileUtils() {
		super();
	}

	/**
	 * 判断profiles是否被激活
	 *
	 * @param profileExpressions profile表达式
	 * @return 结果
	 */
	public static boolean matchesProfiles(String... profileExpressions) {
		return SpringUtils.getEnvironment()
			.matchesProfiles(profileExpressions);
	}

}
