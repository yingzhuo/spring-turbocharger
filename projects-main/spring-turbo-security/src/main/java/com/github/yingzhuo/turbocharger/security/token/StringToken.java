package com.github.yingzhuo.turbocharger.security.token;

import org.springframework.util.Assert;

import java.util.Objects;

/**
 * String令牌
 *
 * @author 应卓
 * @since 1.0.0
 */
public class StringToken implements Token {

	private final String string;

	/**
	 * 构造方法
	 *
	 * @param tokenValue 令牌内容
	 */
	public StringToken(CharSequence tokenValue) {
		Assert.hasText("token", "token is null or blank");
		this.string = tokenValue.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asString() {
		return string;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return string;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StringToken that = (StringToken) o;
		return string.equals(that.string);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(string);
	}

}
