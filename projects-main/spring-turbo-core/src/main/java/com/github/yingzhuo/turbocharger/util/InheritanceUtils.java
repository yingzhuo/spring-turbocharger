package com.github.yingzhuo.turbocharger.util;

import org.springframework.util.Assert;

public final class InheritanceUtils {

	private InheritanceUtils() {
	}

	public static int distance(Class<?> child, Class<?> parent) {
		Assert.notNull(child, "child is required");
		Assert.notNull(parent, "parent is required");

		if (child.equals(parent)) {
			return 0;
		}

		final Class<?> cParent = child.getSuperclass();
		int d = parent.equals(cParent) ? 1 : 0;

		if (d == 1) {
			return d;
		}
		d += distance(cParent, parent);
		return d > 0 ? d + 1 : -1;
	}

}
