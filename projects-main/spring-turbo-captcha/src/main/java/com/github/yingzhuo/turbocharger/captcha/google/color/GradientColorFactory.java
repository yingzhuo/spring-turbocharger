/*
 *
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
 *
 */
package com.github.yingzhuo.turbocharger.captcha.google.color;

import java.awt.*;

/**
 * @author 应卓
 * @since 1.0.0
 */
public class GradientColorFactory implements ColorFactory {

	private Color start;
	private Color step;

	public GradientColorFactory() {
		start = new Color(192, 192, 0);
		step = new Color(192, 128, 128);
	}

	@Override
	public Color getColor(int index) {
		return new Color((start.getRed() + step.getRed() * index) % 256,
			(start.getGreen() + step.getGreen() * index) % 256, (start.getBlue() + step.getBlue() * index) % 256);
	}

	public void setStart(Color start) {
		this.start = start;
	}

	public void setStep(Color step) {
		this.step = step;
	}

}
