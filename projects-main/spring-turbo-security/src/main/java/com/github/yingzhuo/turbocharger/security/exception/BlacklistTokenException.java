package com.github.yingzhuo.turbocharger.security.exception;

import com.github.yingzhuo.turbocharger.security.token.blacklist.TokenBlacklistManager;

public class BlacklistTokenException extends BadTokenException {

	public BlacklistTokenException(String msg) {
		super(msg);
	}

	public BlacklistTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
