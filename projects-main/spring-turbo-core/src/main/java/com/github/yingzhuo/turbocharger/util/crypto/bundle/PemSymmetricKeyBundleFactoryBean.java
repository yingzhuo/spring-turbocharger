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
package com.github.yingzhuo.turbocharger.util.crypto.bundle;

import com.github.yingzhuo.turbocharger.core.ResourceUtils;
import com.github.yingzhuo.turbocharger.util.crypto.pem.PemUtils;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import static com.github.yingzhuo.turbocharger.util.crypto.pem.PemUtils.readPkcs8Key;

/**
 * {@link SymmetricKeyBundle} 配置用 {@link FactoryBean} <br>
 * 用PEM格式文件配置
 *
 * @author 应卓
 * @see SymmetricKeyBundle
 * @see PemUtils
 * @since 3.3.1
 */
public class PemSymmetricKeyBundleFactoryBean implements FactoryBean<SymmetricKeyBundle>, InitializingBean {

	@Setter
	private String keyLocation;

	@Setter
	private String keyContent;

	@Setter
	private String keyPassword;

	@Nullable
	private SymmetricKeyBundle bundle;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SymmetricKeyBundle getObject() {
		Assert.notNull(bundle, "bundle is not initialized");
		return bundle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return SymmetricKeyBundle.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() {
		var key = readPkcs8Key(getKeyContent(), this.keyPassword);
		this.bundle = new SymmetricKeyBundleImpl(key);
	}

	private String getKeyContent() {
		if (StringUtils.hasText(this.keyContent)) {
			return this.keyContent;
		}

		Assert.notNull(this.keyLocation, "keyLocation is required");
		return ResourceUtils.readResourceAsString(keyLocation);
	}

}
