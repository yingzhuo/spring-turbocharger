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
package com.github.yingzhuo.turbocharger.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.turbocharger.util.crypto.bundle.AsymmetricKeyBundle;
import com.github.yingzhuo.turbocharger.util.crypto.bundle.PemAsymmetricKeyBundleFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @since 3.5.0
 */
public abstract class AbstractPemAlgorithmFactoryBean implements FactoryBean<Algorithm>, InitializingBean {

	private final PemAsymmetricKeyBundleFactoryBean delegatingFactoryBean;

	@Nullable
	private AsymmetricKeyBundle bundle;

	/**
	 * 构造方法
	 */
	protected AbstractPemAlgorithmFactoryBean() {
		delegatingFactoryBean = new PemAsymmetricKeyBundleFactoryBean();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<?> getObjectType() {
		return Algorithm.class;
	}

	public void setCertificateLocation(String certificateLocation) {
		delegatingFactoryBean.setCertificateLocation(certificateLocation);
	}

	public void setCertificateContent(String certificateContent) {
		delegatingFactoryBean.setCertificateContent(certificateContent);
	}

	public void setPrivateKeyLocation(String privateKeyLocation) {
		delegatingFactoryBean.setPrivateKeyLocation(privateKeyLocation);
	}

	public void setPrivateKeyContent(String privateKeyContent) {
		delegatingFactoryBean.setPrivateKeyContent(privateKeyContent);
	}

	public void setPrivateKeyPassword(String privateKeyPassword) {
		delegatingFactoryBean.setPrivateKeyPassword(privateKeyPassword);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		delegatingFactoryBean.afterPropertiesSet();
		this.bundle = delegatingFactoryBean.getObject();
	}

	protected final AsymmetricKeyBundle getBundle() {
		Assert.notNull(bundle, "bundle not initialized");
		return bundle;
	}

}
