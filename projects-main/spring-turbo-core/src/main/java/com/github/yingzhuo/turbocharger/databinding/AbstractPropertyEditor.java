package com.github.yingzhuo.turbocharger.databinding;

import com.github.yingzhuo.turbocharger.exception.DataBindingException;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

public abstract class AbstractPropertyEditor<T> extends PropertyEditorSupport implements PropertyEditor {

	@Override
	public final void setAsText(String text) throws IllegalArgumentException {
		try {
			setValue(convert(text));
		} catch (RuntimeException e) {
			throw InternalConverterUtils.transform(e);
		}
	}

	protected abstract T convert(String text) throws DataBindingException;

}
