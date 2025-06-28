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
package com.github.yingzhuo.turbocharger.databinding;

import com.github.yingzhuo.turbocharger.exception.DataBindingException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.util.StringUtils;

/**
 * 内部工具
 *
 * @author 应卓
 * @since 3.3.1
 */
final class InternalConverterUtils {

	public static <T extends RuntimeException> RuntimeException transform(T e) {

		if ((e instanceof MessageSourceResolvable) || (e instanceof MultiMessageSourceResolvable)) {
			return e;
		}

		var msg = e.getMessage();
		if (!StringUtils.hasText(msg)) {
			var rootEx = NestedExceptionUtils.getRootCause(e);
			if (rootEx != null) {
				msg = rootEx.getMessage();
			}
		}

		if (StringUtils.hasText(msg)) {
			return DataBindingException.of(msg);
		}

		return e;
	}

}
