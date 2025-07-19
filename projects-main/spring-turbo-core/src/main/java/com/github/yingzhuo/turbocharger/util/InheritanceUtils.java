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

import org.springframework.util.Assert;

/**
 * @author 应卓
 * @since 1.0.8
 */
public final class InheritanceUtils {

	/**
	 * 私有构造方法
	 */
	private InheritanceUtils() {
	}

	public static int distance(Class<?> child, Class<?> parent) {
		Assert.notNull(child, "child is required");
		Assert.notNull(parent, "parent is required");

		if (child.equals(parent)) {
			return 0;
		}

		final Class<?> cParent = child.getSuperclass();
		int d = parent.equals(cParent) ? 1 : 0;

		if (d == 1) {
			return d;
		}
		d += distance(cParent, parent);
		return d > 0 ? d + 1 : -1;
	}

}
