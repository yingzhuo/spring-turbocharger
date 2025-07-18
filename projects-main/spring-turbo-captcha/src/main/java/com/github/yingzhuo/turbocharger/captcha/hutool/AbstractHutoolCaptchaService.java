/*
 * Copyright 2022-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.turbocharger.captcha.hutool;

import cn.hutool.captcha.ICaptcha;
import com.github.yingzhuo.turbocharger.captcha.Captcha;
import com.github.yingzhuo.turbocharger.captcha.CaptchaService;
import com.github.yingzhuo.turbocharger.util.io.CloseUtils;
import org.springframework.lang.NonNull;

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

	@NonNull
	protected abstract ICaptcha createCaptcha();

}
