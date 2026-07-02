package com.github.yingzhuo.turbocharger.util.text;

import com.github.yingzhuo.turbocharger.util.CharPool;
import com.github.yingzhuo.turbocharger.util.CharSequenceUtils;
import com.github.yingzhuo.turbocharger.util.StringPool;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class TextStringBuilder implements Serializable, CharSequence, Appendable {

	public static final int CAPACITY = 32;
	private static final char SPACE = CharPool.SPACE;
	private static final int EOS = -1;
	private static final int FALSE_STRING_SIZE = Boolean.FALSE.toString().length();
	private static final int TRUE_STRING_SIZE = Boolean.TRUE.toString().length();
	private char[] buffer;
	@Nullable
	private String newLine;
	@Nullable
	private String nullText;
	private int reallocations;
	private int size;

	public TextStringBuilder() {
		this(CAPACITY);
	}

	private TextStringBuilder(final char[] initialBuffer, final int length) {
		this.buffer = initialBuffer;
		this.size = length;
	}

	public TextStringBuilder(@Nullable CharSequence string) {
		this(CharSequenceUtils.length(string) + CAPACITY);
		if (string != null) {
			append(string);
		}
	}

	public TextStringBuilder(int initialCapacity) {
		buffer = new char[initialCapacity <= 0 ? CAPACITY : initialCapacity];
	}

	public TextStringBuilder append(boolean value) {
		if (value) {
			ensureCapacity(size + TRUE_STRING_SIZE);
			appendTrue(size);
		} else {
			ensureCapacity(size + FALSE_STRING_SIZE);
			appendFalse(size);
		}
		return this;
	}

	@Override
	public TextStringBuilder append(char ch) {
		final int len = length();
		ensureCapacity(len + 1);
		buffer[size++] = ch;
		return this;
	}

	public TextStringBuilder append(@Nullable char[] chars) {
		if (chars == null) {
			return appendNull();
		}
		final int strLen = chars.length;
		if (strLen > 0) {
			final int len = length();
			ensureCapacity(len + strLen);
			System.arraycopy(chars, 0, buffer, len, strLen);
			size += strLen;
		}
		return this;
	}

	public TextStringBuilder append(@Nullable char[] chars, final int startIndex, final int length) {
		if (chars == null) {
			return appendNull();
		}
		if (startIndex < 0 || startIndex > chars.length) {
			throw new StringIndexOutOfBoundsException("Invalid startIndex: " + length);
		}
		if (length < 0 || startIndex + length > chars.length) {
			throw new StringIndexOutOfBoundsException("Invalid length: " + length);
		}
		if (length > 0) {
			final int len = length();
			ensureCapacity(len + length);
			System.arraycopy(chars, startIndex, buffer, len, length);
			size += length;
		}
		return this;
	}

	public TextStringBuilder append(@Nullable CharBuffer str) {
		return append(str, 0, CharSequenceUtils.length(str));
	}

	public TextStringBuilder append(@Nullable CharBuffer buf, int startIndex, int length) {
		if (buf == null) {
			return appendNull();
		}
		if (buf.hasArray()) {
			final int totalLength = buf.remaining();
			if (startIndex < 0 || startIndex > totalLength) {
				throw new StringIndexOutOfBoundsException("startIndex must be valid");
			}
			if (length < 0 || startIndex + length > totalLength) {
				throw new StringIndexOutOfBoundsException("length must be valid");
			}
			final int len = length();
			ensureCapacity(len + length);
			System.arraycopy(buf.array(), buf.arrayOffset() + buf.position() + startIndex, buffer, len, length);
			size += length;
		} else {
			append(buf.toString(), startIndex, length);
		}
		return this;
	}

	@Override
	public TextStringBuilder append(@Nullable CharSequence seq) {
		if (seq == null) {
			return appendNull();
		}
		if (seq instanceof TextStringBuilder) {
			return append((TextStringBuilder) seq);
		}
		if (seq instanceof StringBuilder) {
			return append((StringBuilder) seq);
		}
		if (seq instanceof StringBuffer) {
			return append((StringBuffer) seq);
		}
		if (seq instanceof CharBuffer) {
			return append((CharBuffer) seq);
		}
		return append(seq.toString());
	}

	@Override
	public TextStringBuilder append(@Nullable CharSequence seq, int startIndex, int endIndex) {
		if (seq == null) {
			return appendNull();
		}
		if (endIndex <= 0) {
			throw new StringIndexOutOfBoundsException("endIndex must be valid");
		}
		if (startIndex >= endIndex) {
			throw new StringIndexOutOfBoundsException("endIndex must be greater than startIndex");
		}
		return append(seq.toString(), startIndex, endIndex - startIndex);
	}

	public TextStringBuilder append(double value) {
		return append(String.valueOf(value));
	}

	public TextStringBuilder append(float value) {
		return append(String.valueOf(value));
	}

	public TextStringBuilder append(int value) {
		return append(String.valueOf(value));
	}

	public TextStringBuilder append(long value) {
		return append(String.valueOf(value));
	}

	public TextStringBuilder append(@Nullable Object obj) {
		if (obj == null) {
			return appendNull();
		}
		if (obj instanceof CharSequence cs) {
			return append(cs);
		}
		return append(obj.toString());
	}

	public TextStringBuilder append(@Nullable String str) {
		return append(str, 0, CharSequenceUtils.length(str));
	}

	public TextStringBuilder append(@Nullable String str, int startIndex, int length) {
		if (str == null) {
			return appendNull();
		}
		if (startIndex < 0 || startIndex > str.length()) {
			throw new StringIndexOutOfBoundsException("startIndex must be valid");
		}
		if (length < 0 || startIndex + length > str.length()) {
			throw new StringIndexOutOfBoundsException("length must be valid");
		}
		if (length > 0) {
			final int len = length();
			ensureCapacity(len + length);
			str.getChars(startIndex, startIndex + length, buffer, len);
			size += length;
		}
		return this;
	}

	public TextStringBuilder append(@Nullable StringBuffer str) {
		return append(str, 0, CharSequenceUtils.length(str));
	}

	public TextStringBuilder append(@Nullable StringBuffer str, int startIndex, int length) {
		if (str == null) {
			return appendNull();
		}
		if (startIndex < 0 || startIndex > str.length()) {
			throw new StringIndexOutOfBoundsException("startIndex must be valid");
		}
		if (length < 0 || startIndex + length > str.length()) {
			throw new StringIndexOutOfBoundsException("length must be valid");
		}
		if (length > 0) {
			final int len = length();
			ensureCapacity(len + length);
			str.getChars(startIndex, startIndex + length, buffer, len);
			size += length;
		}
		return this;
	}

	public TextStringBuilder append(@Nullable StringBuilder str) {
		return append(str, 0, CharSequenceUtils.length(str));
	}

	public TextStringBuilder append(@Nullable StringBuilder str, int startIndex, int length) {
		if (str == null) {
			return appendNull();
		}
		if (startIndex < 0 || startIndex > str.length()) {
			throw new StringIndexOutOfBoundsException("startIndex must be valid");
		}
		if (length < 0 || startIndex + length > str.length()) {
			throw new StringIndexOutOfBoundsException("length must be valid");
		}
		if (length > 0) {
			final int len = length();
			ensureCapacity(len + length);
			str.getChars(startIndex, startIndex + length, buffer, len);
			size += length;
		}
		return this;
	}

	public TextStringBuilder append(@Nullable TextStringBuilder str) {
		return append(str, 0, CharSequenceUtils.length(str));
	}

	public TextStringBuilder append(@Nullable TextStringBuilder str, final int startIndex, final int length) {
		if (str == null) {
			return appendNull();
		}
		if (startIndex < 0 || startIndex > str.length()) {
			throw new StringIndexOutOfBoundsException("startIndex must be valid");
		}
		if (length < 0 || startIndex + length > str.length()) {
			throw new StringIndexOutOfBoundsException("length must be valid");
		}
		if (length > 0) {
			final int len = length();
			ensureCapacity(len + length);
			str.getChars(startIndex, startIndex + length, buffer, len);
			size += length;
		}
		return this;
	}

	public TextStringBuilder appendAll(@Nullable Iterable<?> iterable) {
		if (iterable != null) {
			iterable.forEach(this::append);
		}
		return this;
	}

	public TextStringBuilder appendAll(@Nullable Iterator<?> it) {
		if (it != null) {
			it.forEachRemaining(this::append);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> TextStringBuilder appendAll(@Nullable T... array) {
		if (array != null && array.length > 0) {
			for (final Object element : array) {
				append(element);
			}
		}
		return this;
	}

	public TextStringBuilder appendFixedWidthPadLeft(int value, int width, char padChar) {
		return appendFixedWidthPadLeft(String.valueOf(value), width, padChar);
	}

	public TextStringBuilder appendFixedWidthPadLeft(@Nullable Object obj, int width, char padChar) {
		if (width > 0) {
			ensureCapacity(size + width);
			String str = obj == null ? getNullText() : obj.toString();
			if (str == null) {
				str = StringPool.EMPTY;
			}
			final int strLen = str.length();
			if (strLen >= width) {
				str.getChars(strLen - width, strLen, buffer, size);
			} else {
				final int padLen = width - strLen;
				for (int i = 0; i < padLen; i++) {
					buffer[size + i] = padChar;
				}
				str.getChars(0, strLen, buffer, size + padLen);
			}
			size += width;
		}
		return this;
	}

	public TextStringBuilder appendFixedWidthPadRight(int value, int width, char padChar) {
		return appendFixedWidthPadRight(String.valueOf(value), width, padChar);
	}

	public TextStringBuilder appendFixedWidthPadRight(@Nullable Object obj, int width, char padChar) {
		if (width > 0) {
			ensureCapacity(size + width);
			String str = obj == null ? getNullText() : obj.toString();
			if (str == null) {
				str = StringPool.EMPTY;
			}
			final int strLen = str.length();
			if (strLen >= width) {
				str.getChars(0, width, buffer, size);
			} else {
				final int padLen = width - strLen;
				str.getChars(0, strLen, buffer, size);
				for (int i = 0; i < padLen; i++) {
					buffer[size + strLen + i] = padChar;
				}
			}
			size += width;
		}
		return this;
	}

	public TextStringBuilder appendln(boolean value) {
		return append(value).appendNewLine();
	}

	public TextStringBuilder appendln(char ch) {
		return append(ch).appendNewLine();
	}

	public TextStringBuilder appendln(@Nullable char[] chars) {
		return append(chars).appendNewLine();
	}

	public TextStringBuilder appendln(char[] chars, int startIndex, int length) {
		return append(chars, startIndex, length).appendNewLine();
	}

	public TextStringBuilder appendln(double value) {
		return append(value).appendNewLine();
	}

	public TextStringBuilder appendln(float value) {
		return append(value).appendNewLine();
	}

	public TextStringBuilder appendln(int value) {
		return append(value).appendNewLine();
	}

	public TextStringBuilder appendln(long value) {
		return append(value).appendNewLine();
	}

	public TextStringBuilder appendln(@Nullable Object obj) {
		return append(obj).appendNewLine();
	}

	public TextStringBuilder appendln(@Nullable String str) {
		return append(str).appendNewLine();
	}

	public TextStringBuilder appendln(@Nullable String str, int startIndex, int length) {
		return append(str, startIndex, length).appendNewLine();
	}

	public TextStringBuilder appendln(@Nullable StringBuffer str) {
		return append(str).appendNewLine();
	}

	public TextStringBuilder appendln(@Nullable StringBuffer str, int startIndex, int length) {
		return append(str, startIndex, length).appendNewLine();
	}

	public TextStringBuilder appendln(@Nullable StringBuilder str) {
		return append(str).appendNewLine();
	}

	public TextStringBuilder appendln(StringBuilder str, int startIndex, int length) {
		return append(str, startIndex, length).appendNewLine();
	}

	public TextStringBuilder appendln(TextStringBuilder str) {
		return append(str).appendNewLine();
	}

	public TextStringBuilder appendln(TextStringBuilder str, int startIndex, int length) {
		return append(str, startIndex, length).appendNewLine();
	}

	public TextStringBuilder appendNewLine() {
		if (newLine == null) {
			append(System.lineSeparator());
			return this;
		}
		return append(newLine);
	}

	public TextStringBuilder appendNull() {
		if (nullText == null) {
			return this;
		}
		return append(nullText);
	}

	public TextStringBuilder appendPadding(int length, char padChar) {
		if (length >= 0) {
			ensureCapacity(size + length);
			for (int i = 0; i < length; i++) {
				buffer[size++] = padChar;
			}
		}
		return this;
	}

	public TextStringBuilder appendSeparator(char separator) {
		if (isNotEmpty()) {
			append(separator);
		}
		return this;
	}

	public TextStringBuilder appendSeparator(char standard, char defaultIfEmpty) {
		if (isEmpty()) {
			append(defaultIfEmpty);
		} else {
			append(standard);
		}
		return this;
	}

	public TextStringBuilder appendSeparator(String separator) {
		return appendSeparator(separator, null);
	}

	public TextStringBuilder appendSeparator(@Nullable String standard, @Nullable String defaultIfEmpty) {
		final String str = isEmpty() ? defaultIfEmpty : standard;
		if (str != null) {
			append(str);
		}
		return this;
	}

	public TextStringBuilder appendWithSeparators(@Nullable Iterable<?> iterable, @Nullable String separator) {
		if (iterable != null) {
			appendWithSeparators(iterable.iterator(), separator);
		}
		return this;
	}

	public TextStringBuilder appendWithSeparators(@Nullable Iterator<?> it, @Nullable String separator) {
		if (it != null) {
			String sep = Objects.toString(separator, StringPool.EMPTY);
			while (it.hasNext()) {
				append(it.next());
				if (it.hasNext()) {
					append(sep);
				}
			}
		}
		return this;
	}

	public TextStringBuilder appendWithSeparators(@Nullable Object[] array, @Nullable String separator) {
		if (array != null && array.length > 0) {
			String sep = Objects.toString(separator, StringPool.EMPTY);
			append(array[0]);
			for (int i = 1; i < array.length; i++) {
				append(sep);
				append(array[i]);
			}
		}
		return this;
	}

	public StringTokenizer asTokenizer() {
		return new TextStringBuilderTokenizer();
	}

	public String build() {
		return toString();
	}

	public int capacity() {
		return buffer.length;
	}

	@Override
	public char charAt(int index) {
		validateIndex(index);
		return buffer[index];
	}

	public TextStringBuilder clear() {
		size = 0;
		return this;
	}

	public boolean contains(char ch) {
		final char[] thisBuf = buffer;
		for (int i = 0; i < this.size; i++) {
			if (thisBuf[i] == ch) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(String str) {
		return indexOf(str, 0) >= 0;
	}

	public boolean contains(StringMatcher matcher) {
		return indexOf(matcher, 0) >= 0;
	}

	public TextStringBuilder delete(int startIndex, int endIndex) {
		final int actualEndIndex = validateRange(startIndex, endIndex);
		final int len = actualEndIndex - startIndex;
		if (len > 0) {
			deleteImpl(startIndex, actualEndIndex, len);
		}
		return this;
	}

	public TextStringBuilder deleteAll(char ch) {
		for (int i = 0; i < size; i++) {
			if (buffer[i] == ch) {
				final int start = i;
				while (++i < size) {
					if (buffer[i] != ch) {
						break;
					}
				}
				final int len = i - start;
				deleteImpl(start, i, len);
				i -= len;
			}
		}
		return this;
	}

	public TextStringBuilder deleteAll(@Nullable String str) {
		final int len = str == null ? 0 : str.length();
		if (len > 0) {
			int index = indexOf(str, 0);
			while (index >= 0) {
				deleteImpl(index, index + len, len);
				index = indexOf(str, index);
			}
		}
		return this;
	}

	public TextStringBuilder deleteAll(StringMatcher matcher) {
		return replace(matcher, null, 0, size, -1);
	}

	public TextStringBuilder deleteCharAt(int index) {
		validateIndex(index);
		deleteImpl(index, index + 1, 1);
		return this;
	}

	public TextStringBuilder deleteFirst(char ch) {
		for (int i = 0; i < size; i++) {
			if (buffer[i] == ch) {
				deleteImpl(i, i + 1, 1);
				break;
			}
		}
		return this;
	}

	public TextStringBuilder deleteFirst(@Nullable String str) {
		final int len = str == null ? 0 : str.length();
		if (len > 0) {
			final int index = indexOf(str, 0);
			if (index >= 0) {
				deleteImpl(index, index + len, len);
			}
		}
		return this;
	}

	public TextStringBuilder deleteFirst(StringMatcher matcher) {
		return replace(matcher, null, 0, size, 1);
	}

	private void deleteImpl(int startIndex, int endIndex, int len) {
		System.arraycopy(buffer, endIndex, buffer, startIndex, size - endIndex);
		size -= len;
	}

	public boolean endsWith(String str) {
		final int len = str.length();
		if (len == 0) {
			return true;
		}
		if (len > size) {
			return false;
		}
		int pos = size - len;
		for (int i = 0; i < len; i++, pos++) {
			if (buffer[pos] != str.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	public TextStringBuilder ensureCapacity(int capacity) {
		// checks for overflow
		if (capacity > 0 && capacity - buffer.length > 0) {
			reallocate(capacity);
		}
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof TextStringBuilder && equals((TextStringBuilder) obj);
	}

	public boolean equals(@Nullable TextStringBuilder other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (this.size != other.size) {
			return false;
		}
		// Be aware not to use Arrays.equals(buffer, other.buffer) for equals() method
		// as length of the buffers may be different (TEXT-211)
		final char[] thisBuf = this.buffer;
		final char[] otherBuf = other.buffer;
		for (int i = size - 1; i >= 0; i--) {
			if (thisBuf[i] != otherBuf[i]) {
				return false;
			}
		}
		return true;
	}

	public boolean equalsIgnoreCase(@Nullable TextStringBuilder other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (this.size != other.size) {
			return false;
		}
		final char[] thisBuf = this.buffer;
		final char[] otherBuf = other.buffer;
		for (int i = size - 1; i >= 0; i--) {
			final char c1 = thisBuf[i];
			final char c2 = otherBuf[i];
			if (c1 != c2 && Character.toUpperCase(c1) != Character.toUpperCase(c2)) {
				return false;
			}
		}
		return true;
	}

	private void getChars(int startIndex, int endIndex, char[] target, int targetIndex) {
		if (startIndex < 0) {
			throw new StringIndexOutOfBoundsException(startIndex);
		}
		if (endIndex < 0 || endIndex > length()) {
			throw new StringIndexOutOfBoundsException(endIndex);
		}
		if (startIndex > endIndex) {
			throw new StringIndexOutOfBoundsException("end < start");
		}
		System.arraycopy(buffer, startIndex, target, targetIndex, endIndex - startIndex);
	}

	@Nullable
	public String getNewLineText() {
		return newLine;
	}

	public TextStringBuilder setNewLineText(@Nullable String newLine) {
		this.newLine = newLine;
		return this;
	}

	@Nullable
	public String getNullText() {
		return nullText;
	}

	public TextStringBuilder setNullText(@Nullable String nullText) {
		if (nullText != null && nullText.isEmpty()) {
			nullText = null;
		}
		this.nullText = nullText;
		return this;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	public int indexOf(char ch) {
		return indexOf(ch, 0);
	}

	public int indexOf(char ch, int startIndex) {
		startIndex = Math.max(0, startIndex);
		if (startIndex >= size) {
			return -1;
		}
		final char[] thisBuf = buffer;
		for (int i = startIndex; i < size; i++) {
			if (thisBuf[i] == ch) {
				return i;
			}
		}
		return -1;
	}

	public int indexOf(@Nullable String str) {
		return indexOf(str, 0);
	}

	public int indexOf(@Nullable String str, int startIndex) {
		startIndex = Math.max(0, startIndex);
		if (str == null || startIndex >= size) {
			return -1;
		}
		final int strLen = str.length();
		if (strLen == 1) {
			return indexOf(str.charAt(0), startIndex);
		}
		if (strLen == 0) {
			return startIndex;
		}
		if (strLen > size) {
			return -1;
		}
		final char[] thisBuf = buffer;
		final int len = size - strLen + 1;
		outer:
		for (int i = startIndex; i < len; i++) {
			for (int j = 0; j < strLen; j++) {
				if (str.charAt(j) != thisBuf[i + j]) {
					continue outer;
				}
			}
			return i;
		}
		return -1;
	}

	public int indexOf(@Nullable StringMatcher matcher) {
		return indexOf(matcher, 0);
	}

	public int indexOf(@Nullable StringMatcher matcher, int startIndex) {
		startIndex = Math.max(0, startIndex);
		if (matcher == null || startIndex >= size) {
			return -1;
		}
		final int len = size;
		final char[] buf = buffer;
		for (int i = startIndex; i < len; i++) {
			if (matcher.isMatch(buf, i, startIndex, len) > 0) {
				return i;
			}
		}
		return -1;
	}

	public TextStringBuilder insert(int index, boolean value) {
		validateIndex(index);
		if (value) {
			ensureCapacity(size + TRUE_STRING_SIZE);
			System.arraycopy(buffer, index, buffer, index + TRUE_STRING_SIZE, size - index);
			appendTrue(index);
		} else {
			ensureCapacity(size + FALSE_STRING_SIZE);
			System.arraycopy(buffer, index, buffer, index + FALSE_STRING_SIZE, size - index);
			appendFalse(index);
		}
		return this;
	}

	public TextStringBuilder insert(int index, char value) {
		validateIndex(index);
		ensureCapacity(size + 1);
		System.arraycopy(buffer, index, buffer, index + 1, size - index);
		buffer[index] = value;
		size++;
		return this;
	}

	public TextStringBuilder insert(int index, @Nullable char[] chars) {
		validateIndex(index);
		if (chars == null) {
			return insert(index, nullText);
		}
		final int len = chars.length;
		if (len > 0) {
			ensureCapacity(size + len);
			System.arraycopy(buffer, index, buffer, index + len, size - index);
			System.arraycopy(chars, 0, buffer, index, len);
			size += len;
		}
		return this;
	}

	public TextStringBuilder insert(int index, @Nullable char[] chars, int offset, int length) {
		validateIndex(index);
		if (chars == null) {
			return insert(index, nullText);
		}
		if (offset < 0 || offset > chars.length) {
			throw new StringIndexOutOfBoundsException("Invalid offset: " + offset);
		}
		if (length < 0 || offset + length > chars.length) {
			throw new StringIndexOutOfBoundsException("Invalid length: " + length);
		}
		if (length > 0) {
			ensureCapacity(size + length);
			System.arraycopy(buffer, index, buffer, index + length, size - index);
			System.arraycopy(chars, offset, buffer, index, length);
			size += length;
		}
		return this;
	}

	public TextStringBuilder insert(int index, double value) {
		return insert(index, String.valueOf(value));
	}

	public TextStringBuilder insert(int index, float value) {
		return insert(index, String.valueOf(value));
	}

	public TextStringBuilder insert(int index, int value) {
		return insert(index, String.valueOf(value));
	}

	public TextStringBuilder insert(int index, long value) {
		return insert(index, String.valueOf(value));
	}

	public TextStringBuilder insert(int index, @Nullable Object obj) {
		if (obj == null) {
			return insert(index, nullText);
		}
		return insert(index, obj.toString());
	}

	public TextStringBuilder insert(int index, @Nullable String str) {
		validateIndex(index);
		if (str == null) {
			str = nullText;
		}
		if (str != null) {
			final int strLen = str.length();
			if (strLen > 0) {
				final int newSize = size + strLen;
				ensureCapacity(newSize);
				System.arraycopy(buffer, index, buffer, index + strLen, size - index);
				size = newSize;
				str.getChars(0, strLen, buffer, index);
			}
		}
		return this;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isNotEmpty() {
		return size != 0;
	}

	public int lastIndexOf(char ch) {
		return lastIndexOf(ch, size - 1);
	}

	public int lastIndexOf(char ch, int startIndex) {
		startIndex = startIndex >= size ? size - 1 : startIndex;
		if (startIndex < 0) {
			return -1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (buffer[i] == ch) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(@Nullable String str) {
		return lastIndexOf(str, size - 1);
	}

	public int lastIndexOf(@Nullable String str, int startIndex) {
		startIndex = startIndex >= size ? size - 1 : startIndex;
		if (str == null || startIndex < 0) {
			return -1;
		}
		final int strLen = str.length();
		if (strLen > 0 && strLen <= size) {
			if (strLen == 1) {
				return lastIndexOf(str.charAt(0), startIndex);
			}

			outer:
			for (int i = startIndex - strLen + 1; i >= 0; i--) {
				for (int j = 0; j < strLen; j++) {
					if (str.charAt(j) != buffer[i + j]) {
						continue outer;
					}
				}
				return i;
			}

		} else if (strLen == 0) {
			return startIndex;
		}
		return -1;
	}

	public int lastIndexOf(StringMatcher matcher) {
		return lastIndexOf(matcher, size);
	}

	public int lastIndexOf(StringMatcher matcher, int startIndex) {
		startIndex = startIndex >= size ? size - 1 : startIndex;
		if (startIndex < 0) {
			return -1;
		}
		final char[] buf = buffer;
		final int endIndex = startIndex + 1;
		for (int i = startIndex; i >= 0; i--) {
			if (matcher.isMatch(buf, i, 0, endIndex) > 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int length() {
		return size;
	}

	public TextStringBuilder minimizeCapacity() {
		if (buffer.length > size) {
			reallocate(size);
		}
		return this;
	}

	private void reallocate(int newLength) {
		this.buffer = Arrays.copyOf(buffer, newLength);
		this.reallocations++;
	}

	public TextStringBuilder replace(int startIndex, int endIndex, String replaceStr) {
		endIndex = validateRange(startIndex, endIndex);
		final int insertLen = CharSequenceUtils.length(replaceStr);
		replaceImpl(startIndex, endIndex, endIndex - startIndex, replaceStr, insertLen);
		return this;
	}

	public TextStringBuilder replace(StringMatcher matcher, String replaceStr, int startIndex, int endIndex,
									 int replaceCount) {
		endIndex = validateRange(startIndex, endIndex);
		return replaceImpl(matcher, replaceStr, startIndex, endIndex, replaceCount);
	}

	public TextStringBuilder replaceAll(char search, char replace) {
		if (search != replace) {
			for (int i = 0; i < size; i++) {
				if (buffer[i] == search) {
					buffer[i] = replace;
				}
			}
		}
		return this;
	}

	public TextStringBuilder replaceAll(@Nullable String searchStr, String replaceStr) {
		int searchLen = searchStr == null ? 0 : searchStr.length();
		if (searchLen > 0) {
			int replaceLen = CharSequenceUtils.length(replaceStr);
			int index = indexOf(searchStr, 0);
			while (index >= 0) {
				replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen);
				index = indexOf(searchStr, index + replaceLen);
			}
		}
		return this;
	}

	public TextStringBuilder replaceAll(StringMatcher matcher, String replaceStr) {
		return replace(matcher, replaceStr, 0, size, -1);
	}

	public TextStringBuilder replaceFirst(char search, char replace) {
		if (search != replace) {
			for (int i = 0; i < size; i++) {
				if (buffer[i] == search) {
					buffer[i] = replace;
					break;
				}
			}
		}
		return this;
	}

	public TextStringBuilder replaceFirst(String searchStr, String replaceStr) {
		final int searchLen = CharSequenceUtils.length(searchStr);
		if (searchLen > 0) {
			final int index = indexOf(searchStr, 0);
			if (index >= 0) {
				final int replaceLen = CharSequenceUtils.length(replaceStr);
				replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen);
			}
		}
		return this;
	}

	public TextStringBuilder replaceFirst(StringMatcher matcher, String replaceStr) {
		return replace(matcher, replaceStr, 0, size, 1);
	}

	private void replaceImpl(int startIndex, int endIndex, int removeLen, String insertStr, int insertLen) {
		int newSize = size - removeLen + insertLen;
		if (insertLen != removeLen) {
			ensureCapacity(newSize);
			System.arraycopy(buffer, endIndex, buffer, startIndex + insertLen, size - endIndex);
			size = newSize;
		}
		if (insertLen > 0) {
			insertStr.getChars(0, insertLen, buffer, startIndex);
		}
	}

	private TextStringBuilder replaceImpl(@Nullable StringMatcher matcher, String replaceStr, int from, int to,
										  int replaceCount) {
		if (matcher == null || size == 0) {
			return this;
		}
		final int replaceLen = CharSequenceUtils.length(replaceStr);
		for (int i = from; i < to && replaceCount != 0; i++) {
			final char[] buf = buffer;
			final int removeLen = matcher.isMatch(buf, i, from, to);
			if (removeLen > 0) {
				replaceImpl(i, i + removeLen, removeLen, replaceStr, replaceLen);
				to = to - removeLen + replaceLen;
				i = i + replaceLen - 1;
				if (replaceCount > 0) {
					replaceCount--;
				}
			}
		}
		return this;
	}

	public TextStringBuilder reverse() {
		if (size == 0) {
			return this;
		}

		final int half = size / 2;
		final char[] buf = buffer;
		for (int leftIdx = 0, rightIdx = size - 1; leftIdx < half; leftIdx++, rightIdx--) {
			final char swap = buf[leftIdx];
			buf[leftIdx] = buf[rightIdx];
			buf[rightIdx] = swap;
		}
		return this;
	}

	public TextStringBuilder set(CharSequence str) {
		clear();
		append(str);
		return this;
	}

	public TextStringBuilder setCharAt(int index, char ch) {
		validateIndex(index);
		buffer[index] = ch;
		return this;
	}

	public int size() {
		return size;
	}

	public boolean startsWith(String str) {
		final int len = str.length();
		if (len == 0) {
			return true;
		}
		if (len > size) {
			return false;
		}
		for (int i = 0; i < len; i++) {
			if (buffer[i] != str.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	public String substring(int start) {
		return substring(start, size);
	}

	public String substring(int startIndex, int endIndex) {
		endIndex = validateRange(startIndex, endIndex);
		return new String(buffer, startIndex, endIndex - startIndex);
	}

	@Override
	public CharSequence subSequence(int startIndex, int endIndex) {
		if (startIndex < 0) {
			throw new StringIndexOutOfBoundsException(startIndex);
		}
		if (endIndex > size) {
			throw new StringIndexOutOfBoundsException(endIndex);
		}
		if (startIndex > endIndex) {
			throw new StringIndexOutOfBoundsException(endIndex - startIndex);
		}
		return substring(startIndex, endIndex);
	}

	public char[] toCharArray() {
		return size == 0 ? new char[0] : Arrays.copyOf(buffer, size);
	}

	public char[] toCharArray(int startIndex, int endIndex) {
		endIndex = validateRange(startIndex, endIndex);
		final int len = endIndex - startIndex;
		return len == 0 ? new char[0] : Arrays.copyOfRange(buffer, startIndex, endIndex);
	}

	@Override
	public String toString() {
		return new String(buffer, 0, size);
	}

	public StringBuffer toStringBuffer() {
		return new StringBuffer(size).append(buffer, 0, size);
	}

	public StringBuilder toStringBuilder() {
		return new StringBuilder(size).append(buffer, 0, size);
	}

	public TextStringBuilder trim() {
		if (size == 0) {
			return this;
		}
		int len = size;
		char[] buf = buffer;
		int pos = 0;
		while (pos < len && buf[pos] <= SPACE) {
			pos++;
		}
		while (pos < len && buf[len - 1] <= SPACE) {
			len--;
		}
		if (len < size) {
			delete(len, size);
		}
		if (pos > 0) {
			delete(0, pos);
		}
		return this;
	}

	private void validateIndex(int index) {
		if (index < 0 || index >= size) {
			throw new StringIndexOutOfBoundsException(index);
		}
	}

	private int validateRange(int startIndex, int endIndex) {
		if (startIndex < 0) {
			throw new StringIndexOutOfBoundsException(startIndex);
		}
		if (endIndex > size) {
			endIndex = size;
		}
		if (startIndex > endIndex) {
			throw new StringIndexOutOfBoundsException("end < start");
		}
		return endIndex;
	}

	private void appendTrue(int index) {
		buffer[index++] = 't';
		buffer[index++] = 'r';
		buffer[index++] = 'u';
		buffer[index] = 'e';
		size += TRUE_STRING_SIZE;
	}

	private void appendFalse(int index) {
		buffer[index++] = 'f';
		buffer[index++] = 'a';
		buffer[index++] = 'l';
		buffer[index++] = 's';
		buffer[index] = 'e';
		size += FALSE_STRING_SIZE;
	}

	private char[] getBuffer() {
		return this.buffer;
	}

	private class TextStringBuilderTokenizer extends StringTokenizer {

		private TextStringBuilderTokenizer() {
		}

		@Override
		public String getContent() {
			final String str = super.getContent();
			if (str == null) {
				return TextStringBuilder.this.toString();
			}
			return str;
		}

		@Override
		protected List<String> tokenize(@Nullable final char[] chars, final int offset, final int count) {
			if (chars == null) {
				return super.tokenize(TextStringBuilder.this.getBuffer(), 0, TextStringBuilder.this.size());
			}
			return super.tokenize(chars, offset, count);
		}
	}

}
