package com.github.yingzhuo.turbocharger.captcha.google.filter.predefined;

import com.github.yingzhuo.turbocharger.captcha.google.filter.AbstractFilterFactory;
import com.github.yingzhuo.turbocharger.captcha.google.filter.lib.DoubleRippleImageOp;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0
 */
public class DoubleRippleFilterFactory extends AbstractFilterFactory {

	protected List<BufferedImageOp> filters;
	protected DoubleRippleImageOp ripple;

	public DoubleRippleFilterFactory() {
		ripple = new DoubleRippleImageOp();
	}

	@Override
	public List<BufferedImageOp> getFilters() {
		if (filters == null) {
			filters = new ArrayList<>();
			filters.add(ripple);
		}

		return filters;
	}

}
