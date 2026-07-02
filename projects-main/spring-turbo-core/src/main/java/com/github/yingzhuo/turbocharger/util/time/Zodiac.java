package com.github.yingzhuo.turbocharger.util.time;

public enum Zodiac {

	ARIES,

	TAURUS,

	GEMINI,

	CANCER,

	LEO,

	VIRGO,

	LIBRA,

	SCORPIO,

	SAGITTARIUS,

	CAPRICORN,

	AQUARIUS,

	PISCES;

	public static Zodiac getZodiac(int month, int day) {
		return switch (month) {
			case 1 -> day >= 20 ? AQUARIUS : CAPRICORN;
			case 2 -> day >= 19 ? PISCES : AQUARIUS;
			case 3 -> day >= 21 ? ARIES : PISCES;
			case 4 -> day >= 20 ? TAURUS : ARIES;
			case 5 -> day >= 21 ? GEMINI : TAURUS;
			case 6 -> day >= 21 ? CANCER : GEMINI;
			case 7 -> day >= 23 ? LEO : CANCER;
			case 8 -> day >= 23 ? VIRGO : LEO;
			case 9 -> day >= 23 ? LIBRA : VIRGO;
			case 10 -> day >= 23 ? SCORPIO : LIBRA;
			case 11 -> day >= 22 ? SAGITTARIUS : SCORPIO;
			case 12 -> day >= 22 ? CAPRICORN : SAGITTARIUS;
			default -> throw new IllegalArgumentException("invalid day or month");
		};
	}

}
