package com.github.yingzhuo.turbocharger.util.collection;

import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class Attributes extends LinkedMultiValueMap<String, Object> implements MultiValueMap<String, Object> {

	public Attributes() {
		super();
	}

	public Attributes(@Nullable Map<String, List<Object>> map) {
		if (map != null && !map.isEmpty()) {
			super.putAll(map);
		}
	}

	public Attributes(@Nullable Attributes anotherAttributes) {
		this((Map<String, List<Object>>) anotherAttributes);
	}

	public static Attributes newInstance() {
		return new Attributes();
	}

	public static Attributes fromMap(@Nullable Map<String, Object> map) {
		final Attributes attributes = new Attributes();
		if (!CollectionUtils.isEmpty(map)) {
			for (String key : map.keySet()) {
				Object value = map.get(key);
				attributes.add(key, value);
			}
		}
		return attributes;
	}

	public static Attributes fromListMap(@Nullable Map<String, List<Object>> map) {
		return new Attributes(map);
	}

	public static Attributes fromAttributes(@Nullable Attributes attributes) {
		return new Attributes(attributes);
	}

	public static Attributes fromMultiValueMap(@Nullable MultiValueMap<String, Object> map) {
		final Attributes attributes = new Attributes();
		Optional.ofNullable(map).ifPresent(attributes::addAll);
		return attributes;
	}

	@Nullable
	public <T> T findFirst(String key) {
		Assert.notNull(key, "key is required");
		return (T) super.getFirst(key);
	}

	@Nullable
	public <T> T findFirstOrDefault(String key, @Nullable T defaultIfNull) {
		Assert.notNull(key, "key is required");
		return Optional.<T>ofNullable(findFirst(key)).orElse(defaultIfNull);
	}

	@Nullable
	public <T> T findFirstOrDefault(String key, Supplier<T> defaultIfNull) {
		Assert.notNull(key, "key is required");
		return Optional.<T>ofNullable(findFirst(key)).orElseGet(defaultIfNull);
	}

	public <T> T findRequiredFirst(String key) {
		return findRequiredFirst(key,
			() -> new NoSuchElementException(StringFormatter.format("element not found. key: {}", key)));
	}

	public <T> T findRequiredFirst(String key,
								   Supplier<? extends RuntimeException> exceptionIfKeyNotFound) {
		Assert.notNull(key, "key is required");
		Assert.notNull(exceptionIfKeyNotFound, "exceptionIfKeyNotFound is required");

		T obj = findFirst(key);
		if (obj == null) {
			throw exceptionIfKeyNotFound.get();
		}
		return obj;
	}

}
