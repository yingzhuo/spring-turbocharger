package com.github.yingzhuo.turbocharger.captcha.google.filter.predefined;

import com.github.yingzhuo.turbocharger.captcha.google.filter.FilterFactory;

import java.awt.image.BufferedImage;

public class NoopFilterFactory implements FilterFactory {

	@Override
	public BufferedImage apply(BufferedImage source) {
		return source;
	}

}
