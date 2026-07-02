package com.github.yingzhuo.turbocharger.zxing.qrcode;

import com.github.yingzhuo.turbocharger.util.io.ImageUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.jspecify.annotations.Nullable;

import java.awt.image.BufferedImage;

public interface QRCodeGenerator {

	public BufferedImage generate(String content,
								  @Nullable Logo logo,
								  @Nullable ErrorCorrectionLevel errorCorrectionLevel,
								  int size);

}
