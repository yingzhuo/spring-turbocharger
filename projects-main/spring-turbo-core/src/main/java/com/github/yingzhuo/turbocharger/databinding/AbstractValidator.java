package com.github.yingzhuo.turbocharger.databinding;

import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractValidator<T> implements Validator {

	private final Class<T> supportType;

	public AbstractValidator(Class<T> supportType) {
		this.supportType = supportType;
	}

	@Override
	public final boolean supports(Class<?> clazz) {
		return ClassUtils.isAssignable(supportType, clazz);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final void validate(Object target, Errors errors) {
		doValidate((T) target, errors);
	}

	protected abstract void doValidate(T target, Errors errors);

}
