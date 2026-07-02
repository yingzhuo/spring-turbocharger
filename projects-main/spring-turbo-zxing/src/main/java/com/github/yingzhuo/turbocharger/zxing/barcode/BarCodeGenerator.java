package com.github.yingzhuo.turbocharger.zxing.barcode;

import java.awt.image.BufferedImage;

public interface BarCodeGenerator {

	public BufferedImage generate(String content, int width, int height);
}
