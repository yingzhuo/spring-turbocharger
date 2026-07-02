package com.github.yingzhuo.turbocharger.util.time;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public final class LocalDateTimeUtils {

	private LocalDateTimeUtils() {
		super();
	}

	public static LocalDateTime toLocalDateTime(Date date, @Nullable ZoneId zoneId) {
		Assert.notNull(date, "date is required");
		return Instant.ofEpochMilli(date.getTime())
			.atZone(Objects.requireNonNullElse(zoneId, ZoneId.systemDefault()))
			.toLocalDateTime();
	}

	public static LocalDateTime toLocalDateTime(long timestamp, @Nullable ZoneId zoneId) {
		Assert.isTrue(timestamp >= 0, "timestamp should >= 0");

		return Instant.ofEpochMilli(timestamp)
			.atZone(Objects.requireNonNullElse(zoneId, ZoneId.systemDefault()))
			.toLocalDateTime();
	}

}
