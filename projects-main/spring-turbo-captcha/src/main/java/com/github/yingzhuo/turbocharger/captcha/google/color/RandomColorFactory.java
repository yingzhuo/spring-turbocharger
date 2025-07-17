/*
 * Copyright 2025-present the original author or authors.
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
package com.github.yingzhuo.turbocharger.captcha.google.color;

import java.awt.*;
import java.util.Random;

/**
 * @author 应卓
 * @since 1.0.0
 */
public class RandomColorFactory implements ColorFactory {

	private Color min;
	private Color max;

	public RandomColorFactory() {
		min = new Color(0, 0, 0);
		max = new Color(255, 255, 255);
	}

	public void setMin(Color min) {
		this.min = min;
	}

	public void setMax(Color max) {
		this.max = max;
	}

	@Override
	public Color getColor(int index) {
		Random r = new Random();
		return new Color(min.getRed() + r.nextInt((max.getRed() - min.getRed())),
			min.getGreen() + r.nextInt((max.getGreen() - min.getGreen())),
			min.getBlue() + r.nextInt((max.getBlue() - min.getBlue())));
	}

}
