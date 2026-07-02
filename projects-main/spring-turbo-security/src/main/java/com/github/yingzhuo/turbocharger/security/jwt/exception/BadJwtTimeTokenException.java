package com.github.yingzhuo.turbocharger.security.jwt.exception;

import com.github.yingzhuo.turbocharger.security.exception.BadTokenException;

public class BadJwtTimeTokenException extends BadTokenException {

	public BadJwtTimeTokenException(String msg) {
		super(msg);
	}

	public BadJwtTimeTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
