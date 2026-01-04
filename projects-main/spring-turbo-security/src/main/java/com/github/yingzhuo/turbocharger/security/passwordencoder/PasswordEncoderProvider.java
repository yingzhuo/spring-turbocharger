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
package com.github.yingzhuo.turbocharger.security.passwordencoder;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 应卓
 * @see PasswordEncoderFactoryBean
 * @see org.springframework.security.crypto.password.DelegatingPasswordEncoder
 * @see com.github.yingzhuo.turbocharger.util.spi.SPILoader
 * @since 3.5.3
 */
public interface PasswordEncoderProvider {

	/**
	 * 获取ID
	 *
	 * @return ID
	 */
	public @NonNull String getId();

	/**
	 * 获取{@link PasswordEncoder} 实例
	 *
	 * @return {@link PasswordEncoder} 实例
	 */
	public @NonNull PasswordEncoder getEncoder();

}
