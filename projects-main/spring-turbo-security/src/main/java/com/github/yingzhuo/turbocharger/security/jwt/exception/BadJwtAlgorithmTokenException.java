package com.github.yingzhuo.turbocharger.security.jwt.exception;

import com.github.yingzhuo.turbocharger.security.exception.BadTokenException;

public class BadJwtAlgorithmTokenException extends BadTokenException {

	public BadJwtAlgorithmTokenException(String msg) {
		super(msg);
	}

	public BadJwtAlgorithmTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
