/*
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
 */
package com.github.yingzhuo.turbocharger.captcha.google;

import com.github.yingzhuo.turbocharger.captcha.CaptchaService;
import com.github.yingzhuo.turbocharger.captcha.google.background.SingleColorBackgroundFactory;
import com.github.yingzhuo.turbocharger.captcha.google.color.RandomColorFactory;
import com.github.yingzhuo.turbocharger.captcha.google.filter.predefined.CurvesAbstractRippleFilterFactory;
import com.github.yingzhuo.turbocharger.captcha.google.font.RandomFontFactory;
import com.github.yingzhuo.turbocharger.captcha.google.renderer.BestFitTextRenderer;
import com.github.yingzhuo.turbocharger.captcha.google.word.AdaptiveRandomWordFactory;

/**
 * @author 应卓
 * @since 1.0.0
 */
public class GoogleCaptchaService extends AbstractGoogleCaptchaService implements CaptchaService {

	public GoogleCaptchaService() {
		super();
		backgroundFactory = new SingleColorBackgroundFactory();
		wordFactory = new AdaptiveRandomWordFactory();
		fontFactory = new RandomFontFactory();
		textRenderer = new BestFitTextRenderer();
		colorFactory = new RandomColorFactory();
		filterFactory = new CurvesAbstractRippleFilterFactory(colorFactory);
		textRenderer.setLeftMargin(10);
		textRenderer.setRightMargin(10);
		width = 160;
		height = 70;
	}

}
