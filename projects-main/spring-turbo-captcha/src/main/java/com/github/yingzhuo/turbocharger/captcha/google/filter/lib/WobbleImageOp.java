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
package com.github.yingzhuo.turbocharger.captcha.google.filter.lib;

/**
 * @since 1.0.0
 */
public class WobbleImageOp extends AbstractTransformImageOp {

	private final double xRandom;
	private final double yRandom;
	private double xWavelength;
	private double yWavelength;
	private double xAmplitude;
	private double yAmplitude;
	private double xScale;
	private double yScale;

	public WobbleImageOp() {
		xWavelength = 15;
		yWavelength = 15;
		xAmplitude = 4.0;
		yAmplitude = 3.0;
		xScale = 1.0;
		yScale = 1.0;
		xRandom = 3 * Math.random();
		yRandom = 10 * Math.random();
	}

	public double getxWavelength() {
		return xWavelength;
	}

	public void setxWavelength(double xWavelength) {
		this.xWavelength = xWavelength;
	}

	public double getyWavelength() {
		return yWavelength;
	}

	public void setyWavelength(double yWavelength) {
		this.yWavelength = yWavelength;
	}

	public double getxAmplitude() {
		return xAmplitude;
	}

	public void setxAmplitude(double xAmplitude) {
		this.xAmplitude = xAmplitude;
	}

	public double getyAmplitude() {
		return yAmplitude;
	}

	public void setyAmplitude(double yAmplitude) {
		this.yAmplitude = yAmplitude;
	}

	public double getxScale() {
		return xScale;
	}

	public void setxScale(double xScale) {
		this.xScale = xScale;
	}

	public double getyScale() {
		return yScale;
	}

	public void setyScale(double yScale) {
		this.yScale = yScale;
	}

	@Override
	protected void transform(int x, int y, double[] t) {
		double tx = Math.cos((xScale * x + y) / xWavelength + xRandom);
		double ty = Math.sin((yScale * y + x) / yWavelength + yRandom);
		t[0] = x + xAmplitude * tx;
		t[1] = y + yAmplitude * ty;
	}
}
