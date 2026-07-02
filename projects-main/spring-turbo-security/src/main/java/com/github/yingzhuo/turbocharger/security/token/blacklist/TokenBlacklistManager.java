package com.github.yingzhuo.turbocharger.security.token.blacklist;

import com.github.yingzhuo.turbocharger.security.exception.BlacklistTokenException;
import com.github.yingzhuo.turbocharger.security.token.Token;

public interface TokenBlacklistManager {

	public void save(Token token);

	public void verify(Token token) throws BlacklistTokenException;

}
