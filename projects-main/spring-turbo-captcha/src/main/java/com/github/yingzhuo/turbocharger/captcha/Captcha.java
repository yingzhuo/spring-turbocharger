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
package com.github.yingzhuo.turbocharger.captcha;

import org.springframework.util.Assert;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

/**
 * 人机验证码
 *
 * @author 应卓
 * @since 1.0.0
 */
public final class Captcha implements Serializable {

	private final String word;
	private final BufferedImage image;

	public Captcha(String word, BufferedImage image) {
		Assert.hasLength(word, "word is required");
		Assert.notNull(image, "image is required");
		this.word = word;
		this.image = image;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Captcha captcha = (Captcha) o;
		return word.equals(captcha.word) && image.equals(captcha.image);
	}

	@Override
	public int hashCode() {
		return Objects.hash(word, image);
	}

	public String getWord() {
		return word;
	}

	public BufferedImage getImage() {
		return image;
	}

}
