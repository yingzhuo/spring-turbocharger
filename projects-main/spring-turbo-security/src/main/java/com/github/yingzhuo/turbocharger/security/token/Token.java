package com.github.yingzhuo.turbocharger.security.token;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 令牌
 *
 * @author 应卓
 * @since 1.0.0
 */
@FunctionalInterface
public interface Token extends Comparable<Token>, Supplier<String>, Serializable {

	/**
	 * 获取令牌的值
	 *
	 * @return 令牌的值
	 */
	public String asString();

	/**
	 * 获取令牌的值
	 *
	 * @return 令牌的值
	 */
	@Override
	public default String get() {
		return this.asString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public default int compareTo(Token o) {
		return Objects.compare(this.asString(), o.asString(), Comparator.naturalOrder());
	}

}
