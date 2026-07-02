package com.github.yingzhuo.turbocharger.captcha.hutool;

import cn.hutool.captcha.ICaptcha;
import com.github.yingzhuo.turbocharger.captcha.Captcha;
import com.github.yingzhuo.turbocharger.captcha.CaptchaService;
import com.github.yingzhuo.turbocharger.util.io.CloseUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 * @since 1.0.1
 */
public abstract class AbstractHutoolCaptchaService implements CaptchaService {

	@Override
	public final Captcha create() {
		final ICaptcha c = createCaptcha();
		return new Captcha(c.getCode(), toBufferedImage(c));
	}

	private BufferedImage toBufferedImage(ICaptcha c) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;

		try {
			c.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
			return ImageIO.read(in);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} finally {
			CloseUtils.closeQuietly(in);
			CloseUtils.closeQuietly(out);
		}
	}

	protected abstract ICaptcha createCaptcha();

}
