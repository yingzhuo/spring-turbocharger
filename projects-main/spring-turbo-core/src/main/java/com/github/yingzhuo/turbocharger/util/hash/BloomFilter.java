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
package com.github.yingzhuo.turbocharger.util.hash;

import org.springframework.lang.Nullable;

/**
 * 布隆过滤器
 *
 * @author 应卓
 * @since 3.4.0
 */
public interface BloomFilter {

	/**
	 * 添加一个元素
	 *
	 * @param element 要添加的元素
	 */
	public void add(String element);

	/**
	 * 判断是否有可能存在某元素
	 *
	 * @param element 待检测的元素
	 * @return 结果
	 */
	public boolean mightContain(@Nullable String element);

	/**
	 * 判断是否不存在某元素
	 *
	 * @param element 待检测的元素
	 * @return 结果
	 */
	public default boolean notContain(@Nullable String element) {
		return !mightContain(element);
	}

}
