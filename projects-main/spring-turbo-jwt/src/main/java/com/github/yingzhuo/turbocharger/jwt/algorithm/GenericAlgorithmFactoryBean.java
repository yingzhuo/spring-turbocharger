package com.github.yingzhuo.turbocharger.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class GenericAlgorithmFactoryBean implements FactoryBean<GenericAlgorithm>, ResourceLoaderAware {

	private @Nullable ResourceLoader resourceLoader;
	private @Nullable String pemLocation;
	private @Nullable String password;
	private Charset pemCharset = StandardCharsets.UTF_8;

	public GenericAlgorithmFactoryBean() {
		super();
	}

	@Override
	public GenericAlgorithm getObject() throws Exception {
		Assert.notNull(resourceLoader, "resourceLoader must not be null");
		Assert.hasText(pemLocation, "pemLocation must not be blank");
		Assert.notNull(pemCharset, "pemCharset must not be null");

		var resource = resourceLoader.getResource(pemLocation);
		return new GenericAlgorithm(resource.getContentAsString(pemCharset), password);
	}

	@Override
	public Class<?> getObjectType() {
		return Algorithm.class;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void setPemLocation(String pemLocation) {
		this.pemLocation = pemLocation;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPemCharset(Charset pemCharset) {
		this.pemCharset = pemCharset;
	}
}
