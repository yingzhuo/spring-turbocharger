/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.captcha.verysimple;

import com.github.yingzhuo.turbocharger.captcha.Captcha;
import com.github.yingzhuo.turbocharger.captcha.CaptchaService;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 简易实现，只适合极少数应用或非严肃的开发
 *
 * @author 应卓
 * @since 1.0.0
 */
@Setter
public class SimpleCaptchaService implements CaptchaService {

	private static final Random RANDOM = new Random();

	private int length = 6;
	private int width = 100;
	private int height = 18;
	private String characters = "absdegkmnopwxABSDEGKMNOPWX23456789";
	private String font = "Times New Roman";

	@Override
	public Captcha create() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(getRandomColor(200, 250));
		g.fillRect(1, 1, width - 1, height - 1);
		g.setColor(new Color(102, 102, 102));
		g.drawRect(0, 0, width - 1, height - 1);
		g.setFont(new Font(font, Font.PLAIN, 17));
		g.setColor(getRandomColor(160, 200));

		// 画随机线
		for (int i = 0; i < 155; i++) {
			int x = RANDOM.nextInt(width - 1);
			int y = RANDOM.nextInt(height - 1);
			int xl = RANDOM.nextInt(6) + 1;
			int yl = RANDOM.nextInt(12) + 1;
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 从另一方向画随机线
		for (int i = 0; i < 70; i++) {
			int x = RANDOM.nextInt(width - 1);
			int y = RANDOM.nextInt(height - 1);
			int xl = RANDOM.nextInt(12) + 1;
			int yl = RANDOM.nextInt(6) + 1;
			g.drawLine(x, y, x - xl, y - yl);
		}

		// 生成随机数,并将随机数字转换为字母
		final StringBuilder captchaValue = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char ch = getRandomChar();
			captchaValue.append(ch);
			g.setColor(new Color(20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110)));
			g.drawString(String.valueOf(ch), 15 * i + 10, 16);
		}
		g.dispose();

		return new Captcha(captchaValue.toString(), image);
	}

	private char getRandomChar() {
		if (characters == null || characters.isEmpty()) {
			return (char) (RANDOM.nextInt(26) + 65);
		} else {
			int index = RANDOM.nextInt(characters.length());
			return characters.charAt(index);
		}
	}

	private Color getRandomColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + RANDOM.nextInt(bc - fc);
		int g = fc + RANDOM.nextInt(bc - fc);
		int b = fc + RANDOM.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
