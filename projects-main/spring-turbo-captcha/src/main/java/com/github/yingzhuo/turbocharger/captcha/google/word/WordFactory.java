package com.github.yingzhuo.turbocharger.captcha.google.word;

/**
 * @author 应卓
 * @since 1.0.0
 */
@FunctionalInterface
public interface WordFactory {

	public Word getNextWord();

}
