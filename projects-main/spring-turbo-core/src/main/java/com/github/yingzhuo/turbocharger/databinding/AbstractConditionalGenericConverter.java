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
package com.github.yingzhuo.turbocharger.databinding;

import com.github.yingzhuo.turbocharger.exception.DataBindingException;
import com.github.yingzhuo.turbocharger.util.collection.ArrayUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link ConditionalGenericConverter} 辅助工具 <br>
 * 本类型将尝试转换{@link RuntimeException} 转换成 {@link org.springframework.context.MessageSourceResolvable}。
 *
 * @author 应卓
 * @since 3.3.1
 */
public abstract class AbstractConditionalGenericConverter implements ConditionalGenericConverter {

	private final Set<ConvertiblePair> convertibleTypes;

	public AbstractConditionalGenericConverter(Class<?> sourceType, Class<?> targetType, Class<?>... moreTargetTypes) {
		Set<ConvertiblePair> set = new HashSet<>();
		set.add(new ConvertiblePair(sourceType, targetType));

		if (!ArrayUtils.isNullOrEmpty(moreTargetTypes)) {
			for (var it : moreTargetTypes) {
				set.add(new ConvertiblePair(sourceType, it));
			}
		}

		this.convertibleTypes = Collections.unmodifiableSet(set);
	}

	public AbstractConditionalGenericConverter(MultiValueMap<Class<?>, Class<?>> supported) {
		Set<ConvertiblePair> set = new HashSet<>();
		if (!CollectionUtils.isEmpty(supported)) {
			for (var sourceType : supported.keySet()) {
				for (var targetType : supported.get(sourceType)) {
					set.add(new ConvertiblePair(sourceType, targetType));
				}
			}
		}
		this.convertibleTypes = Collections.unmodifiableSet(set);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Set<ConvertiblePair> getConvertibleTypes() {
		return this.convertibleTypes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public final Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		try {
			return doConvert(source, sourceType, targetType);
		} catch (RuntimeException e) {
			throw InternalConverterUtils.transform(e);
		}
	}

	/**
	 * 转换数据
	 *
	 * @param source     源数据
	 * @param sourceType 源数据类型
	 * @param targetType 目标类型
	 * @return 转换结果
	 * @throws DataBindingException 数据转换失败或数据非法
	 */
	@Nullable
	protected abstract Object doConvert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) throws DataBindingException;

}
