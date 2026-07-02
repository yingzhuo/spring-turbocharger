package com.github.yingzhuo.turbocharger.security.token.resolver;

import com.github.yingzhuo.turbocharger.security.token.StringToken;
import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.util.StringUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;
import java.util.Optional;

public class QueryTokenResolver implements TokenResolver {

	protected final String paramName;
	protected final String prefix;
	protected final int prefixLen;

	public QueryTokenResolver(String paramName) {
		this(paramName, null);
	}

	public QueryTokenResolver(String paramName, @Nullable String prefix) {
		Assert.notNull(paramName, "paramName must not be null");
		prefix = Objects.requireNonNullElse(prefix, "");

		this.paramName = paramName;
		this.prefix = prefix;
		this.prefixLen = prefix.length();
	}

	@Override
	public Optional<Token> resolve(WebRequest request) {
		String paramValue = request.getParameter(paramName);

		if (paramValue == null || !paramValue.startsWith(prefix)) {
			return Optional.empty();
		}

		paramValue = paramValue.substring(prefixLen);

		if (StringUtils.isBlank(paramValue)) {
			return Optional.empty();
		}

		return Optional.of(new StringToken(paramValue));
	}

}
