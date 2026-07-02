package com.github.yingzhuo.turbocharger.zxing.barcode;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 3.4.3
 */
public interface BarCodeGenerator {

	public BufferedImage generate(String content, int width, int height);
}
