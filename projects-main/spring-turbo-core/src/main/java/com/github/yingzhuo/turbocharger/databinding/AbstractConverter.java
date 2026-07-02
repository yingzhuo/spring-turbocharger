package com.github.yingzhuo.turbocharger.databinding;

import com.github.yingzhuo.turbocharger.exception.DataBindingException;
import org.springframework.core.convert.converter.Converter;

public abstract class AbstractConverter<S, T> implements Converter<S, T> {

	@Override
	public final T convert(S source) {
		try {
			return doConvert(source);
		} catch (RuntimeException e) {
			throw InternalConverterUtils.transform(e);
		}
	}

	protected abstract T doConvert(S source) throws DataBindingException;

}
