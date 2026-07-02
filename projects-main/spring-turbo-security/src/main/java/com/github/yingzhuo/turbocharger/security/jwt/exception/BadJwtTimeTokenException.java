package com.github.yingzhuo.turbocharger.security.jwt.exception;

import com.github.yingzhuo.turbocharger.security.exception.BadTokenException;

/**
 * 令牌在错误的时间被使用
 *
 * @author 应卓
 * @since 2.2.4
 */
public class BadJwtTimeTokenException extends BadTokenException {

	public BadJwtTimeTokenException(String msg) {
		super(msg);
	}

	public BadJwtTimeTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
