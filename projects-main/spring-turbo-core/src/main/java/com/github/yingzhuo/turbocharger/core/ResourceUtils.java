package com.github.yingzhuo.turbocharger.core;

import com.github.yingzhuo.turbocharger.util.io.CloseUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

import static java.io.File.separator;

@SuppressWarnings("unchecked")
public final class ResourceUtils {

	private static final ResourceLoader RESOURCE_LOADER
		= ApplicationResourceLoader.get(ClassUtils.getDefaultClassLoader());

	private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER
		= ResourcePatternUtils.getResourcePatternResolver(RESOURCE_LOADER);

	private ResourceUtils() {
		super();
	}

	public static ResourceLoader getResourceLoader() {
		return RESOURCE_LOADER;
	}

	public static ResourcePatternResolver getResourcePatternResolver() {
		return RESOURCE_PATTERN_RESOLVER;
	}

	public static <T extends Resource> T loadResource(String location) {
		Assert.hasText(location, "location is required");
		return (T) RESOURCE_LOADER.getResource(location);
	}

	public static InputStream loadResourceAsInputStream(String location) {
		return loadResourceAsInputStream(location, -1);
	}

	public static InputStream loadResourceAsInputStream(String location, int bufferSize) {
		try {
			var in = loadResource(location).getInputStream();
			return bufferSize > 0 ? new BufferedInputStream(in, bufferSize) : in;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static List<Resource> loadResources(String locationPattern) {
		try {
			return Arrays.asList(RESOURCE_PATTERN_RESOLVER.getResources(locationPattern));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static String readResourceAsString(String location) {
		return readResourceAsString(location, (Charset) null);
	}

	public static String readResourceAsString(String location, @Nullable Charset charset) {
		try {
			charset = Objects.requireNonNullElse(charset, StandardCharsets.UTF_8);
			return loadResource(location).getContentAsString(charset);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static String readResourceAsString(String location, @Nullable String charset) {
		var cs = Optional.ofNullable(charset)
			.map(Charset::forName)
			.orElse(StandardCharsets.UTF_8);

		return readResourceAsString(location, cs);
	}

	public static Stream<String> readResourceAsLines(String location) {
		return readResourceAsString(location).lines();
	}

	public static Stream<String> readResourceAsLines(String location, @Nullable Charset charset) {
		return readResourceAsString(location, charset).lines();
	}

	public static Stream<String> readResourceAsLines(String location, @Nullable String charset) {
		return readResourceAsString(location, charset).lines();
	}

	public static Properties loadResourceAsProperties(String location, boolean xmlFormat) {
		Assert.notNull(location, "resource location must not be null");
		try (var inputStream = loadResourceAsInputStream(location)) {
			var properties = new Properties();
			if (xmlFormat) {
				properties.loadFromXML(inputStream);
			} else {
				properties.load(inputStream);
			}
			return properties;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static byte[] readResourceAsBytes(String location) {
		try {
			var resource = loadResource(location);
			return resource.getContentAsByteArray();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void close(@Nullable Resource resource) {
		if (resource == null) {
			return;
		}

		if (resource.isOpen()) {
			try {
				CloseUtils.closeQuietly(resource.getInputStream());
			} catch (IOException ignored) {
				// noop
			}
		}

		if (resource instanceof WritableResource wr) {
			try {
				CloseUtils.closeQuietly(wr.getOutputStream());
			} catch (IOException ignored) {
				// noop
			}
		}
	}

	public static String getResourceLocation(Class<?> fromPackage, String filename) {
		Assert.notNull(fromPackage, "fromPackage must not be null");
		Assert.hasText(filename, "filename must not be empty");
		return new StringBuilder("classpath:")
			.append(fromPackage.getPackage().getName().replaceAll("\\.", separator))
			.append(separator)
			.append(filename)
			.toString();
	}

}
