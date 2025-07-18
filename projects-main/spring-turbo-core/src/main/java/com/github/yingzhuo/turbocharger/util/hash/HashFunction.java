/*
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
 */
package com.github.yingzhuo.turbocharger.util.hash;

import java.util.function.Function;

/**
 * 哈希函数器
 *
 * @author 应卓
 * @see ConsistentHashing
 * @since 3.4.0
 */
@FunctionalInterface
public interface HashFunction extends Function<String, Integer> {

	/**
	 * 计算哈希值
	 *
	 * @param key 键
	 * @return 哈希值
	 */
	@Override
	public Integer apply(String key);

}
