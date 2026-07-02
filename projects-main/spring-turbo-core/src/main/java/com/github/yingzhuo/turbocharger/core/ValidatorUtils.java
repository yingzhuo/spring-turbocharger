package com.github.yingzhuo.turbocharger.core;

import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

/**
 * @author 应卓
 * @see SpringUtils
 * @see Validator
 * @since 1.0.0
 */
public final class ValidatorUtils {

	/**
	 * 私有构造方法
	 */
	private ValidatorUtils() {
		super();
	}

	/**
	 * 查询是否支持验证
	 *
	 * @param targetType 待验证的类型
	 * @return 结果
	 */
	public static boolean support(Class<?> targetType) {
		Assert.notNull(targetType, "targetType is required");
		var validator = SpringUtils.getValidator();
		return validator.supports(targetType);
	}

	/**
	 * 验证
	 *
	 * @param obj 待验证的对象
	 * @return 验证结果
	 */
	public static BindingResult validate(Object obj) {
		Assert.notNull(obj, "obj is required");
		var objectName = StringFormatter.format("bean[{}]", System.identityHashCode(obj));
		var errors = new BeanPropertyBindingResult(obj, objectName);
		var validator = SpringUtils.getValidator();
		validator.validate(obj, errors);
		return errors;
	}

}
