package com.github.yingzhuo.turbocharger.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class DefaultAuthenticationDetailsCreator implements AuthenticationDetailsCreator {

	@Override
	public Object createDetails(HttpServletRequest request, HttpServletResponse response) {
		return new WebAuthenticationDetailsSource().buildDetails(request);
	}

}
