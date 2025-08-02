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
package com.github.yingzhuo.turbocharger.util;

import com.github.yingzhuo.turbocharger.util.reflection.InstantiationException;

/**
 * 类型加载异常
 *
 * @author 应卓
 * @see InstantiationException
 * @since 1.0.2
 */
public class ClassLoadingException extends IllegalStateException {

	/**
	 * 构造方法
	 */
	public ClassLoadingException() {
		super();
	}

	/**
	 * 构造方法
	 *
	 * @param message 消息
	 */
	public ClassLoadingException(String message) {
		super(message);
	}

}
