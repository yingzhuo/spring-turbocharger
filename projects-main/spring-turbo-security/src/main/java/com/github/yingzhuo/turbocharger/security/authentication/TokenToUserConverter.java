package com.github.yingzhuo.turbocharger.security.authentication;

import com.github.yingzhuo.turbocharger.security.token.StringToken;
import com.github.yingzhuo.turbocharger.security.token.Token;
import org.jspecify.annotations.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@FunctionalInterface
public interface TokenToUserConverter extends Converter<Token, UserDetails> {

	@Nullable
	public UserDetails convert(@Nullable Token token) throws AuthenticationException;

}
