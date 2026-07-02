package com.github.yingzhuo.turbocharger.captcha.google.filter;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface FilterFactory {

	public BufferedImage apply(BufferedImage source);

}
