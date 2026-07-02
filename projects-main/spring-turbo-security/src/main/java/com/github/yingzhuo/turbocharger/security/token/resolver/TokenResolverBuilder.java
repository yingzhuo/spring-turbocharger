package com.github.yingzhuo.turbocharger.security.token.resolver;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.github.yingzhuo.turbocharger.util.collection.CollectionUtils.nullSafeAddAll;

public final class TokenResolverBuilder {

	private final List<TokenResolver> list = new ArrayList<>();

	TokenResolverBuilder() {
		super();
	}

	public TokenResolverBuilder add(TokenResolver... resolvers) {
		nullSafeAddAll(list, resolvers);
		return this;
	}

	public TokenResolverBuilder add(Collection<TokenResolver> resolvers) {
		nullSafeAddAll(list, resolvers);
		return this;
	}

	public TokenResolverBuilder fromHttpHeader(String headName) {
		list.add(new HeaderTokenResolver(headName));
		return this;
	}

	public TokenResolverBuilder fromHttpHeader(String headName, @Nullable String prefix) {
		list.add(new HeaderTokenResolver(headName, prefix));
		return this;
	}

	public TokenResolverBuilder fromHttpQuery(String paramName) {
		list.add(new QueryTokenResolver(paramName));
		return this;
	}

	public TokenResolverBuilder fromHttpQuery(String paramName, @Nullable String prefix) {
		list.add(new QueryTokenResolver(paramName, prefix));
		return this;
	}

	public TokenResolverBuilder bearerToken() {
		list.add(new BearerTokenResolver());
		return this;
	}

	public TokenResolver build() {
		if (list.isEmpty()) {
			return request -> Optional.empty();
		}
		if (list.size() == 1) {
			return list.get(0);
		}
		return new CompositeTokenResolver(list);
	}

}
