package com.github.yingzhuo.turbocharger.security.token.blacklist;

import com.github.yingzhuo.turbocharger.security.exception.BlacklistTokenException;
import com.github.yingzhuo.turbocharger.security.token.Token;

/**
 * @author 应卓
 * @since 2.0.5
 */
public interface TokenBlacklistManager {

	public void save(Token token);

	public void verify(Token token) throws BlacklistTokenException;

}
