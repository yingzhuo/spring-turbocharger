package com.github.yingzhuo.turbocharger.util;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.util.*;

public final class EnumUtils {

	private EnumUtils() {
		super();
	}

	@Nullable
	public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String enumName) {
		return getEnum(enumClass, enumName, null);
	}

	@Nullable
	public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String enumName,
												@Nullable final E defaultEnum) {

		Assert.hasText(enumName, "enumName is required");
		try {
			return Enum.valueOf(enumClass, enumName);
		} catch (final IllegalArgumentException ex) {
			return defaultEnum;
		}
	}

	@Nullable
	public static <E extends Enum<E>> E getEnumIgnoreCase(final Class<E> enumClass, final String enumName) {
		return getEnumIgnoreCase(enumClass, enumName, null);
	}

	@Nullable
	public static <E extends Enum<E>> E getEnumIgnoreCase(final Class<E> enumClass, final String enumName,
														  @Nullable final E defaultEnum) {

		Assert.notNull(enumClass, "enumClass is required");
		Assert.hasText(enumName, "enumName is required");

		for (final E each : enumClass.getEnumConstants()) {
			if (each.name().equalsIgnoreCase(enumName)) {
				return each;
			}
		}
		return defaultEnum;
	}

	public static <E extends Enum<E>> List<E> getEnumList(final Class<E> enumClass) {
		return new ArrayList<>(Arrays.asList(enumClass.getEnumConstants()));
	}

	public static <E extends Enum<E>> Map<String, E> getEnumMap(final Class<E> enumClass) {
		final Map<String, E> map = new LinkedHashMap<>();
		for (final E e : enumClass.getEnumConstants()) {
			map.put(e.name(), e);
		}
		return map;
	}

	public static <E extends Enum<E>> boolean isValidEnum(final Class<E> enumClass, final String enumName) {
		return getEnum(enumClass, enumName) != null;
	}

	public static <E extends Enum<E>> boolean isValidEnumIgnoreCase(final Class<E> enumClass, final String enumName) {
		return getEnumIgnoreCase(enumClass, enumName) != null;
	}

}
