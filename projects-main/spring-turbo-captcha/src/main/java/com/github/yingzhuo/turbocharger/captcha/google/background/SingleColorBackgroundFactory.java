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
package com.github.yingzhuo.turbocharger.captcha.google.background;

import com.github.yingzhuo.turbocharger.captcha.google.color.ColorFactory;
import com.github.yingzhuo.turbocharger.captcha.google.color.SingleColorFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.0.0
 */
public class SingleColorBackgroundFactory implements BackgroundFactory {

	private ColorFactory colorFactory;

	public SingleColorBackgroundFactory() {
		colorFactory = new SingleColorFactory(Color.WHITE);
	}

	public SingleColorBackgroundFactory(Color color) {
		colorFactory = new SingleColorFactory(color);
	}

	public void setColorFactory(ColorFactory colorFactory) {
		this.colorFactory = colorFactory;
	}

	@Override
	public void fillBackground(BufferedImage dest) {
		Graphics g = dest.getGraphics();
		g.setColor(colorFactory.getColor(0));
		g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
	}

}
