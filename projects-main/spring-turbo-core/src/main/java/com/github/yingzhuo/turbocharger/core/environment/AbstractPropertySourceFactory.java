package com.github.yingzhuo.turbocharger.core.environment;

import com.github.yingzhuo.turbocharger.util.StringUtils;
import com.github.yingzhuo.turbocharger.util.id.UUIDs;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;

/**
 * (内部使用)
 *
 * @author 应卓
 * @since 2.1.3
 */
public abstract class AbstractPropertySourceFactory implements PropertySourceFactory {

	private final PropertySourceLoader loader;

	public AbstractPropertySourceFactory(PropertySourceLoader loader) {
		this.loader = loader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource)
		throws IOException {
		final var propertySourceName = enforcePropertySourceName(name, resource);
		final var list = loader.load(propertySourceName, resource.getResource());

		if (list.isEmpty()) {
			return new EmptyPropertySource(propertySourceName);
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			throw new IOException("multiple document is NOT supported yet.");
		}
	}

	private String enforcePropertySourceName(@Nullable String name, EncodedResource resource) {
		if (name == null) {
			name = resource.getResource().getFilename();
		}

		if (StringUtils.isBlank(name)) {
			return UUIDs.classic32();
		}
		return name;
	}

}
