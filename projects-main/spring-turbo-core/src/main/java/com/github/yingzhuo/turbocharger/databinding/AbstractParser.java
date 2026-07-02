package com.github.yingzhuo.turbocharger.databinding;

import com.github.yingzhuo.turbocharger.exception.DataBindingException;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;

import java.util.Locale;

public abstract class AbstractParser<T> implements Parser<T> {

	@Override
	public final T parse(String text, Locale locale) {
		try {
			return doParse(text, locale);
		} catch (RuntimeException e) {
			throw InternalConverterUtils.transform(e);
		}
	}

	protected abstract T doParse(String text, Locale locale) throws DataBindingException;

}
