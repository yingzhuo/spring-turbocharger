package com.github.yingzhuo.turbocharger.core;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public final class LocaleUtils {

	private LocaleUtils() {
		super();
	}

	public static Locale getLocale() {
		return getLocale(true);
	}

	public static Locale getLocale(boolean removeVariant) {
		Locale locale;
		try {
			locale = LocaleContextHolder.getLocale();
		} catch (Throwable e) {
			locale = Locale.getDefault();
		}

		if (removeVariant) {
			var lang = locale.getLanguage();
			var country = locale.getCountry();

			if (lang != null && country != null) {
				return new Locale(lang, country);
			} else if (lang != null) {
				return new Locale(lang);
			} else {
				return Locale.getDefault();
			}
		} else {
			return locale;
		}
	}

}
