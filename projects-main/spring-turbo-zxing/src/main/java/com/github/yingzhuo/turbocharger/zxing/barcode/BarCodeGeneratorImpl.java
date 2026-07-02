package com.github.yingzhuo.turbocharger.zxing.barcode;

import com.github.yingzhuo.turbocharger.zxing.exception.WritingException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.awt.image.BufferedImage;
import java.util.EnumMap;

/**
 * @author 应卓
 * @since 3.4.3
 */
public class BarCodeGeneratorImpl implements BarCodeGenerator {

	@Override
	public BufferedImage generate(String content, int width, int height) {
		try {
			var hints = new EnumMap<>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 1);

			// 生成条形码矩阵
			var bitMatrix = new MultiFormatWriter()
				.encode(
					content,
					BarcodeFormat.CODE_128, // 格式：CODE_128、EAN_13 等
					width,
					height,
					hints
				);

			return MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			throw new WritingException(e);
		}
	}

}
