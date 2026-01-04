/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.security.event;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author 应卓
 * @since 3.5.3
 */
public abstract class AbstractSecurityEvent extends ApplicationEvent {

	/**
	 * 构造方法
	 *
	 * @param request 请求
	 */
	public AbstractSecurityEvent(HttpServletRequest request) {
		this(request, null);
	}

	/**
	 * 构造方法
	 *
	 * @param request  请求
	 * @param response 应答
	 */
	public AbstractSecurityEvent(HttpServletRequest request, @Nullable HttpServletResponse response) {
		super(new ServletWebRequest(request, response));
	}

	public ServletWebRequest getWebRequest() {
		return (ServletWebRequest) getSource();
	}

}
