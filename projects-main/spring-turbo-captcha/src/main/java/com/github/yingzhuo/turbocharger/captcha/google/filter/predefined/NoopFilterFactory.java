package com.github.yingzhuo.turbocharger.captcha.google.filter.predefined;

import com.github.yingzhuo.turbocharger.captcha.google.filter.FilterFactory;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.0.0
 */
public class NoopFilterFactory implements FilterFactory {

	@Override
	public BufferedImage apply(BufferedImage source) {
		return source;
	}

}
