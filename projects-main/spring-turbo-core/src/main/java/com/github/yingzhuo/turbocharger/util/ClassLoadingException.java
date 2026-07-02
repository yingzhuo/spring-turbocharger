package com.github.yingzhuo.turbocharger.util;

import com.github.yingzhuo.turbocharger.util.reflection.InstantiationException;

public class ClassLoadingException extends IllegalStateException {

	public ClassLoadingException() {
		super();
	}

	public ClassLoadingException(String message) {
		super(message);
	}

}
