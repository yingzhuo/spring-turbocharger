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
package com.github.yingzhuo.turbocharger.util.reflection;

import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.springframework.util.Assert;

import java.util.function.Supplier;

/**
 * @author 应卓
 * @see InstanceUtils
 * @see InstantiationException
 * @since 1.0.0
 */
public final class InstantiationExceptionSupplier implements Supplier<InstantiationException> {

	private final Class<?> type;

	public InstantiationExceptionSupplier(Class<?> type) {
		Assert.notNull(type, "type is required");
		this.type = type;
	}

	@Override
	public InstantiationException get() {
		return new InstantiationException(
			StringFormatter.format("not able to create instance. type: '{}'", type.getName()));
	}

}
