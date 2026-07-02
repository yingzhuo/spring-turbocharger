package com.github.yingzhuo.turbocharger.util;

import com.github.yingzhuo.turbocharger.util.reflection.InstantiationException;

/**
 * 类型加载异常
 *
 * @author 应卓
 * @see InstantiationException
 * @since 1.0.2
 */
public class ClassLoadingException extends IllegalStateException {

	/**
	 * 构造方法
	 */
	public ClassLoadingException() {
		super();
	}

	/**
	 * 构造方法
	 *
	 * @param message 消息
	 */
	public ClassLoadingException(String message) {
		super(message);
	}

}
