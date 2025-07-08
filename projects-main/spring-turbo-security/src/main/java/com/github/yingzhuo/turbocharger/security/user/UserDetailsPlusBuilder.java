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
package com.github.yingzhuo.turbocharger.security.user;

import com.github.yingzhuo.turbocharger.security.util.AuthorityUtils;
import com.github.yingzhuo.turbocharger.util.collection.Attributes;
import com.github.yingzhuo.turbocharger.util.time.LocalDateUtils;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

/**
 * {@link UserDetailsPlus} 创建器
 *
 * @author 应卓
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see UserDetailsPlus#builder()
 * @see UserDetailsPlus
 * @since 1.0.0
 */
@Deprecated(since = "3.5.3")
public final class UserDetailsPlusBuilder {

	private static final String DEFAULT_PASSWORD = "<NO PASSWORD>";

	private final User.UserBuilder userBuilder = User.builder();
	private Attributes attributes = Attributes.newInstance();
	private boolean passwordFlag = false; // 是否主动设置了密码

	@Nullable
	private Object id;

	@Nullable
	private Object avatar;

	@Nullable
	private Object pronouns;

	@Nullable
	private Object nativeUser;

	@Nullable
	private String email;

	@Nullable
	private String phoneNumber;

	@Nullable
	private LocalDate dateOfBirth;

	@Nullable
	private String biography;

	@Nullable
	private String nationality;

	@Nullable
	private String location;

	@Nullable
	private String url;

	/**
	 * 构造方法
	 */
	UserDetailsPlusBuilder() {
	}

	public UserDetailsPlusBuilder username(String username) {
		userBuilder.username(username);
		return this;
	}

	public UserDetailsPlusBuilder password(String password) {
		passwordFlag = true;
		userBuilder.password(password);
		return this;
	}

	public UserDetailsPlusBuilder passwordEncoder(Function<String, String> encoder) {
		userBuilder.passwordEncoder(encoder);
		return this;
	}

	public UserDetailsPlusBuilder roles(String... roles) {
		userBuilder.roles(roles);
		return this;
	}

	public UserDetailsPlusBuilder authorities(GrantedAuthority... authorities) {
		userBuilder.authorities(authorities);
		return this;
	}

	public UserDetailsPlusBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
		userBuilder.authorities(authorities);
		return this;
	}

	public UserDetailsPlusBuilder authorities(String... authorities) {
		userBuilder.authorities(authorities);
		return this;
	}

	public UserDetailsPlusBuilder authoritiesWithCommaSeparatedString(String string) {
		return authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(string));
	}

	public UserDetailsPlusBuilder accountExpired(boolean accountExpired) {
		userBuilder.accountExpired(accountExpired);
		return this;
	}

	public UserDetailsPlusBuilder accountLocked(boolean accountLocked) {
		userBuilder.accountLocked(accountLocked);
		return this;
	}

	public UserDetailsPlusBuilder credentialsExpired(boolean credentialsExpired) {
		userBuilder.credentialsExpired(credentialsExpired);
		return this;
	}

	public UserDetailsPlusBuilder disabled(boolean disabled) {
		userBuilder.disabled(disabled);
		return this;
	}

	public UserDetailsPlusBuilder id(Object id) {
		this.id = id;
		return this;
	}

	public UserDetailsPlusBuilder avatar(Object avatar) {
		this.avatar = avatar;
		return this;
	}

	public UserDetailsPlusBuilder pronouns(Object pronouns) {
		this.pronouns = pronouns;
		return this;
	}

	public UserDetailsPlusBuilder nativeUser(Object u) {
		this.nativeUser = u;
		return this;
	}

	public UserDetailsPlusBuilder email(String email) {
		this.email = email;
		return this;
	}

	public UserDetailsPlusBuilder phoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public UserDetailsPlusBuilder dateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public UserDetailsPlusBuilder dateOfBirth(Date dateOfBirth) {
		return dateOfBirth(LocalDateUtils.toLocalDate(dateOfBirth));
	}

	public UserDetailsPlusBuilder dateOfBirth(String dateOfBirth) {
		return dateOfBirth(LocalDate.parse(dateOfBirth));
	}

	public UserDetailsPlusBuilder biography(String bioInfo) {
		this.biography = bioInfo;
		return this;
	}

	public UserDetailsPlusBuilder nationality(String nationality) {
		this.nationality = nationality;
		return this;
	}

	public UserDetailsPlusBuilder location(String location) {
		this.location = location;
		return this;
	}

	public UserDetailsPlusBuilder url(String url) {
		this.url = url;
		return this;
	}

	public UserDetailsPlusBuilder putAttribute(String key, Object value) {
		this.attributes.add(key, value);
		return this;
	}

	public UserDetailsPlusBuilder setAttributes(@Nullable Attributes attributes) {
		this.attributes = Optional.ofNullable(attributes).orElseGet(Attributes::new);
		return this;
	}

	public UserDetailsPlus build() {
		if (!passwordFlag) {
			userBuilder.password(DEFAULT_PASSWORD);
		}

		return new UserDetailsPlusImpl(userBuilder.build(), this.id, this.avatar, this.pronouns, this.nativeUser,
			this.email, this.phoneNumber, this.dateOfBirth, this.biography, this.nationality, this.location,
			this.url, this.attributes);
	}

}
