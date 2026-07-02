package com.github.yingzhuo.turbocharger.security.token;

import org.springframework.util.Assert;

import java.util.Objects;

public class StringToken implements Token {

	private final String string;

	public StringToken(CharSequence tokenValue) {
		Assert.hasText("token", "token is null or blank");
		this.string = tokenValue.toString();
	}

	@Override
	public String asString() {
		return string;
	}

	@Override
	public String toString() {
		return string;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(string);
	}

}
