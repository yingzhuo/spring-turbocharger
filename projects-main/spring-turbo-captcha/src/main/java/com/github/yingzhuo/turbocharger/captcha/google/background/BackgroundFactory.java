package com.github.yingzhuo.turbocharger.captcha.google.background;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface BackgroundFactory {

	public void fillBackground(BufferedImage dest);

}
