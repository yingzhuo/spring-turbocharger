package com.github.yingzhuo.turbocharger.util.reflection;

import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.springframework.util.Assert;

import java.util.function.Supplier;

public final class InstantiationExceptionSupplier implements Supplier<InstantiationException> {

	private final Class<?> type;

	public InstantiationExceptionSupplier(Class<?> type) {
		Assert.notNull(type, "type is required");
		this.type = type;
	}

	@Override
	public InstantiationException get() {
		return new InstantiationException(
			StringFormatter.format("not able to create instance. type: '{}'", type.getName()));
	}

}
