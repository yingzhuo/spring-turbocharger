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
package com.github.yingzhuo.turbocharger.util.concurrent;

/**
 * 不检查线程被中断异常
 *
 * @author 应卓
 * @since 3.3.5
 */
public final class UncheckedInterruptedException extends RuntimeException {

	/**
	 * 构造方法
	 *
	 * @param cause 要检查线程被中断方法
	 */
	public UncheckedInterruptedException(Throwable cause) {
		super(cause);
	}

}
