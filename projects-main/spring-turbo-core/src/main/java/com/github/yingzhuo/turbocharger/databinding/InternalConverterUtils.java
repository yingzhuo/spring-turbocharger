package com.github.yingzhuo.turbocharger.databinding;

import com.github.yingzhuo.turbocharger.exception.DataBindingException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.util.StringUtils;

final class InternalConverterUtils {

	public static <T extends RuntimeException> RuntimeException transform(T e) {

		if ((e instanceof MessageSourceResolvable) || (e instanceof MultiMessageSourceResolvable)) {
			return e;
		}

		var msg = e.getMessage();
		if (!StringUtils.hasText(msg)) {
			var rootEx = NestedExceptionUtils.getRootCause(e);
			if (rootEx != null) {
				msg = rootEx.getMessage();
			}
		}

		if (StringUtils.hasText(msg)) {
			return DataBindingException.of(msg);
		}

		return e;
	}

}
