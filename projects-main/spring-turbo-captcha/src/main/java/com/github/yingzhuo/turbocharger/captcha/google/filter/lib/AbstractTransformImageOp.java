/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.captcha.google.filter.lib;

/**
 * @since 1.0.0
 */
public abstract class AbstractTransformImageOp extends AbstractImageOp {

	private boolean initialized;

	public AbstractTransformImageOp() {
		setEdgeMode(EDGE_CLAMP);
	}

	protected abstract void transform(int x, int y, double[] t);

	protected void init() {
	}

	@Override
	protected void filter(int[] inPixels, int[] outPixels, int width, int height) {
		if (!initialized) {
			init();
			initialized = true;
		}
		double[] t = new double[2];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				transform(x, y, t);
				int pixel = getPixelBilinear(inPixels, t[0], t[1], width, height, getEdgeMode());
				outPixels[x + y * width] = pixel;
			}
		}
	}

}
