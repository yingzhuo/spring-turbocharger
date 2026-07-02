package com.github.yingzhuo.turbocharger.security.event;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.request.ServletWebRequest;

public abstract class AbstractSecurityEvent extends ApplicationEvent {

	public AbstractSecurityEvent(HttpServletRequest request) {
		this(request, null);
	}

	public AbstractSecurityEvent(HttpServletRequest request, @Nullable HttpServletResponse response) {
		super(new ServletWebRequest(request, response));
	}

	public ServletWebRequest getWebRequest() {
		return (ServletWebRequest) getSource();
	}

}
