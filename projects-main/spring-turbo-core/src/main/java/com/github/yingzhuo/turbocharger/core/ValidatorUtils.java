package com.github.yingzhuo.turbocharger.core;

import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

public final class ValidatorUtils {

	private ValidatorUtils() {
		super();
	}

	public static boolean support(Class<?> targetType) {
		Assert.notNull(targetType, "targetType is required");
		var validator = SpringUtils.getValidator();
		return validator.supports(targetType);
	}

	public static BindingResult validate(Object obj) {
		Assert.notNull(obj, "obj is required");
		var objectName = StringFormatter.format("bean[{}]", System.identityHashCode(obj));
		var errors = new BeanPropertyBindingResult(obj, objectName);
		var validator = SpringUtils.getValidator();
		validator.validate(obj, errors);
		return errors;
	}

}
