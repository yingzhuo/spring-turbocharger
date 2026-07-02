package com.github.yingzhuo.turbocharger.captcha.google.filter.predefined;

import com.github.yingzhuo.turbocharger.captcha.google.filter.AbstractFilterFactory;
import com.github.yingzhuo.turbocharger.captcha.google.filter.lib.RippleImageOp;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0
 */
abstract class AbstractRippleFilterFactory extends AbstractFilterFactory {

	protected List<BufferedImageOp> filters;
	protected RippleImageOp ripple;

	public AbstractRippleFilterFactory() {
		ripple = new RippleImageOp();
	}

	protected List<BufferedImageOp> getPreRippleFilters() {
		return new ArrayList<>();
	}

	protected List<BufferedImageOp> getPostRippleFilters() {
		return new ArrayList<>();
	}

	@Override
	public List<BufferedImageOp> getFilters() {
		if (filters == null) {
			filters = new ArrayList<>();
			filters.addAll(getPreRippleFilters());
			filters.add(ripple);
			filters.addAll(getPostRippleFilters());
		}
		return filters;
	}

}
