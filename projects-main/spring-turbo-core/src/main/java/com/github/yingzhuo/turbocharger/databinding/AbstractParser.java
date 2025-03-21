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
import com.github.yingzhuo.turbocharger.util.concurrent.ThreadSharedObjects;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;

import java.util.Locale;

/**
 * {@link Formatter} 辅助工具 <br>
 * 本类型将尝试转换{@link RuntimeException} 转换成 {@link org.springframework.context.MessageSourceResolvable}。
 *
 * @param <T> 目标类型泛型
 * @author 应卓
 * @since 3.3.1
 */
public abstract class AbstractParser<T> implements Parser<T> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final T parse(String text, Locale locale) {
		try {
			return doParse(text, locale);
		} catch (RuntimeException e) {
			throw InternalConverterUtils.transform(e);
		}
	}

	/**
	 * 转换数据
	 *
	 * @param text   源数据
	 * @param locale locale
	 * @return 转换结果
	 * @throws DataBindingException 数据转换失败或数据非法
	 */
	protected abstract T doParse(String text, Locale locale) throws DataBindingException;

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
