package com.github.yingzhuo.turbocharger.core.io;

import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;

public abstract class DelegatingResource extends AbstractResource {

	private final Resource delegatingResource;

	protected DelegatingResource(Resource delegatingResource) {
		Assert.notNull(delegatingResource, "delegatingResource may not be null");
		this.delegatingResource = delegatingResource;
	}

	@Override
	public String getDescription() {
		return delegatingResource.getDescription();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return delegatingResource.getInputStream();
	}

	public Resource getDelegatingResource() {
		return this.delegatingResource;
	}

}
