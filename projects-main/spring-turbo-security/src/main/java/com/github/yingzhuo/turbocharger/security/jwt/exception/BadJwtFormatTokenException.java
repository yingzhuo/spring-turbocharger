package com.github.yingzhuo.turbocharger.security.jwt.exception;

import com.github.yingzhuo.turbocharger.security.exception.BadTokenException;

/**
 * 错误的JWT令牌格式
 *
 * @author 应卓
 * @since 2.2.4
 */
public class BadJwtFormatTokenException extends BadTokenException {

	public BadJwtFormatTokenException(String msg) {
		super(msg);
	}

	public BadJwtFormatTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
