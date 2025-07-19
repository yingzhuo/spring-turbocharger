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
package com.github.yingzhuo.turbocharger.captcha.google;

import com.github.yingzhuo.turbocharger.captcha.Captcha;
import com.github.yingzhuo.turbocharger.captcha.CaptchaService;
import com.github.yingzhuo.turbocharger.captcha.google.background.BackgroundFactory;
import com.github.yingzhuo.turbocharger.captcha.google.color.ColorFactory;
import com.github.yingzhuo.turbocharger.captcha.google.filter.FilterFactory;
import com.github.yingzhuo.turbocharger.captcha.google.font.FontFactory;
import com.github.yingzhuo.turbocharger.captcha.google.renderer.TextRenderer;
import com.github.yingzhuo.turbocharger.captcha.google.word.Word;
import com.github.yingzhuo.turbocharger.captcha.google.word.WordFactory;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.0.0
 */
public abstract class AbstractGoogleCaptchaService implements CaptchaService {

	protected FontFactory fontFactory;
	protected WordFactory wordFactory;
	protected ColorFactory colorFactory;
	protected BackgroundFactory backgroundFactory;
	protected TextRenderer textRenderer;
	protected FilterFactory filterFactory;
	protected int width;
	protected int height;

	public FontFactory getFontFactory() {
		return fontFactory;
	}

	public void setFontFactory(FontFactory fontFactory) {
		this.fontFactory = fontFactory;
	}

	public WordFactory getWordFactory() {
		return wordFactory;
	}

	public void setWordFactory(WordFactory wordFactory) {
		this.wordFactory = wordFactory;
	}

	public ColorFactory getColorFactory() {
		return colorFactory;
	}

	public void setColorFactory(ColorFactory colorFactory) {
		this.colorFactory = colorFactory;
	}

	public BackgroundFactory getBackgroundFactory() {
		return backgroundFactory;
	}

	public void setBackgroundFactory(BackgroundFactory backgroundFactory) {
		this.backgroundFactory = backgroundFactory;
	}

	public TextRenderer getTextRenderer() {
		return textRenderer;
	}

	public void setTextRenderer(TextRenderer textRenderer) {
		this.textRenderer = textRenderer;
	}

	public FilterFactory getFilterFactory() {
		return filterFactory;
	}

	public void setFilterFactory(FilterFactory filterFactory) {
		this.filterFactory = filterFactory;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public Captcha create() {
		BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		backgroundFactory.fillBackground(bufImage);
		Word word = wordFactory.getNextWord();
		textRenderer.draw(word.getStringForDrawing(), bufImage, fontFactory, colorFactory);
		BufferedImage image = filterFactory.apply(bufImage);
		return new Captcha(word.getStringForValidation(), image);
	}

}
