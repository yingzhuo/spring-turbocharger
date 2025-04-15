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
package com.github.yingzhuo.turbocharger.security.util;

import com.github.yingzhuo.turbocharger.core.SpringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * {@link PasswordEncoder} 相关工具
 *
 * @author 应卓
 * @since 2.0.1
 */
public final class PasswordEncoderUtils {

	/**
	 * 私有构造方法
	 */
	private PasswordEncoderUtils() {
		super();
	}

	public String encode(CharSequence rawPwd) {
		return SpringUtils.getRequiredBean(PasswordEncoder.class).encode(rawPwd);
	}

	public boolean matches(CharSequence rawPwd, String encodedPwd) {
		return SpringUtils.getRequiredBean(PasswordEncoder.class).matches(rawPwd, encodedPwd);
	}

}
