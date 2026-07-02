package com.github.yingzhuo.turbocharger.security.token.resolver;

import com.github.yingzhuo.turbocharger.security.token.StringToken;
import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.util.StringUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;
import java.util.Optional;

public class HeaderTokenResolver implements TokenResolver {

	protected final String headerName;
	protected final String prefix;
	protected final int prefixLen;

	public HeaderTokenResolver(String headerName) {
		this(headerName, null);
	}

	public HeaderTokenResolver(String headerName, @Nullable String prefix) {
		Assert.hasText(headerName, "headerName is required");

		prefix = Objects.requireNonNullElse(prefix, "");

		this.headerName = headerName;
		this.prefix = prefix;
		this.prefixLen = prefix.length();
	}

	@Override
	public Optional<Token> resolve(WebRequest request) {
		var headerValue = request.getHeader(headerName);

		if (headerValue == null || !headerValue.startsWith(prefix)) {
			return Optional.empty();
		}

		headerValue = headerValue.substring(prefixLen);

		if (StringUtils.isBlank(headerValue)) {
			return Optional.empty();
		}

		return Optional.of(new StringToken(headerValue));
	}

}
