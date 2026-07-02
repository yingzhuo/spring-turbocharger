package com.github.yingzhuo.turbocharger.captcha.google.filter.predefined;

import com.github.yingzhuo.turbocharger.captcha.google.filter.lib.DiffuseImageOp;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0
 */
public class DiffuseAbstractRippleFilterFactory extends AbstractRippleFilterFactory {

	protected DiffuseImageOp diffuse = new DiffuseImageOp();

	@Override
	protected List<BufferedImageOp> getPreRippleFilters() {
		List<BufferedImageOp> list = new ArrayList<>();
		list.add(diffuse);
		return list;
	}

}
