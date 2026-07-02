package com.github.yingzhuo.turbocharger.util.io;

import com.github.yingzhuo.turbocharger.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public final class ImageUtils {

	private ImageUtils() {
	}

	public static byte[] toByteArray(BufferedImage image, String format) {
		try {
			var os = new ByteArrayOutputStream();
			ImageIO.write(image, format, os);
			return os.toByteArray();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static String encodeToBase64(BufferedImage image, String format) {
		var bytes = toByteArray(image, format);
		var base64Bytes = Base64Utils.encode(bytes, false, true);
		return new String(base64Bytes, StandardCharsets.UTF_8);
	}

}
