/*
 * Copyright 2022-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.turbocharger.webmvc.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * Servlet相关工具
 *
 * @author 应卓
 * @since 1.0.0
 */
public final class ServletUtils {

	/**
	 * 私有构造方法
	 */
	private ServletUtils() {
	}

	@Nullable
	public static HttpServletRequest getRequest() {
		try {
			var attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			return attributes.getRequest();
		} catch (Exception e) {
			return null;
		}
	}

	public static HttpServletRequest getRequiredRequest() {
		var request = getRequest();
		return Objects.requireNonNull(request);
	}

	public static HttpServletRequest getUnwrappedRequest() {
		var request = getRequiredRequest();
		while (request instanceof HttpServletRequestWrapper wrapper) {
			request = (HttpServletRequest) (wrapper).getRequest();
		}
		return request;
	}

	@Nullable
	public static HttpServletResponse getResponse() {
		try {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
			return attributes.getResponse();
		} catch (Exception e) {
			return null;
		}
	}

	public static HttpServletResponse getRequiredResponse() {
		final HttpServletResponse response = getResponse();
		return Objects.requireNonNull(response);
	}

	public static HttpServletResponse getUnwrappedResponse() {
		var response = getRequiredResponse();
		while (response instanceof HttpServletResponseWrapper wrapper) {
			response = (HttpServletResponse) (wrapper).getResponse();
		}
		return response;
	}

	public static HttpSession getSession() {
		return getSession(true);
	}

	public static HttpSession getSession(boolean create) {
		return getRequiredRequest().getSession(create);
	}

	public static String getSessionId() {
		return getSession().getId();
	}

	public static ServletContext getServletContext() {
		return getRequiredRequest().getServletContext();
	}

}
