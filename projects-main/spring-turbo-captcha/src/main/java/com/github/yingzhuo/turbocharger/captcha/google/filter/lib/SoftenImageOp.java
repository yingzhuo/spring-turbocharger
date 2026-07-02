package com.github.yingzhuo.turbocharger.captcha.google.filter.lib;

public class SoftenImageOp extends AbstractConvolveImageOp {

	private static final float[][] matrix = {{0 / 16f, 1 / 16f, 0 / 16f}, {1 / 16f, 12 / 16f, 1 / 16f},
		{0 / 16f, 1 / 16f, 0 / 16f}};

	public SoftenImageOp() {
		super(matrix);
	}
}
