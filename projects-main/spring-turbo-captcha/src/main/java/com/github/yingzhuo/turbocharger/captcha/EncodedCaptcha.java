package com.github.yingzhuo.turbocharger.captcha;

import com.github.yingzhuo.turbocharger.util.io.ImageUtils;
import lombok.Getter;
import org.springframework.util.Assert;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

/**
 * 经过编码的人机验证码
 *
 * @author 应卓
 * @see Captcha
 * @see #of(Captcha)
 * @since 1.0.0
 */
public final class EncodedCaptcha implements Serializable {

	private final Captcha captcha;

	@Getter
	private final String encodedImage;

	/**
	 * 私有构造方法
	 *
	 * @param captcha 人机验证码
	 */
	private EncodedCaptcha(Captcha captcha) {
		this.captcha = captcha;
		this.encodedImage = ImageUtils.encodeToBase64(captcha.getImage(), "png");
	}

	public static EncodedCaptcha of(Captcha captcha) {
		Assert.notNull(captcha, "captcha is required");
		return new EncodedCaptcha(captcha);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EncodedCaptcha that = (EncodedCaptcha) o;
		return captcha.equals(that.captcha);
	}

	@Override
	public int hashCode() {
		return Objects.hash(captcha);
	}

	public String getWord() {
		return captcha.getWord();
	}

	public BufferedImage getImage() {
		return captcha.getImage();
	}

}
