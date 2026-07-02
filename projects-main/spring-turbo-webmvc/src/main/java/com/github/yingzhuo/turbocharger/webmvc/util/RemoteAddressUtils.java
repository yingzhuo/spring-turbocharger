package com.github.yingzhuo.turbocharger.webmvc.util;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;

public final class RemoteAddressUtils {

	private RemoteAddressUtils() {
	}

	@Nullable
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");

			if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (String strIp : ips) {
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	public static String getRequiredIpAddress(HttpServletRequest request) {
		var ip = getIpAddress(request);
		return Objects.requireNonNull(ip);
	}

	@Nullable
	public static String getIpAddress(NativeWebRequest request) {
		final HttpServletRequest httpServletRequest = request.getNativeRequest(HttpServletRequest.class);
		return getIpAddress(httpServletRequest);
	}

	public static String getRequiredIpAddress(NativeWebRequest request) {
		var ip = getIpAddress(request);
		return Objects.requireNonNull(ip);
	}

}
