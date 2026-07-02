package com.github.yingzhuo.turbocharger.util;

import com.github.yingzhuo.turbocharger.util.collection.CollectionUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.yingzhuo.turbocharger.util.StringPool.COMMA;
import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

public final class StringUtils {

	private StringUtils() {
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static boolean isEmpty(@Nullable String string) {
		return string == null || string.isEmpty();
	}

	public static boolean isNotEmpty(@Nullable String string) {
		return !isEmpty(string);
	}

	public static boolean isBlank(@Nullable String string) {
		return string == null || string.isBlank();
	}

	public static boolean isNotBlank(@Nullable String string) {
		return !isBlank(string);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static int length(@Nullable final String string) {
		return string == null ? 0 : string.length();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static boolean containsWhitespace(String string) {
		Assert.notNull(string, "string is null");

		int strLen = string.length();
		for (int i = 0; i < strLen; i++) {
			if (CharUtils.isWhitespace(string.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsAnyChars(String string, String charsToCheck) {
		Assert.notNull(string, "string is null");

		final Set<Character> charSet = toCharSet(string);
		if (charSet.isEmpty()) {
			return false;
		}
		return toCharStream(charsToCheck).anyMatch(charSet::contains);
	}

	public static boolean containsAllChars(String string, String charsToCheck) {
		Assert.notNull(string, "string is null");

		final Set<Character> charSet = toCharSet(string);
		if (charSet.isEmpty()) {
			return false;
		}
		return toCharStream(charsToCheck).allMatch(charSet::contains);
	}

	public static boolean containsNoneChars(String string, String charsToCheck) {
		Assert.notNull(string, "string is null");

		final Set<Character> charSet = toCharSet(string);
		if (charSet.isEmpty()) {
			return true;
		}
		return toCharStream(charsToCheck).noneMatch(charSet::contains);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static int countMatches(String string, char ch) {
		Assert.notNull(string, "string is null");

		if (isEmpty(string)) {
			return 0;
		}
		int count = 0;
		// We could also call str.toCharArray() for faster look ups but that would generate more garbage.
		for (int i = 0; i < string.length(); i++) {
			if (ch == string.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static String deleteWhitespace(String string) {
		Assert.notNull(string, "string is null");

		if (string.isEmpty()) {
			return string;
		}

		final int sz = string.length();
		final char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(string.charAt(i))) {
				chs[count++] = string.charAt(i);
			}
		}
		if (count == sz) {
			return string;
		}
		if (count == 0) {
			return EMPTY;
		}
		return new String(chs, 0, count);
	}

	public static String deleteChars(String string, String charsToDelete) {
		Assert.notNull(string, "string is null");

		if (isEmpty(charsToDelete)) {
			return string;
		}

		final Set<Character> charsToDeleteSet = toCharSet(charsToDelete);

		final StringBuilder builder = new StringBuilder();
		toCharList(string).stream().filter(c -> !charsToDeleteSet.contains(c)).forEach(builder::append);
		return builder.toString();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static Stream<Character> toCharStream(@Nullable String string) {
		if (string == null) {
			return Stream.empty();
		}
		return string.chars().mapToObj(ch -> (char) ch);
	}

	public static List<Character> toCharList(@Nullable String string) {
		return toCharStream(string).collect(Collectors.toList());
	}

	public static Set<Character> toCharSet(@Nullable String string) {
		return toCharStream(string).collect(Collectors.toSet());
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static String reverse(String string) {
		Assert.notNull(string, "string is null");
		return new StringBuilder(string).reverse().toString();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static String[] commaDelimitedListToStringArray(@Nullable String string) {
		return commaDelimitedListToStringArray(string, false);
	}

	public static String[] commaDelimitedListToStringArray(@Nullable String string, boolean trimAllElements) {
		if (string == null || isBlank(string)) {
			return new String[0];
		}

		final String[] array = string.split(COMMA);
		if (trimAllElements) {
			return Arrays.stream(array).map(String::trim).toArray(String[]::new);
		} else {
			return array;
		}
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static String capitalize(final String string) {
		Assert.notNull(string, "string is null");

		final int strLen = length(string);
		if (strLen == 0) {
			return string;
		}

		final int firstCodepoint = string.codePointAt(0);
		final int newCodePoint = Character.toTitleCase(firstCodepoint);
		if (firstCodepoint == newCodePoint) {
			// already capitalized
			return string;
		}

		final int[] newCodePoints = new int[strLen]; // cannot be longer than the char array
		int outOffset = 0;
		newCodePoints[outOffset++] = newCodePoint; // copy the first codepoint
		for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
			final int codepoint = string.codePointAt(inOffset);
			newCodePoints[outOffset++] = codepoint; // copy the remaining ones
			inOffset += Character.charCount(codepoint);
		}
		return new String(newCodePoints, 0, outOffset);
	}

	public static String uncapitalize(final String string) {
		Assert.notNull(string, "string is null");

		final int strLen = length(string);
		if (strLen == 0) {
			return string;
		}

		final int firstCodepoint = string.codePointAt(0);
		final int newCodePoint = Character.toLowerCase(firstCodepoint);
		if (firstCodepoint == newCodePoint) {
			// already capitalized
			return string;
		}

		final int[] newCodePoints = new int[strLen]; // cannot be longer than the char array
		int outOffset = 0;
		newCodePoints[outOffset++] = newCodePoint; // copy the first codepoint
		for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
			final int codepoint = string.codePointAt(inOffset);
			newCodePoints[outOffset++] = codepoint; // copy the remaining ones
			inOffset += Character.charCount(codepoint);
		}
		return new String(newCodePoints, 0, outOffset);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static void nullSafeAdd(Collection<String> collection, @Nullable String element) {
		CollectionUtils.nullSafeAdd(collection, element);
	}

	public static void nullSafeAddAll(Collection<String> collection, @Nullable String... elements) {
		CollectionUtils.nullSafeAddAll(collection, elements);
	}

	public static void nullSafeAddAll(Collection<String> collection, @Nullable Collection<String> elements) {
		CollectionUtils.nullSafeAddAll(collection, elements);
	}

	public static void emptySafeAdd(Collection<String> collection, @Nullable String element) {
		emptySafeAddAll(collection, element);
	}

	public static void emptySafeAddAll(Collection<String> collection, @Nullable String... elements) {
		Assert.notNull(collection, "collection is required");

		if (elements != null) {
			for (String element : elements) {
				if (isNotEmpty(element)) {
					collection.add(element);
				}
			}
		}
	}

	public static void emptySafeAddAll(Collection<String> collection, @Nullable Collection<String> elements) {
		Assert.notNull(collection, "collection is required");

		if (elements != null) {
			for (String element : elements) {
				if (isNotEmpty(element)) {
					collection.add(element);
				}
			}
		}
	}

	public static void blankSafeAdd(Collection<String> collection, @Nullable String element) {
		blankSafeAddAll(collection, element);
	}

	public static void blankSafeAddAll(Collection<String> collection, @Nullable String... elements) {
		Assert.notNull(collection, "collection is required");

		if (elements != null) {
			for (String element : elements) {
				if (isNotBlank(element)) {
					collection.add(element);
				}
			}
		}
	}

	public static void blankSafeAddAll(Collection<String> collection, @Nullable Collection<String> elements) {
		Assert.notNull(collection, "collection is required");

		if (elements != null) {
			for (String element : elements) {
				if (isNotBlank(element)) {
					collection.add(element);
				}
			}
		}
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static String nullSafeJoin(@Nullable Iterable<?> elements, @Nullable String separator) {
		if (elements == null) {
			return EMPTY;
		}

		var strJoiner = new StringJoiner(Objects.requireNonNullElse(separator, EMPTY));
		for (var ele : elements) {
			if (ele != null) {
				strJoiner.add(String.valueOf(ele));
			}
		}
		return strJoiner.toString();
	}

	public static String nullSafeJoin(@Nullable Iterator<?> elements, @Nullable String separator) {
		if (elements == null) {
			return EMPTY;
		}

		var strJoiner = new StringJoiner(Objects.requireNonNullElse(separator, EMPTY));
		while (elements.hasNext()) {
			var ele = elements.next();
			if (ele != null) {
				strJoiner.add(String.valueOf(ele));
			}
		}
		return strJoiner.toString();
	}

	public static String nullSafeJoin(@Nullable Object[] elements, @Nullable String separator) {
		if (elements == null) {
			return EMPTY;
		}

		var strJoiner = new StringJoiner(Objects.requireNonNullElse(separator, EMPTY));
		for (var ele : elements) {
			if (ele != null) {
				strJoiner.add(String.valueOf(ele));
			}
		}
		return strJoiner.toString();
	}

	public static String nullSafeJoin(@Nullable String[] elements, @Nullable String separator) {
		if (elements == null) {
			return EMPTY;
		}

		var strJoiner = new StringJoiner(Objects.requireNonNullElse(separator, EMPTY));
		for (var ele : elements) {
			if (ele != null) {
				strJoiner.add(ele);
			}
		}
		return strJoiner.toString();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static boolean startsWith(@Nullable String string, String prefix) {
		Assert.notNull(prefix, "prefix is required");

		if (string == null)
			return false;
		return string.startsWith(prefix);
	}

	public static boolean endsWith(@Nullable String string, String suffix) {
		Assert.notNull(suffix, "suffix is required");

		if (string == null)
			return false;
		return string.endsWith(suffix);
	}

	public static boolean startsWithIgnoreCase(@Nullable String string, String prefix) {
		Assert.notNull(prefix, "prefix is required");

		return (string != null && string.length() >= prefix.length()
			&& string.regionMatches(true, 0, prefix, 0, prefix.length()));
	}

	public static boolean endsWithIgnoreCase(@Nullable String string, String suffix) {
		Assert.notNull(suffix, "suffix is required");

		return (string != null && string.length() >= suffix.length()
			&& string.regionMatches(true, string.length() - suffix.length(), suffix, 0, suffix.length()));
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static String repeat(String string, int n) {
		Assert.notNull(string, "string is required");
		Assert.isTrue(n >= 1, "n must greater than 0");

		if (n == 1 || string.equals("")) {
			return string;
		}

		final StringBuilder builder = new StringBuilder(string.length() * n);
		while (n-- != 0) {
			builder.append(string);
		}
		return builder.toString();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static int compare(@Nullable String string1, @Nullable String string2) {
		return compare(string1, string2, true);
	}

	public static int compare(@Nullable String string1, @Nullable String string2, final boolean nullIsLess) {
		if (string1 == string2) {
			return 0;
		}
		if (string1 == null) {
			return nullIsLess ? -1 : 1;
		}
		if (string2 == null) {
			return nullIsLess ? 1 : -1;
		}
		return string1.compareTo(string2);
	}

	public static int compareIgnoreCase(@Nullable String string1, @Nullable String string2) {
		return compareIgnoreCase(string1, string2, true);
	}

	public static int compareIgnoreCase(@Nullable String string1, @Nullable String string2, final boolean nullIsLess) {
		if (string1 == string2) {
			return 0;
		}
		if (string1 == null) {
			return nullIsLess ? -1 : 1;
		}
		if (string2 == null) {
			return nullIsLess ? 1 : -1;
		}
		return string1.compareToIgnoreCase(string2);
	}

	// -----------------------------------------------------------------------------------------------------------------

	@Nullable
	public static String firstNonEmpty(@Nullable String... strings) {
		if (strings != null) {
			for (String val : strings) {
				if (isNotEmpty(val)) {
					return val;
				}
			}
		}
		return null;
	}

	@Nullable
	public static String firstNonBlank(@Nullable String... values) {
		if (values != null) {
			for (String val : values) {
				if (isNotBlank(val)) {
					return val;
				}
			}
		}
		return null;
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static String joinWithComma(Collection<?> collection) {
		return join(collection, COMMA);
	}

	public static String join(Collection<?> collection) {
		return join(collection, null);
	}

	public static String join(Collection<?> collection, @Nullable String delimiter) {
		Assert.notNull(collection, "collection is null");
		return String.join(
			Objects.requireNonNullElse(delimiter, EMPTY),
			collection.stream()
				.map(Object::toString)
				.toList()
		);
	}

	// -----------------------------------------------------------------------------------------------------------------

	@Nullable
	public static String emptyToNull(@Nullable String string) {
		if (string == null || string.isEmpty()) {
			return null;
		}
		return string;
	}

	@Nullable
	public static String blankToNull(@Nullable String string) {
		if (string == null || string.isBlank()) {
			return null;
		}
		return string;
	}

}
