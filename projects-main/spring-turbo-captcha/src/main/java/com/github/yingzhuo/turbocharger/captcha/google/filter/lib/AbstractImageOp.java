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

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;

/**
 * @since 1.0.0
 */
public abstract class AbstractImageOp implements BufferedImageOp {

	public static final int EDGE_ZERO = 0;
	public static final int EDGE_MIRROR = 1;
	public static final int EDGE_CLAMP = 2;

	protected int edgeMode;
	protected RenderingHints hints;

	public int getEdgeMode() {
		return edgeMode;
	}

	public void setEdgeMode(int edgeMode) {
		this.edgeMode = edgeMode;
	}

	protected int getPixel(int pixels[], int x, int y, int width, int height, int edgeMode) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			return pixels[x + y * width];
		} else if (edgeMode == EDGE_ZERO) {
			return 0;
		} else if (edgeMode == EDGE_CLAMP) {
			if (x < 0) {
				x = 0;
			} else if (x >= width) {
				x = width - 1;
			}
			if (y < 0) {
				y = 0;
			} else if (y >= height) {
				y = height - 1;
			}
			return pixels[x + y * width];
		} else {
			if (x < 0) {
				x = -x;
			} else if (x >= width) {
				x = width + width - x - 1;
			}
			if (y < 0) {
				y = -y;
			} else if (y > height) {
				y = height = height - y - 1;
			}
			try {
				return pixels[x + y * width];
			} catch (Exception e) {
				return 0;
			}
		}
	}

	private int linear(int from, int to, int shift, double d) {
		return ((int) Math
			.floor(((from >> shift) & 0xff) + d * (((to >> shift) & 0xff) - ((from >> shift) & 0xff)))) << shift;
	}

	private int linear(int from, int to, double d) {
		int c = 0;
		for (int i = 0; i < 4; i++) {
			c += linear(from, to, i * 8, d);
		}
		return c;
	}

	protected int bilinear(int nw, int ne, int sw, int se, double xd, double yd) {
		return linear(linear(nw, ne, xd), linear(sw, se, xd), yd);
	}

	protected int getPixelBilinear(int pixels[], double x, double y, int width, int height, int edgeMode) {
		int xi = (int) Math.floor(x);
		int yi = (int) Math.floor(y);
		double xd = x - xi;
		double yd = y - yi;
		int nw = getPixel(pixels, xi, yi, width, height, edgeMode);
		int ne = getPixel(pixels, xi + 1, yi, width, height, edgeMode);
		int sw = getPixel(pixels, xi, yi + 1, width, height, edgeMode);
		int se = getPixel(pixels, xi + 1, yi + 1, width, height, edgeMode);
		return bilinear(nw, ne, sw, se, xd, yd);
	}

	protected int limit(int v, int min, int max) {
		if (v < min) {
			v = min;
		} else if (v > max) {
			v = max;
		}
		return v;
	}

	protected int limitByte(int v) {
		return limit(v, 0, 255);
	}

	protected void filter(int[] inPixels, int[] outPixels, int width, int height) {
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if (dest == null) {
			dest = createCompatibleDestImage(src, null);
		}
		int width = src.getWidth();
		int height = src.getHeight();
		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		src.getRaster().getDataElements(0, 0, width, height, inPixels);
		filter(inPixels, outPixels, width, height);
		dest.getRaster().setDataElements(0, 0, width, height, outPixels);
		return dest;
	}

	@Override
	public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
		if (destCM == null) {
			destCM = src.getColorModel();
			if (destCM instanceof IndexColorModel) {
				destCM = ColorModel.getRGBdefault();
			}
		}
		return new BufferedImage(destCM, destCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()),
			destCM.isAlphaPremultiplied(), null);
	}

	@Override
	public Rectangle2D getBounds2D(BufferedImage src) {
		return src.getRaster().getBounds();
	}

	@Override
	public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
		if (dstPt == null) {
			dstPt = new Point2D.Float();
		}
		dstPt.setLocation(srcPt.getX(), srcPt.getY());
		return dstPt;
	}

	@Override
	public RenderingHints getRenderingHints() {
		return hints;
	}

}
