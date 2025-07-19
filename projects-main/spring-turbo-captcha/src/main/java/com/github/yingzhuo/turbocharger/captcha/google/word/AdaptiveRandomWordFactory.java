/*
 * Copyright 2022-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *    ____             _            _____           _
 *   / ___| _ __  _ __(_)_ __   __ |_   _|   _ _ __| |__   ___
 *   ___ | '_ | '__| | '_  / _` || || | | | '__| '_  / _
 *    ___) | |_) | |  | | | | | (_| || || |_| | |  | |_) | (_) |
 *   |____/| .__/|_|  |_|_| |_|__, ||_| __,_|_|  |_.__/ ___/
 *         |_|                 |___/   https://github.com/yingzhuo/spring-turbo
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.github.yingzhuo.turbocharger.captcha.google.word;

import java.util.Random;

/**
 * @author 应卓
 * @since 1.0.0
 */
public class AdaptiveRandomWordFactory extends RandomWordFactory {

	protected String wideCharacters;

	public AdaptiveRandomWordFactory() {
		this.characters = "absdegkmnopwx23456789";
		this.wideCharacters = "mw";
		this.minLength = 6;
		this.maxLength = 6;
	}

	public void setWideCharacters(String wideCharacters) {
		this.wideCharacters = wideCharacters;
	}

	@Override
	public Word getNextWord() {
		final Random rnd = new Random();
		final StringBuilder sb = new StringBuilder();
		final StringBuilder chars = new StringBuilder(characters);
		int l = minLength + (maxLength > minLength ? rnd.nextInt(maxLength - minLength) : 0);
		for (int i = 0; i < l; i++) {
			int j = rnd.nextInt(chars.length());
			char c = chars.charAt(j);
			if (wideCharacters.indexOf(c) != -1) {
				for (int k = 0; k < wideCharacters.length(); k++) {
					int idx = chars.indexOf(String.valueOf(wideCharacters.charAt(k)));
					if (idx != -1) {
						chars.deleteCharAt(idx);
					}
				}
			}
			sb.append(c);
		}

		final Word word = new Word();
		word.setStringForDrawing(sb.toString());
		word.setStringForValidation(sb.toString());
		return word;
	}

}
