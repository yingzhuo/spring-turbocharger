package com.github.yingzhuo.turbocharger.databinding;

import com.github.yingzhuo.turbocharger.exception.DataBindingException;
import com.github.yingzhuo.turbocharger.util.collection.ArrayUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractGenericConverter implements GenericConverter {

	private final Set<ConvertiblePair> convertibleTypes;

	public AbstractGenericConverter(Class<?> sourceType, Class<?> targetType, Class<?>... moreTargetTypes) {
		Set<ConvertiblePair> set = new HashSet<>();
		set.add(new ConvertiblePair(sourceType, targetType));

		if (!ArrayUtils.isNullOrEmpty(moreTargetTypes)) {
			for (var it : moreTargetTypes) {
				set.add(new ConvertiblePair(sourceType, it));
			}
		}

		this.convertibleTypes = Collections.unmodifiableSet(set);
	}

	public AbstractGenericConverter(MultiValueMap<Class<?>, Class<?>> supported) {
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

	@Override
	public final Set<ConvertiblePair> getConvertibleTypes() {
		return this.convertibleTypes;
	}

	@Nullable
	@Override
	public final Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		try {
			return doConvert(source, sourceType, targetType);
		} catch (RuntimeException e) {
			throw InternalConverterUtils.transform(e);
		}
	}

	@Nullable
	protected abstract Object doConvert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) throws DataBindingException;

}
