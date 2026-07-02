package com.github.yingzhuo.turbocharger.util;

import org.springframework.util.Assert;

import java.util.function.Supplier;

/**
 * 类型加载异常提供器
 *
 * @author 应卓
 * @see ClassUtils
 * @see ClassLoadingException
 * @since 1.0.2
 */
public class ClassLoadingExceptionSupplier implements Supplier<ClassLoadingException> {

	private final String className;

	/**
	 * 构造方法
	 *
	 * @param className 加载类型名称
	 */
	public ClassLoadingExceptionSupplier(String className) {
		Assert.hasText(className, "className is required");
		this.className = className;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClassLoadingException get() {
		var msg = StringFormatter.format("not able to load class. class name: '{}'", className);
		return new ClassLoadingException(msg);
	}

}
