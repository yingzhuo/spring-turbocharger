package com.github.yingzhuo.turbocharger.security.jwt.exception;

import com.github.yingzhuo.turbocharger.security.exception.BadTokenException;

public class BadJwtFormatTokenException extends BadTokenException {

	public BadJwtFormatTokenException(String msg) {
		super(msg);
	}

	public BadJwtFormatTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
