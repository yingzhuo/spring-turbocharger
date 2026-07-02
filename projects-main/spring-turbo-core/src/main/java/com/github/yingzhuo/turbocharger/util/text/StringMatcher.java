package com.github.yingzhuo.turbocharger.util.text;

import com.github.yingzhuo.turbocharger.util.CharPool;
import com.github.yingzhuo.turbocharger.util.CharSequenceUtils;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;

import static com.github.yingzhuo.turbocharger.util.CharPool.*;

public interface StringMatcher {

	public static StringMatcher andMatcher(@Nullable StringMatcher... matchers) {
		if (matchers == null || matchers.length == 0) {
			return new None();
		}
		if (matchers.length == 1) {
			return matchers[0];
		} else {
			return new And(matchers);
		}
	}

	public static StringMatcher noneMatcher() {
		return new None();
	}

	public static StringMatcher charMatcher(char ch) {
		return new Char(ch);
	}

	public static StringMatcher charSetMatcher(@Nullable char... chars) {
		final int len = chars != null ? chars.length : 0;
		if (len == 0) {
			return new None();
		}
		if (len == 1) {
			return new Char(chars[0]);
		}
		return new CharSet(chars);
	}

	public static StringMatcher charSetMatcher(@Nullable String chars) {
		if (chars == null) {
			return new None();
		}
		final int len = chars.length();
		if (len == 0) {
			return new None();
		}
		if (len == 1) {
			return new Char(chars.charAt(0));
		}
		return new CharSet(chars.toCharArray());
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static StringMatcher commaMatcher() {
		return new Char(COMMA);
	}

	public static StringMatcher singleQuoteMatcher() {
		return new Char(SINGLE_QUOTE);
	}

	public static StringMatcher doubleQuoteMatcher() {
		return new Char(DOUBLE_QUOTE);
	}

	public static StringMatcher quoteMatcher() {
		return new CharSet(new char[]{'\'', '"'});
	}

	public static StringMatcher spaceMatcher() {
		return new Char(SPACE);
	}

	public static StringMatcher tabMatcher() {
		return new Char(TAB);
	}

	public static StringMatcher hyphenMatcher() {
		return new Char(HYPHEN);
	}

	public static StringMatcher colonMatcher() {
		return new Char(COLON);
	}

	public static StringMatcher doubleColonMatcher() {
		return new CharArray("::".toCharArray());
	}

	public static StringMatcher underscoreMatcher() {
		return new Char(UNDERSCORE);
	}

	public static StringMatcher atMarkerMatcher() {
		return new Char(AT_SIGN);
	}

	public static StringMatcher dotMatcher() {
		return new Char(DOT);
	}

	public static StringMatcher semicolonMatcher() {
		return new Char(SEMICOLON);
	}

	public static StringMatcher slashMatcher() {
		return new Char(SLASH);
	}

	public static StringMatcher backslashMatcher() {
		return new Char(BACKSLASH);
	}

	public static StringMatcher slashAndBackslashMatcher() {
		return new CharSet(new char[]{SLASH, BACKSLASH});
	}

	public static StringMatcher whitespaceMatcher() {
		return new Whitespace();
	}

	public static StringMatcher numericMatcher() {
		return new CharSet(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
	}

	public static StringMatcher lowerMatcher() {
		return new CharSet(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'});
	}

	public static StringMatcher upperMatcher() {
		return new CharSet(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'});
	}

	public static StringMatcher alphaMatcher() {
		return new CharSet(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			// ------
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z'});
	}

	public static StringMatcher alphanumericMatcher() {
		return new CharSet(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			// ------
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z',
			// ------
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
	}

	public static StringMatcher splitMatcher() {
		return new CharSet(" \t\n\r\f".toCharArray());
	}

	public static StringMatcher stringMatcher(@Nullable char... string) {
		final int length = string != null ? string.length : 0;
		return length == 0 ? new None() : length == 1 ? new Char(string[0]) : new CharArray(string);
	}

	public static StringMatcher stringMatcher(@Nullable String string) {
		if (string == null) {
			return new None();
		} else {
			return string.isEmpty() ? new None() : stringMatcher(string.toCharArray());
		}
	}

	public default int isMatch(final char[] buffer, final int pos) {
		return isMatch(buffer, pos, 0, buffer.length);
	}

	public int isMatch(char[] buffer, int start, int bufferStart, int bufferEnd);

	public default int isMatch(final CharSequence buffer, final int pos) {
		return isMatch(buffer, pos, 0, buffer.length());
	}

	public default int isMatch(final CharSequence buffer, final int start, final int bufferStart, final int bufferEnd) {
		return isMatch(CharSequenceUtils.toCharArray(buffer), start, bufferEnd, bufferEnd);
	}

	public default int size() {
		return 0;
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static final class And implements StringMatcher {

		private final StringMatcher[] matchers;

		public And(StringMatcher... matchers) {
			this.matchers = matchers.clone();
		}

		@Override
		public int isMatch(char[] buffer, int start, int bufferStart, int bufferEnd) {
			int total = 0;
			int curStart = start;
			for (StringMatcher stringMatcher : matchers) {
				if (stringMatcher != null) {
					int len = stringMatcher.isMatch(buffer, curStart, bufferStart, bufferEnd);
					if (len == 0) {
						return 0;
					}
					total += len;
					curStart += len;
				}
			}
			return total;
		}

		@Override
		public int isMatch(CharSequence buffer, int start, int bufferStart, int bufferEnd) {
			int total = 0;
			int curStart = start;
			for (StringMatcher stringMatcher : matchers) {
				if (stringMatcher != null) {
					int len = stringMatcher.isMatch(buffer, curStart, bufferStart, bufferEnd);
					if (len == 0) {
						return 0;
					}
					total += len;
					curStart += len;
				}
			}
			return total;
		}

		@Override
		public int size() {
			int total = 0;
			for (StringMatcher stringMatcher : matchers) {
				if (stringMatcher != null) {
					total += stringMatcher.size();
				}
			}
			return total;
		}
	}

	public static final class CharArray implements StringMatcher {

		private final char[] chars;
		private final String string;

		public CharArray(char... chars) {
			this.chars = chars.clone();
			this.string = String.valueOf(chars);
		}

		@Override
		public int isMatch(char[] buffer, int start, int bufferStart, int bufferEnd) {
			int len = size();
			if (start + len > bufferEnd) {
				return 0;
			}
			int j = start;
			for (int i = 0; i < len; i++, j++) {
				if (chars[i] != buffer[j]) {
					return 0;
				}
			}
			return len;
		}

		@Override
		public int isMatch(CharSequence buffer, int start, int bufferStart, int bufferEnd) {
			int len = size();
			if (start + len > bufferEnd) {
				return 0;
			}
			int j = start;
			for (int i = 0; i < len; i++, j++) {
				if (chars[i] != buffer.charAt(j)) {
					return 0;
				}
			}
			return len;
		}

		@Override
		public int size() {
			return chars.length;
		}
	}

	public static final class Char implements StringMatcher {

		private final char ch;

		public Char(char ch) {
			this.ch = ch;
		}

		@Override
		public int isMatch(char[] buffer, int start, int bufferStart, int bufferEnd) {
			return ch == buffer[start] ? 1 : 0;
		}

		@Override
		public int isMatch(CharSequence buffer, int start, int bufferStart, int bufferEnd) {
			return ch == buffer.charAt(start) ? 1 : 0;
		}

		@Override
		public int size() {
			return 1;
		}
	}

	public static class CharSet implements StringMatcher {

		private final char[] chars;

		public CharSet(char[] chars) {
			this.chars = chars.clone();
			Arrays.sort(this.chars);
		}

		@Override
		public int isMatch(char[] buffer, int start, int bufferStart, int bufferEnd) {
			return Arrays.binarySearch(chars, buffer[start]) >= 0 ? 1 : 0;
		}

		@Override
		public int isMatch(CharSequence buffer, int start, int bufferStart, int bufferEnd) {
			return Arrays.binarySearch(chars, buffer.charAt(start)) >= 0 ? 1 : 0;
		}

		@Override
		public int size() {
			return 1;
		}
	}

	public static final class None implements StringMatcher {

		public None() {
		}

		@Override
		public int isMatch(char[] buffer, int start, int bufferStart, int bufferEnd) {
			return 0;
		}

		@Override
		public int isMatch(CharSequence buffer, int start, int bufferStart, int bufferEnd) {
			return 0;
		}

		@Override
		public int size() {
			return 0;
		}
	}

	public static final class Whitespace implements StringMatcher {

		private static final int SPACE_INT = (int) CharPool.SPACE; // 32

		public Whitespace() {
		}

		@Override
		public int isMatch(char[] buffer, int start, int bufferStart, int bufferEnd) {
			return buffer[start] <= SPACE_INT ? 1 : 0;
		}

		@Override
		public int isMatch(CharSequence buffer, int start, int bufferStart, int bufferEnd) {
			return buffer.charAt(start) <= SPACE_INT ? 1 : 0;
		}

		@Override
		public int size() {
			return 1;
		}
	}

}
