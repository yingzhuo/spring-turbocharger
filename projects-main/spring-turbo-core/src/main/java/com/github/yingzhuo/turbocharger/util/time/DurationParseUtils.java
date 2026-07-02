package com.github.yingzhuo.turbocharger.util.time;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public final class DurationParseUtils {

	private DurationParseUtils() {
		super();
	}

	public static Duration parse(String string) {
		return parse(string, null);
	}

	public static Duration parse(String string, @Nullable ChronoUnit unit) {
		Assert.hasText(string, "string is required");

		Duration duration = parseFromSimpleStyle(string, unit);
		if (duration == null) {
			duration = parseFromISO8601Style(string, unit);
		}

		if (duration == null) {
			throw new IllegalArgumentException("'" + string + "' is not a valid duration");
		}

		return duration;
	}

	@Nullable
	private static Duration parseFromSimpleStyle(String string, @Nullable ChronoUnit unit) {
		try {
			return DurationStyle.SIMPLE.parse(string, unit);
		} catch (Exception e) {
			return null;
		}
	}

	@Nullable
	private static Duration parseFromISO8601Style(String string, @Nullable ChronoUnit unit) {
		try {
			return DurationStyle.ISO8601.parse(string, unit);
		} catch (Exception e) {
			return null;
		}
	}

}
