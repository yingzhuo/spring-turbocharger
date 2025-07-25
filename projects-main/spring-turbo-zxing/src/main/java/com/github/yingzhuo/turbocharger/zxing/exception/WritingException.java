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
package com.github.yingzhuo.turbocharger.zxing.exception;

/**
 * 生成二维码或条形码错误
 *
 * @author 应卓
 * @since 3.4.3
 */
public class WritingException extends RuntimeException {

	/**
	 * 构造方法
	 *
	 * @param exception 其他要检查异常
	 */
	public WritingException(Exception exception) {
		super(exception);
	}

}
