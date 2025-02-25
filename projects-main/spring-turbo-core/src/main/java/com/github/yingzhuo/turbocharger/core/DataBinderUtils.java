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

import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.validation.DataBinder;

/**
 * {@link DataBinder} 相关工具
 *
 * @author 应卓
 * @see org.springframework.validation.DataBinder
 * @since 3.3.5
 */
public final class DataBinderUtils {

	/**
	 * 私有构造方法
	 */
	private DataBinderUtils() {
		super();
	}

	/**
	 * 新建一个DataBinder
	 *
	 * @param target binding对象
	 * @return {@link DataBinder} 实例
	 */
	public static DataBinder createDataBinder(Object target) {
		return createDataBinder(target, null);
	}

	/**
	 * 新建一个DataBinder
	 *
	 * @param target     binding对象
	 * @param objectName 名称
	 * @return {@link DataBinder} 实例
	 */
	public static DataBinder createDataBinder(Object target, @Nullable String objectName) {
		Assert.notNull(target, "target is required");

		var conversionService =
			SpringUtils.getBean(ConversionService.class)
				.orElseGet(DefaultFormattingConversionService::new);

		var binder = new DataBinder(target, objectName);
		binder.setConversionService(conversionService);
		return binder;
	}

}
