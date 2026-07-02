package com.github.yingzhuo.turbocharger.captcha.google.background;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.0.0
 */
@FunctionalInterface
public interface BackgroundFactory {

	public void fillBackground(BufferedImage dest);

}
