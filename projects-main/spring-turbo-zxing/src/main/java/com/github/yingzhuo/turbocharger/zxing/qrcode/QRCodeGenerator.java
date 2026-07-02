package com.github.yingzhuo.turbocharger.zxing.qrcode;

import com.github.yingzhuo.turbocharger.util.io.ImageUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.jspecify.annotations.Nullable;

import java.awt.image.BufferedImage;

/**
 * QRCode生成器默认实现
 *
 * @author 应卓
 * @see ImageUtils
 * @since 1.0.0
 */
public interface QRCodeGenerator {

	public BufferedImage generate(String content,
								  @Nullable Logo logo,
								  @Nullable ErrorCorrectionLevel errorCorrectionLevel,
								  int size);

}
