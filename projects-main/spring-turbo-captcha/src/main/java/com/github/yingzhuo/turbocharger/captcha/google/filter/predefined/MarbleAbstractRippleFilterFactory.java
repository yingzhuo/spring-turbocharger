package com.github.yingzhuo.turbocharger.captcha.google.filter.predefined;

import com.github.yingzhuo.turbocharger.captcha.google.filter.lib.MarbleImageOp;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0
 */
public class MarbleAbstractRippleFilterFactory extends AbstractRippleFilterFactory {

	protected MarbleImageOp marble = new MarbleImageOp();

	@Override
	protected List<BufferedImageOp> getPreRippleFilters() {
		List<BufferedImageOp> list = new ArrayList<>();
		list.add(marble);
		return list;
	}

}
