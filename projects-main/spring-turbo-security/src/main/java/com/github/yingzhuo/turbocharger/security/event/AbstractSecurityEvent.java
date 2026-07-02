package com.github.yingzhuo.turbocharger.security.event;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.context.ApplicationEvent;
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
