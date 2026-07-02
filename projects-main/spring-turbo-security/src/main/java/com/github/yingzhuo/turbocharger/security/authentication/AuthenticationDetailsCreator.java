package com.github.yingzhuo.turbocharger.security.authentication;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

/**
 * @author 应卓
 * @see Authentication#getDetails()
 * @since 3.5.3
 */
@FunctionalInterface
public interface AuthenticationDetailsCreator {

	@Nullable
	public Object createDetails(HttpServletRequest request, HttpServletResponse response);

}
