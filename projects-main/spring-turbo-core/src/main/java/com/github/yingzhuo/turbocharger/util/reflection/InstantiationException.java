package com.github.yingzhuo.turbocharger.util.reflection;

import com.github.yingzhuo.turbocharger.util.ClassLoadingException;

/**
 * 实例化失败异常
 *
 * @author 应卓
 * @see ClassLoadingException
 * @see InstanceUtils
 * @since 1.0.0
 */
public class InstantiationException extends IllegalStateException {

	public InstantiationException() {
		super();
	}

	public InstantiationException(String message) {
		super(message);
	}

}
