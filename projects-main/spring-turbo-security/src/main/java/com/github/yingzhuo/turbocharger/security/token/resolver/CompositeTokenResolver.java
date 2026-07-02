package com.github.yingzhuo.turbocharger.security.token.resolver;

import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.util.collection.CollectionUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.core.OrderComparator;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

public final class CompositeTokenResolver implements TokenResolver, Iterable<TokenResolver> {

	private final List<TokenResolver> resolvers = new ArrayList<>();

	public CompositeTokenResolver(TokenResolver... resolvers) {
		CollectionUtils.nullSafeAddAll(this.resolvers, resolvers);
		OrderComparator.sort(this.resolvers);
	}

	public CompositeTokenResolver(Collection<TokenResolver> resolvers) {
		CollectionUtils.nullSafeAddAll(this.resolvers, resolvers);
		OrderComparator.sort(this.resolvers);
	}

	public static CompositeTokenResolver of(TokenResolver... resolvers) {
		return new CompositeTokenResolver(resolvers);
	}

	@Override
	public Optional<Token> resolve(WebRequest request) {
		for (TokenResolver it : this) {
			Optional<Token> op = doResolve(it, request);
			if (op.isPresent()) {
				return op;
			}
		}
		return Optional.empty();
	}

	@Override
	public Iterator<TokenResolver> iterator() {
		return this.resolvers.iterator();
	}

	private Optional<Token> doResolve(@Nullable TokenResolver resolver, WebRequest request) {
		try {
			if (resolver != null) {
				return resolver.resolve(request);
			} else {
				return Optional.empty();
			}
		} catch (Throwable ignored) {
			return Optional.empty();
		}
	}
}
