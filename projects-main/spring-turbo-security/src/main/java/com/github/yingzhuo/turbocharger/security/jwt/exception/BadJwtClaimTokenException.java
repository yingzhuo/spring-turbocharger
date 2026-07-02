package com.github.yingzhuo.turbocharger.security.jwt.exception;

import com.github.yingzhuo.turbocharger.security.exception.BadTokenException;

public class BadJwtClaimTokenException extends BadTokenException {

	public BadJwtClaimTokenException(String msg) {
		super(msg);
	}

	public BadJwtClaimTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
