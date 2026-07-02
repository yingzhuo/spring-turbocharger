package com.github.yingzhuo.turbocharger.security.filter;

import com.github.yingzhuo.turbocharger.security.authentication.GenericAuthentication;
import com.github.yingzhuo.turbocharger.security.authentication.TokenToUserConverter;
import com.github.yingzhuo.turbocharger.security.event.AuthenticationFailureEvent;
import com.github.yingzhuo.turbocharger.security.event.AuthenticationSuccessEvent;
import com.github.yingzhuo.turbocharger.security.token.resolver.BearerTokenResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationFilter {

	private TokenToUserConverter tokenToUserConverter = token -> null;

	public TokenAuthenticationFilter() {
		super.setTokenResolver(new BearerTokenResolver());
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		// 如果之前的过滤器已经完成认证了
		// 则本过滤不工作
		if (!authenticationIsRequired()) {
			filterChain.doFilter(request, response);
			return;
		}

		try {

			// 尝试解析令牌
			var token = tokenResolver.resolve(new ServletWebRequest(request)).orElse(null);

			if (token == null) {
				filterChain.doFilter(request, response);
				return;
			}

			// 检查黑名单
			if (this.tokenBlacklistManager != null) {
				this.tokenBlacklistManager.verify(token);
			}

			// 尝试获取用户对象
			final UserDetails user = tokenToUserConverter.convert(token);
			if (user == null) {
				filterChain.doFilter(request, response);
				return;
			}

			// 构建认证对象
			var authentication = new GenericAuthentication(
				user,
				token,
				authenticationDetailsCreator == null ? null : authenticationDetailsCreator.createDetails(request, response)
			);
			authentication.setAuthenticated(true);

			// 设置认证上下文
			var securityContext = securityContextHolderStrategy.getContext();
			securityContext.setAuthentication(authentication);

			// 发布认证成功通知 (可选)
			if (this.applicationEventPublisher != null) {
				var event = new AuthenticationSuccessEvent(request, response, authentication.getAuthorities());
				this.applicationEventPublisher.publishEvent(event);
			}

		} catch (AuthenticationException e) {

			securityContextHolderStrategy.clearContext();

			// 发布认证失败通知 (可选)
			if (this.applicationEventPublisher != null) {
				var event = new AuthenticationFailureEvent(request, response, e);
				this.applicationEventPublisher.publishEvent(event);
			}

			if (authenticationEntryPoint != null) {
				authenticationEntryPoint.commence(request, response, e);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	public void setTokenToUserConverter(TokenToUserConverter converter) {
		this.tokenToUserConverter = converter;
	}

}
