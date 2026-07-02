package com.github.yingzhuo.turbocharger.captcha.google.word;

import com.github.yingzhuo.turbocharger.util.RandomUtils;
import com.github.yingzhuo.turbocharger.util.StringFormatter;

public class MathWordFactory implements WordFactory {

	public MathWordFactory() {
	}

	@Override
	public Word getNextWord() {
		final int left = RandomUtils.nextInt(1, 100);
		final int right = RandomUtils.nextInt(1, 100);
		final boolean op = RandomUtils.nextBoolean();

		final Word word = new Word();
		word.setStringForDrawing(getStringForDrawing(left, op, right));
		word.setStringForValidation(getStringForValidation(left, op, right));
		return word;
	}

	// true -> +
	// false -> -
	private String getStringForDrawing(int left, boolean op, int right) {
		return StringFormatter.format("{} {} {}", left, op ? "+" : "-", right);
	}

	// true -> +
	// false -> -
	private String getStringForValidation(int left, boolean op, int right) {
		int value = op ? left + right : left - right;
		return String.valueOf(value);
	}

}
