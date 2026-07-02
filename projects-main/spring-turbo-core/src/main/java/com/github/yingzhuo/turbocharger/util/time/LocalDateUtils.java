package com.github.yingzhuo.turbocharger.util.time;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

public final class LocalDateUtils {

	private LocalDateUtils() {
		super();
	}

	public static Date toDate(LocalDate date) {
		return toDate(date, null);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static Date toDate(LocalDate date, @Nullable ZoneId zoneId) {
		zoneId = Objects.requireNonNullElse(zoneId, ZoneId.systemDefault());
		Instant instant = Instant.from(date.atStartOfDay(zoneId));
		return Date.from(instant);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static LocalDate toLocalDate(Date date) {
		Assert.notNull(date, "date is required");
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static boolean isSameDay(LocalDate date1, LocalDate date2) {
		Assert.notNull(date1, "date1 is required");
		Assert.notNull(date2, "date2 is required");
		return date1.isEqual(date2);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static int distanceDays(LocalDate date1, LocalDate date2) {
		Assert.notNull(date1, "date1 is required");
		Assert.notNull(date2, "date2 is required");
		return (int) Math.abs(ChronoUnit.DAYS.between(date1, date2));
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static Zodiac zodiac(LocalDate date) {
		Assert.notNull(date, "date is required");
		return Zodiac.getZodiac(date.getMonthValue(), date.getDayOfMonth());
	}

}
