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
package com.github.yingzhuo.turbocharger.databinding;

import org.springframework.beans.PropertyAccessException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.ObjectError;

/**
 * {@link DefaultBindingErrorProcessor} 扩展
 *
 * @author 应卓
 * @see #getInstance()
 * @see org.springframework.validation.DataBinder
 * @see org.springframework.web.bind.WebDataBinder
 * @since 3.3.1
 */
public final class SmartBindingErrorProcessor extends DefaultBindingErrorProcessor {

	/**
	 * 私有构造方法
	 */
	private SmartBindingErrorProcessor() {
	}

	/**
	 * 获取本类型实例
	 *
	 * @return 单例实例
	 */
	public static SmartBindingErrorProcessor getInstance() {
		return SyncAvoid.INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processPropertyAccessException(PropertyAccessException ex, BindingResult bindingResult) {

		if (ex.getRootCause() instanceof MessageSourceResolvable rootResolvable) {
			bindingResult.addError(
				new ObjectError(
					bindingResult.getObjectName(),
					rootResolvable.getCodes(),
					rootResolvable.getArguments(),
					rootResolvable.getDefaultMessage()
				)
			);
			return;
		}

		if (ex.getRootCause() instanceof MultiMessageSourceResolvable multiMessageSourceResolvable) {
			for (var resolvable : multiMessageSourceResolvable) {
				if (resolvable != null) {
					bindingResult.addError(
						new ObjectError(
							bindingResult.getObjectName(),
							resolvable.getCodes(),
							resolvable.getArguments(),
							resolvable.getDefaultMessage()
						)
					);
				}
			}
			return;
		}

		super.processPropertyAccessException(ex, bindingResult);
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final SmartBindingErrorProcessor INSTANCE = new SmartBindingErrorProcessor();
	}

}
