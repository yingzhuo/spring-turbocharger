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

import com.github.yingzhuo.turbocharger.util.concurrent.ThreadSharedObjects;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * {@link Validator} 辅助工具
 *
 * @param <T> 可以校检的类型的泛型
 * @author 应卓
 * @since 3.3.1
 */
public abstract class AbstractValidator<T> implements Validator {

	private final Class<T> supportType;

	public AbstractValidator(Class<T> supportType) {
		this.supportType = supportType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supports(Class<?> clazz) {
		return ClassUtils.isAssignable(supportType, clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final void validate(Object target, Errors errors) {
		doValidate((T) target, errors);
	}

	/**
	 * 验证数据
	 *
	 * @param target 待验证的对象
	 * @param errors 错误
	 */
	protected abstract void doValidate(T target, Errors errors);

	/**
	 * 加入一个线程安全的共享对象。在线程的其他地方可以取出这个共享对象。
	 *
	 * @param objectType 共享对象类型
	 * @param object     共享对象
	 * @param <O>        共享对象类型实例
	 * @see ThreadSharedObjects#put(Class, Object)
	 */
	protected final <O> void setSharedObject(Class<O> objectType, O object) {
		ThreadSharedObjects.put(objectType, object);
	}

	/**
	 * 加入一个线程安全的共享对象。在线程的其他地方可以取出这个共享对象。
	 *
	 * @param objectName 共享对象名称
	 * @param object     共享对象
	 * @see ThreadSharedObjects#put(String, Object)
	 */
	protected final void setSharedObject(String objectName, Object object) {
		ThreadSharedObjects.put(objectName, object);
	}

}
