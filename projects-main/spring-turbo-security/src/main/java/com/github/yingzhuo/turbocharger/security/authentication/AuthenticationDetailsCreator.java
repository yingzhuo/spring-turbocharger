package com.github.yingzhuo.turbocharger.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface AuthenticationDetailsCreator {

	@Nullable
	public Object createDetails(HttpServletRequest request, HttpServletResponse response);

}
