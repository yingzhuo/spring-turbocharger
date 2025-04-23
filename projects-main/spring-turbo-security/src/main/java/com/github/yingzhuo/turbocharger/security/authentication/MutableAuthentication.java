/*
 *
 * Copyright 2022-present the original author or authors.
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
 *
 */
package com.github.yingzhuo.turbocharger.security.authentication;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

/**
 * @author 应卓
 * @since 3.1.1
 */
public interface MutableAuthentication extends Authentication {

	@NonNull
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities();

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities);

	@Nullable
	@Override
	public Object getCredentials();

	public void setCredentials(Object credentials);

	@Nullable
	@Override
	public Object getDetails();

	public void setDetails(Object details);

	@Nullable
	@Override
	public Object getPrincipal();

	public void setPrincipal(Object principal);

	@Override
	public boolean isAuthenticated();

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;

	@Override
	public default String getName() {
		if (this.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) this.getPrincipal()).getUsername();
		}
		if (this.getPrincipal() instanceof AuthenticatedPrincipal) {
			return ((AuthenticatedPrincipal) this.getPrincipal()).getName();
		}
		if (this.getPrincipal() instanceof Principal) {
			return ((Principal) this.getPrincipal()).getName();
		}
		return (this.getPrincipal() == null) ? EMPTY : this.getPrincipal().toString();
	}

}
