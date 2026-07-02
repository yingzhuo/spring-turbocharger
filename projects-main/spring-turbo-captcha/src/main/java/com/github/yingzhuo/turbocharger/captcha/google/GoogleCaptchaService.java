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
