package com.github.yingzhuo.turbocharger.zxing.qrcode;

import com.github.yingzhuo.turbocharger.zxing.exception.WritingException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.jspecify.annotations.Nullable;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.EnumMap;

public class QRCodeGeneratorImpl implements QRCodeGenerator {

	@Override
	public BufferedImage generate(String content,
								  @Nullable Logo logo,
								  @Nullable ErrorCorrectionLevel errorCorrectionLevel,
								  int size) {
		try {
			var hints = new EnumMap<>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.ERROR_CORRECTION,
				errorCorrectionLevel != null ? errorCorrectionLevel : ErrorCorrectionLevel.H);
			hints.put(EncodeHintType.MARGIN, 1);


			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size,
				hints);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
				}
			}

			if (logo != null) {
				insertLogo(image, logo, size);
			}

			return image;
		} catch (WriterException e) {
			throw new WritingException(e);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	private void insertLogo(BufferedImage source, Logo logo, int qrCodeSize) {
		Image src = logo.getImage();
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (logo.isCompress()) { // 压缩LOGO
			if (width > 60) {
				width = 60;
			}
			if (height > 60) {
				height = 60;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}

		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (qrCodeSize - width) / 2;
		int y = (qrCodeSize - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}
}
