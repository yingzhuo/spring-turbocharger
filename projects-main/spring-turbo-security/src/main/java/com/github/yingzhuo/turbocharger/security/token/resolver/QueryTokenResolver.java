package com.github.yingzhuo.turbocharger.security.token.resolver;

import com.github.yingzhuo.turbocharger.security.token.StringToken;
import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.util.StringUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * 从HTTP QUERY中解析令牌
 *
 * @author 应卓
 * @see HeaderTokenResolver
 * @since 1.0.0
 */
public class QueryTokenResolver implements TokenResolver {

	protected final String paramName;
	protected final String prefix;
	protected final int prefixLen;

	/**
	 * 构造方法
	 *
	 * @param paramName query name
	 */
	public QueryTokenResolver(String paramName) {
		this(paramName, null);
	}

	/**
	 * 构造方法
	 *
	 * @param paramName query name
	 * @param prefix    前缀
	 */
	public QueryTokenResolver(String paramName, @Nullable String prefix) {
		Assert.notNull(paramName, "paramName must not be null");
		prefix = Objects.requireNonNullElse(prefix, "");

		this.paramName = paramName;
		this.prefix = prefix;
		this.prefixLen = prefix.length();
	}

	/**
	 * 解析令牌
	 *
	 * @param request HTTP请求
	 * @return 令牌Optional，不能成功解析时返回empty-optional
	 */
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
