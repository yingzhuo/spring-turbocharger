package com.github.yingzhuo.turbocharger.util.text;

import com.github.yingzhuo.turbocharger.util.StringPool;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StringTokenizer implements ListIterator<String>, Serializable, Cloneable {

	private static final StringTokenizer CSV_TOKENIZER_PROTOTYPE;
	private static final StringTokenizer TSV_TOKENIZER_PROTOTYPE;

	static {
		CSV_TOKENIZER_PROTOTYPE = new StringTokenizer();
		CSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StringMatcher.commaMatcher());
		CSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StringMatcher.doubleQuoteMatcher());
		CSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StringMatcher.noneMatcher());
		CSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StringMatcher.whitespaceMatcher());
		CSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
		CSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);

		TSV_TOKENIZER_PROTOTYPE = new StringTokenizer();
		TSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StringMatcher.tabMatcher());
		TSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StringMatcher.doubleQuoteMatcher());
		TSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StringMatcher.noneMatcher());
		TSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StringMatcher.whitespaceMatcher());
		TSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
		TSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
	}

	@Nullable
	private char[] chars;

	@Nullable
	private String[] tokens;

	private int tokenPos = 0;

	private StringMatcher delimMatcher = StringMatcher.splitMatcher();

	private StringMatcher quoteMatcher = StringMatcher.noneMatcher();

	private StringMatcher ignoredMatcher = StringMatcher.noneMatcher();

	private StringMatcher trimmerMatcher = StringMatcher.noneMatcher();

	private boolean emptyAsNull = false;

	private boolean ignoreEmptyTokens = false;

	public StringTokenizer() {
		this.chars = null;
	}

	public StringTokenizer(@Nullable char[] input) {
		this.chars = input != null ? input.clone() : null;
	}

	public StringTokenizer(@Nullable char[] input, char delimiter) {
		this(input);
		setDelimiterChar(delimiter);
	}

	public StringTokenizer(@Nullable char[] input, char delimiter, char quote) {
		this(input, delimiter);
		setQuoteChar(quote);
	}

	public StringTokenizer(@Nullable char[] input, String delimiter) {
		this(input);
		setDelimiterString(delimiter);
	}

	public StringTokenizer(@Nullable char[] input, StringMatcher delimiter) {
		this(input);
		setDelimiterMatcher(delimiter);
	}

	public StringTokenizer(@Nullable char[] input, final StringMatcher delimiter, final StringMatcher quote) {
		this(input, delimiter);
		setQuoteMatcher(quote);
	}

	public StringTokenizer(@Nullable String input) {
		this.chars = input != null ? input.toCharArray() : null;
	}

	public StringTokenizer(@Nullable String input, char delimiter) {
		this(input);
		setDelimiterChar(delimiter);
	}

	public StringTokenizer(@Nullable String input, char delimiter, char quote) {
		this(input, delimiter);
		setQuoteChar(quote);
	}

	public StringTokenizer(@Nullable String input, String delimiter) {
		this(input);
		setDelimiterString(delimiter);
	}

	public StringTokenizer(@Nullable String input, StringMatcher delimiter) {
		this(input);
		setDelimiterMatcher(delimiter);
	}

	public StringTokenizer(@Nullable String input, StringMatcher delimiter, StringMatcher quote) {
		this(input, delimiter);
		setQuoteMatcher(quote);
	}

	private static StringTokenizer getCSVClone() {
		return (StringTokenizer) CSV_TOKENIZER_PROTOTYPE.clone();
	}

	public static StringTokenizer getCSVInstance() {
		return getCSVClone();
	}

	public static StringTokenizer getCSVInstance(final char[] input) {
		return getCSVClone().reset(input);
	}

	public static StringTokenizer getCSVInstance(final String input) {
		return getCSVClone().reset(input);
	}

	private static StringTokenizer getTSVClone() {
		return (StringTokenizer) TSV_TOKENIZER_PROTOTYPE.clone();
	}

	public static StringTokenizer newInstance(@Nullable char[] input) {
		return new StringTokenizer(input);
	}

	public static StringTokenizer newInstance(@Nullable String input) {
		return new StringTokenizer(input);
	}

	public static StringTokenizer newInstance() {
		return new StringTokenizer();
	}

	public static StringTokenizer getTSVInstance() {
		return getTSVClone();
	}

	public static StringTokenizer getTSVInstance(final char[] input) {
		return getTSVClone().reset(input);
	}

	public static StringTokenizer getTSVInstance(final String input) {
		return getTSVClone().reset(input);
	}

	@Override
	public void add(String obj) {
		throw new UnsupportedOperationException("add() is unsupported");
	}

	private void addToken(List<String> list, @Nullable String tok) {
		if (tok == null || tok.isEmpty()) {
			if (isIgnoreEmptyTokens()) {
				return;
			}
			if (isEmptyTokenAsNull()) {
				tok = null;
			}
		}
		list.add(tok);
	}

	private void checkTokenized() {
		if (tokens == null) {
			final List<String> split;
			if (chars == null) {
				// still call tokenize as subclass may do some work
				split = tokenize(null, 0, 0);
			} else {
				split = tokenize(chars, 0, chars.length);
			}
			tokens = split.toArray(new String[0]);
		}
	}

	@Override
	public Object clone() {
		try {
			return cloneReset();
		} catch (final CloneNotSupportedException ex) {
			return null;
		}
	}

	Object cloneReset() throws CloneNotSupportedException {
		// this method exists to enable 100% test coverage
		final StringTokenizer cloned = (StringTokenizer) super.clone();
		if (cloned.chars != null) {
			cloned.chars = cloned.chars.clone();
		}
		cloned.reset();
		return cloned;
	}

	@Nullable
	public String getContent() {
		if (chars == null) {
			return null;
		}
		return new String(chars);
	}

	public StringMatcher getDelimiterMatcher() {
		return this.delimMatcher;
	}

	public StringTokenizer setDelimiterMatcher(StringMatcher matcher) {
		this.delimMatcher = matcher;
		return this;
	}

	public StringMatcher getIgnoredMatcher() {
		return ignoredMatcher;
	}

	public StringTokenizer setIgnoredMatcher(StringMatcher matcher) {
		this.ignoredMatcher = matcher;
		return this;
	}

	public StringMatcher getQuoteMatcher() {
		return quoteMatcher;
	}

	public StringTokenizer setQuoteMatcher(StringMatcher matcher) {
		this.quoteMatcher = matcher;
		return this;
	}

	public StringMatcher getTrimmerMatcher() {
		return trimmerMatcher;
	}

	public StringTokenizer setTrimmerMatcher(StringMatcher matcher) {
		this.trimmerMatcher = matcher;
		return this;
	}

	public boolean isEmptyTokenAsNull() {
		return this.emptyAsNull;
	}

	public StringTokenizer setEmptyTokenAsNull(boolean emptyAsNull) {
		this.emptyAsNull = emptyAsNull;
		return this;
	}

	public boolean isIgnoreEmptyTokens() {
		return ignoreEmptyTokens;
	}

	public StringTokenizer setIgnoreEmptyTokens(boolean ignoreEmptyTokens) {
		this.ignoreEmptyTokens = ignoreEmptyTokens;
		return this;
	}

	public String[] getTokenArray() {
		checkTokenized();
		return tokens.clone();
	}

	public String[] getCheckedTokenArray(int expectCount) {
		return getCheckedTokenArray(expectCount, () -> new IllegalArgumentException("expect count not match"));
	}

	public String[] getCheckedTokenArray(int expectCount, Supplier<RuntimeException> exceptionSupplier) {
		checkTokenized();
		if (tokens.length != expectCount) {
			throw exceptionSupplier.get();
		}
		return tokens.clone();
	}

	public List<String> getTokenList() {
		checkTokenized();
		return Arrays.asList(tokens);
	}

	public List<String> getCheckedTokenList(int expectCount) {
		return getCheckedTokenList(expectCount, () -> new IllegalArgumentException("expect count not match"));
	}

	public List<String> getCheckedTokenList(int expectCount, Supplier<RuntimeException> exceptionSupplier) {
		checkTokenized();
		if (tokens.length != expectCount) {
			throw exceptionSupplier.get();
		}
		return Arrays.asList(tokens);
	}

	public Stream<String> getTokenStream() {
		checkTokenized();
		return Stream.of(tokens);
	}

	@Override
	public boolean hasNext() {
		checkTokenized();
		return tokenPos < tokens.length;
	}

	@Override
	public boolean hasPrevious() {
		checkTokenized();
		return tokenPos > 0;
	}

	private boolean isQuote(final char[] srcChars, final int pos, final int len, final int quoteStart,
							final int quoteLen) {
		for (int i = 0; i < quoteLen; i++) {
			if (pos + i >= len || srcChars[pos + i] != srcChars[quoteStart + i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String next() {
		if (hasNext()) {
			return tokens[tokenPos++];
		}
		throw new NoSuchElementException();
	}

	@Override
	public int nextIndex() {
		return tokenPos;
	}

	@Override
	public String previous() {
		if (hasPrevious()) {
			return tokens[--tokenPos];
		}
		throw new NoSuchElementException();
	}

	@Override
	public int previousIndex() {
		return tokenPos - 1;
	}

	private int readNextToken(final char[] srcChars, int start, final int len, final TextStringBuilder workArea,
							  final List<String> tokenList) {
		// skip all leading whitespace, unless it is the
		// field delimiter or the quote character
		while (start < len) {
			final int removeLen = Math.max(getIgnoredMatcher().isMatch(srcChars, start, start, len),
				getTrimmerMatcher().isMatch(srcChars, start, start, len));
			if (removeLen == 0 || getDelimiterMatcher().isMatch(srcChars, start, start, len) > 0
				|| getQuoteMatcher().isMatch(srcChars, start, start, len) > 0) {
				break;
			}
			start += removeLen;
		}

		// handle reaching end
		if (start >= len) {
			addToken(tokenList, StringPool.EMPTY);
			return -1;
		}

		// handle empty token
		final int delimLen = getDelimiterMatcher().isMatch(srcChars, start, start, len);
		if (delimLen > 0) {
			addToken(tokenList, StringPool.EMPTY);
			return start + delimLen;
		}

		// handle found token
		final int quoteLen = getQuoteMatcher().isMatch(srcChars, start, start, len);
		if (quoteLen > 0) {
			return readWithQuotes(srcChars, start + quoteLen, len, workArea, tokenList, start, quoteLen);
		}
		return readWithQuotes(srcChars, start, len, workArea, tokenList, 0, 0);
	}

	private int readWithQuotes(final char[] srcChars, final int start, final int len, final TextStringBuilder workArea,
							   final List<String> tokenList, final int quoteStart, final int quoteLen) {
		// Loop until we've found the end of the quoted
		// string or the end of the input
		workArea.clear();
		int pos = start;
		boolean quoting = quoteLen > 0;
		int trimStart = 0;

		while (pos < len) {
			// quoting mode can occur several times throughout a string
			// we must switch between quoting and non-quoting until we
			// encounter a non-quoted delimiter, or end of string
			if (quoting) {
				// In quoting mode

				// If we've found a quote character, see if it's
				// followed by a second quote. If so, then we need
				// to actually put the quote character into the token
				// rather than end the token.
				if (isQuote(srcChars, pos, len, quoteStart, quoteLen)) {
					if (isQuote(srcChars, pos + quoteLen, len, quoteStart, quoteLen)) {
						// matched pair of quotes, thus an escaped quote
						workArea.append(srcChars, pos, quoteLen);
						pos += quoteLen * 2;
						trimStart = workArea.size();
						continue;
					}

					// end of quoting
					quoting = false;
					pos += quoteLen;
					continue;
				}

			} else {
				// Not in quoting mode

				// check for delimiter, and thus end of token
				final int delimLen = getDelimiterMatcher().isMatch(srcChars, pos, start, len);
				if (delimLen > 0) {
					// return condition when end of token found
					addToken(tokenList, workArea.substring(0, trimStart));
					return pos + delimLen;
				}

				// check for quote, and thus back into quoting mode
				if (quoteLen > 0 && isQuote(srcChars, pos, len, quoteStart, quoteLen)) {
					quoting = true;
					pos += quoteLen;
					continue;
				}

				// check for ignored (outside quotes), and ignore
				final int ignoredLen = getIgnoredMatcher().isMatch(srcChars, pos, start, len);
				if (ignoredLen > 0) {
					pos += ignoredLen;
					continue;
				}

				// check for trimmed character
				// don't yet know if its at the end, so copy to workArea
				// use trimStart to keep track of trim at the end
				final int trimmedLen = getTrimmerMatcher().isMatch(srcChars, pos, start, len);
				if (trimmedLen > 0) {
					workArea.append(srcChars, pos, trimmedLen);
					pos += trimmedLen;
					continue;
				}
			}
			// copy regular character from inside quotes
			workArea.append(srcChars[pos++]);
			trimStart = workArea.size();
		}

		// return condition when end of string found
		addToken(tokenList, workArea.substring(0, trimStart));
		return -1;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove() is unsupported");
	}

	@Override
	public void set(final String obj) {
		throw new UnsupportedOperationException("set() is unsupported");
	}

	public StringTokenizer reset() {
		tokenPos = 0;
		tokens = null;
		return this;
	}

	public StringTokenizer reset(@Nullable char[] input) {
		reset();
		this.chars = input != null ? input.clone() : null;
		return this;
	}

	public StringTokenizer reset(@Nullable String input) {
		reset();
		this.chars = input != null ? input.toCharArray() : null;
		return this;
	}

	public StringTokenizer setDelimiterChar(char delimiter) {
		return setDelimiterMatcher(StringMatcher.charMatcher(delimiter));
	}

	public StringTokenizer setDelimiterString(String delimiter) {
		return setDelimiterMatcher(StringMatcher.stringMatcher(delimiter));
	}

	public StringTokenizer setIgnoredChar(char ignored) {
		return setIgnoredMatcher(StringMatcher.charMatcher(ignored));
	}

	public StringTokenizer setQuoteChar(char quote) {
		return setQuoteMatcher(StringMatcher.charMatcher(quote));
	}

	public int size() {
		checkTokenized();
		return tokens.length;
	}

	protected List<String> tokenize(@Nullable char[] srcChars, int offset, int count) {
		if (srcChars == null || count == 0) {
			return Collections.emptyList();
		}
		final TextStringBuilder buf = new TextStringBuilder();
		final List<String> tokenList = new ArrayList<>();
		int pos = offset;

		// loop around the entire buffer
		while (pos >= 0 && pos < count) {
			// find next token
			pos = readNextToken(srcChars, pos, count, buf, tokenList);

			// handle case where end of string is a delimiter
			if (pos >= count) {
				addToken(tokenList, StringPool.EMPTY);
			}
		}
		return tokenList;
	}

}
