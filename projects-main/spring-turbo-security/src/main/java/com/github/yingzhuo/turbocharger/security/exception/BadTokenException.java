package com.github.yingzhuo.turbocharger.security.exception;

import org.springframework.security.authentication.AccountStatusException;

public class BadTokenException extends AccountStatusException {

	public BadTokenException(String msg) {
		super(msg);
	}

	public BadTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
