package com.github.yingzhuo.turbocharger.security.authentication;

import com.github.yingzhuo.turbocharger.security.token.Token;
import org.jspecify.annotations.Nullable;
import org.springframework.core.style.ToStringCreator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

public class GenericAuthentication implements Authentication, Serializable, Principal {

	private final UserDetails userDetails;
	private final @Nullable Token token;
	private boolean authenticated = true;
	private @Nullable Object details;

	public GenericAuthentication(UserDetails userDetails) {
		this(userDetails, null, null);
	}

	public GenericAuthentication(UserDetails userDetails, @Nullable Token token) {
		this(userDetails, token, null);
	}

	public GenericAuthentication(UserDetails userDetails, @Nullable Token token, @Nullable Object details) {
		Assert.notNull(userDetails, "userDetails must not be null");
		this.userDetails = userDetails;
		this.token = token;
		this.details = details;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userDetails.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return token != null ? token.asString() : String.valueOf(System.identityHashCode(this));
	}

	@Nullable
	@Override
	public Object getDetails() {
		return this.details;
	}

	public void setDetails(@Nullable Object details) {
		this.details = details;
	}

	@Override
	public Object getPrincipal() {
		return this.userDetails;
	}

	@Override
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}

	@Override
	public String getName() {
		return userDetails.getUsername();
	}

	@Override
	public String toString() {
		var creator = new ToStringCreator(this);
		creator.append("Authentication").append(" [");
		creator.append("Principal=").append(getPrincipal()).append(", ");
		creator.append("Credentials=[PROTECTED], ");
		creator.append("Authenticated=").append(isAuthenticated()).append(", ");
		creator.append("Granted Authorities=").append(getAuthorities());
		creator.append("]");
		return creator.toString();
	}

}
