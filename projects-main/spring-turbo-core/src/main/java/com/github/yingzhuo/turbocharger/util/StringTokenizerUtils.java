package com.github.yingzhuo.turbocharger.util;

import com.github.yingzhuo.turbocharger.util.text.StringMatcher;
import com.github.yingzhuo.turbocharger.util.text.StringTokenizer;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class StringTokenizerUtils {

	private StringTokenizerUtils() {
		super();
	}

	public static List<String> split(@Nullable String text, StringMatcher delim) {
		return split(text, delim, true, true);
	}

	public static List<String> split(@Nullable String text, StringMatcher delim, boolean trimToken, boolean ignoreBlankToken) {
		if (text == null) {
			return new ArrayList<>(0);
		}

		var list = new ArrayList<String>();
		var tokens = new StringTokenizer(text, delim);
		while (tokens.hasNext()) {
			var token = tokens.next();
			if (ignoreBlankToken) {
				if (StringUtils.isBlank(token)) {
					continue;
				}
			}

			if (trimToken) {
				token = token.trim();
			}

			list.add(token);
		}
		return list;
	}

}
