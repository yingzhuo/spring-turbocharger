package com.github.yingzhuo.turbocharger.util;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

public final class CharSequenceUtils {

	private CharSequenceUtils() {
		super();
	}

	// ------------------------------------------------------------------------------------------------------------------

	public static int length(@Nullable CharSequence cs) {
		return cs != null ? cs.length() : 0;
	}

	// ------------------------------------------------------------------------------------------------------------------

	public static CharSequence subSequence(CharSequence cs, int start) {
		Assert.notNull(cs, "cs is required");
		return cs.subSequence(start, cs.length());
	}

	// ------------------------------------------------------------------------------------------------------------------

	public static char[] toCharArray(@Nullable CharSequence source) {
		if (source == null) {
			return new char[0];
		}

		int len = StringUtils.length(source.toString());

		if (len == 0) {
			return new char[0];
		}

		if (source instanceof String) {
			return ((String) source).toCharArray();
		}

		char[] array = new char[len];

		for (int i = 0; i < len; i++) {
			array[i] = source.charAt(i);
		}
		return array;
	}

}
