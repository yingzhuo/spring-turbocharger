/*
 *
 * Copyright 2022-present the original author or authors.
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
 *
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
public class RandomWordFactory implements WordFactory {

	protected String characters;
	protected int minLength;
	protected int maxLength;

	public RandomWordFactory() {
		characters = "absdegkmnopwx23456789";
		minLength = 6;
		maxLength = 6;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public Word getNextWord() {
		final Random rnd = new Random();
		final StringBuilder sb = new StringBuilder();
		int l = minLength + (maxLength > minLength ? rnd.nextInt(maxLength - minLength) : 0);
		for (int i = 0; i < l; i++) {
			int j = rnd.nextInt(characters.length());
			sb.append(characters.charAt(j));
		}
		final Word word = new Word();
		word.setStringForDrawing(sb.toString());
		word.setStringForValidation(sb.toString());
		return word;
	}

}
