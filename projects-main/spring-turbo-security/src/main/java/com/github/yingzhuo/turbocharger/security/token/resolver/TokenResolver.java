package com.github.yingzhuo.turbocharger.security.token.resolver;

import com.github.yingzhuo.turbocharger.security.token.Token;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@FunctionalInterface
public interface TokenResolver extends Ordered {

	public static TokenResolverBuilder builder() {
		return new TokenResolverBuilder();
	}

	public Optional<Token> resolve(WebRequest request);

	@Override
	public default int getOrder() {
		return 0;
	}

}
