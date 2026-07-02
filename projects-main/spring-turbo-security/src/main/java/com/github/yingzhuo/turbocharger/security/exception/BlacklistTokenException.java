package com.github.yingzhuo.turbocharger.security.exception;

import com.github.yingzhuo.turbocharger.security.token.blacklist.TokenBlacklistManager;

/**
 * 令牌已被加入黑名单 不可以再使用
 *
 * @author 应卓
 * @see TokenBlacklistManager
 * @since 2.0.5
 */
public class BlacklistTokenException extends BadTokenException {

	public BlacklistTokenException(String msg) {
		super(msg);
	}

	public BlacklistTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
