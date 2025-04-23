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

import java.util.List;
import java.util.Set;

import static com.github.yingzhuo.turbocharger.core.SpringUtils.getApplicationArguments;

/**
 * {@link org.springframework.boot.ApplicationArguments} 相关工具
 *
 * @author 应卓
 * @see SpringUtils
 * @see org.springframework.boot.ApplicationArguments
 * @since 3.0.7
 */
public final class ApplicationArgumentsUtils {

	/**
	 * 私有构造方法
	 */
	private ApplicationArgumentsUtils() {
		super();
	}

	public static Set<String> getOptionNames() {
		return getApplicationArguments().getOptionNames();
	}

	public static boolean containsOption(String name) {
		return getApplicationArguments().containsOption(name);
	}

	public static List<String> getOptionValues(String name) {
		return getApplicationArguments().getOptionValues(name);
	}

	public static List<String> getNonOptionArgs() {
		return getApplicationArguments().getNonOptionArgs();
	}

}
