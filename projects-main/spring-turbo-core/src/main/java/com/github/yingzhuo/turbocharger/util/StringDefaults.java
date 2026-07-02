package com.github.yingzhuo.turbocharger.util;

import org.jspecify.annotations.Nullable;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

public final class StringDefaults {

	private StringDefaults() {
		super();
	}

	public static String nullToDefault(@Nullable String string, String defaultString) {
		return string == null ? defaultString : string;
	}

	public static String emptyToDefault(@Nullable String string, String defaultString) {
		return string == null || string.isEmpty() ? defaultString : string;
	}

	public static String blankToDefault(@Nullable String string, String defaultString) {
		return string == null || string.isBlank() ? defaultString : string;
	}

	public static String nullToEmpty(@Nullable String string) {
		return nullToDefault(string, EMPTY);
	}

	public static String blankToEmpty(@Nullable String string) {
		return blankToDefault(string, EMPTY);
	}

}
