package com.github.yingzhuo.turbocharger.util.time;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public final class DurationFormatUtils {

	private DurationFormatUtils() {
		super();
	}

	public static String format(Duration duration) {
		return format(duration, null);
	}

	public static String format(Duration duration, @Nullable ChronoUnit unit) {
		Assert.notNull(duration, "duration is required");
		return DurationStyle.ISO8601.print(duration, unit);
	}

}
