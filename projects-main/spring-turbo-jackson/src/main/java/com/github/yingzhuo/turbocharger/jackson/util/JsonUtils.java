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
package com.github.yingzhuo.turbocharger.jackson.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.yingzhuo.turbocharger.core.SpringUtils;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UncheckedIOException;

/**
 * Json相关工具
 *
 * @author 应卓
 * @see <a href="https://github.com/FasterXML/jackson">Jackson官方文档</a>
 * @see <a href="https://github.com/json-path/JsonPath">JsonPath官方文档</a>
 * @see ObjectMapper
 * @see JsonMapper
 * @since 3.3.1
 */
public final class JsonUtils {

	/**
	 * 私有构造方法
	 */
	private JsonUtils() {
		super();
	}

	/**
	 * 序列化
	 *
	 * @param obj 要序列化的对象
	 * @return json
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static String toJson(Object obj) {
		try {
			return getObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 序列化
	 * <ul>
	 *     <li>不要缩进</li>
	 * </ul>
	 *
	 * @param obj 要序列化的对象
	 * @return json
	 * @throws java.io.UncheckedIOException 处理失败
	 * @see com.fasterxml.jackson.databind.SerializationFeature#INDENT_OUTPUT
	 */
	public static String toJsonWithoutIndent(Object obj) {
		try {
			return getObjectMapper()
				.writer()
				.withoutFeatures(SerializationFeature.INDENT_OUTPUT)
				.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 序列化
	 * <ul>
	 *     <li>缩进</li>
	 * </ul>
	 *
	 * @param obj 要序列化的对象
	 * @return json
	 * @throws java.io.UncheckedIOException 处理失败
	 * @see com.fasterxml.jackson.databind.SerializationFeature#INDENT_OUTPUT
	 */
	public static String toJsonWithIndent(Object obj) {
		try {
			return getObjectMapper()
				.writer()
				.withFeatures(SerializationFeature.INDENT_OUTPUT)
				.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 序列化
	 * <ul>
	 *     <li>指定视图</li>
	 * </ul>
	 *
	 * @param obj       要序列化的对象
	 * @param viewClass 要混入的视图类
	 * @return json
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static String toJsonWithView(Object obj, Class<?> viewClass) {
		try {
			return getObjectMapper()
				.writer()
				.withView(viewClass)
				.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 序列化
	 * <ul>
	 *     <li>指定视图</li>
	 *     <li>不要缩进</li>
	 * </ul>
	 *
	 * @param obj       要序列化的对象
	 * @param viewClass 要混入的视图类
	 * @return json
	 * @throws java.io.UncheckedIOException 处理失败
	 * @see com.fasterxml.jackson.databind.SerializationFeature#INDENT_OUTPUT
	 */
	public static String toJsonWithViewWithoutIndent(Object obj, Class<?> viewClass) {
		try {
			return getObjectMapper()
				.writer()
				.withView(viewClass)
				.withoutFeatures(SerializationFeature.INDENT_OUTPUT)
				.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 序列化
	 * <ul>
	 *     <li>指定视图</li>
	 *     <li>缩进</li>
	 * </ul>
	 *
	 * @param obj       要序列化的对象
	 * @param viewClass 要混入的视图类
	 * @return json
	 * @throws java.io.UncheckedIOException 处理失败
	 * @see com.fasterxml.jackson.databind.SerializationFeature#INDENT_OUTPUT
	 */
	public static String toJsonWithViewWithIndent(Object obj, Class<?> viewClass) {
		try {
			return getObjectMapper()
				.writer()
				.withView(viewClass)
				.withFeatures(SerializationFeature.INDENT_OUTPUT)
				.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 反序列化
	 *
	 * @param json     json
	 * @param objClass 要转换的类型
	 * @param <T>      要转换的类型泛型
	 * @return 实例
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static <T> T parseJson(String json, Class<T> objClass) {
		try {
			return getObjectMapper().readValue(json, objClass);
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 反序列化
	 *
	 * @param json     json
	 * @param jsonPath json path
	 * @param objClass 要转换的类型
	 * @param <T>      要转换的类型泛型
	 * @return 实例
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static <T> T parseJson(String json, String jsonPath, Class<T> objClass) {
		return parseJson(json, jsonPath, SimpleTypeRef.of(objClass));
	}

	/**
	 * 反序列化
	 *
	 * @param json     json
	 * @param jsonPath json path
	 * @param typeRef  要转换的类型
	 * @param <T>      要转换的类型泛型
	 * @return 实例
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static <T> T parseJson(String json, String jsonPath, TypeRef<T> typeRef) {
		return JsonPath.using(getJsonPathConf())
			.parse(json)
			.read(jsonPath, typeRef);
	}

	/**
	 * 反序列化
	 *
	 * @param json     json
	 * @param objClass 要转换的类型
	 * @param <T>      要转换的类型泛型
	 * @return 实例
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static <T> T parseJson(InputStream json, Class<T> objClass) {
		try {
			return getObjectMapper().readValue(json, objClass);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 反序列化
	 *
	 * @param json     json
	 * @param jsonPath json path
	 * @param objClass 要转换的类型
	 * @param <T>      要转换的类型泛型
	 * @return 实例
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static <T> T parseJson(InputStream json, String jsonPath, Class<T> objClass) {
		return parseJson(json, jsonPath, SimpleTypeRef.of(objClass));
	}

	/**
	 * 反序列化
	 *
	 * @param json     json
	 * @param jsonPath json path
	 * @param typeRef  要转换的类型
	 * @param <T>      要转换的类型泛型
	 * @return 实例
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static <T> T parseJson(InputStream json, String jsonPath, TypeRef<T> typeRef) {
		return JsonPath.using(getJsonPathConf())
			.parse(json)
			.read(jsonPath, typeRef);
	}

	/**
	 * 反序列化
	 *
	 * @param json     json
	 * @param objClass 要转换的类型
	 * @param <T>      要转换的类型泛型
	 * @return 实例
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static <T> T parseJson(Reader json, Class<T> objClass) {
		try {
			return getObjectMapper().readValue(json, objClass);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 反序列化
	 *
	 * @param json     json
	 * @param jsonPath json path
	 * @param objClass 要转换的类型
	 * @param <T>      要转换的类型泛型
	 * @return 实例
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static <T> T parseJson(Reader json, String jsonPath, Class<T> objClass) {
		return parseJson(json, jsonPath, SimpleTypeRef.of(objClass));
	}

	/**
	 * 反序列化
	 *
	 * @param json     json
	 * @param jsonPath json path
	 * @param typeRef  要转换的类型
	 * @param <T>      要转换的类型泛型
	 * @return 实例
	 * @throws java.io.UncheckedIOException 处理失败
	 */
	public static <T> T parseJson(Reader json, String jsonPath, TypeRef<T> typeRef) {
		return JsonPath.using(getJsonPathConf())
			.parse(json)
			.read(jsonPath, typeRef);
	}

	/**
	 * 获取 {@link ObjectMapper} 实例
	 *
	 * @return {@link ObjectMapper} 实例
	 */
	public static ObjectMapper getObjectMapper() {
		return SpringUtils.getBean(ObjectMapper.class)
			.orElseGet(SyncAvoid::getJsonMapper);
	}

	/**
	 * 获取 {@link Configuration} 实例
	 *
	 * @return {@link Configuration} 实例
	 */
	public static Configuration getJsonPathConf() {
		return SpringUtils.getBean(Configuration.class)
			.orElseGet(SyncAvoid::getJsonPathConf);
	}

	// -----------------------------------------------------------------------------------------------------------------

	// 延迟加载
	@SuppressWarnings("deprecation")
	private static class SyncAvoid {

		private static final JsonMapper JSON_MAPPER;
		private static final Configuration JSON_PATH_CONF;

		static {
			// 以下都是作者喜欢的配置，你如果不喜欢，作者表示那没什么好办法满足你。
			JSON_MAPPER = JsonMapper.builder()
				.configure(SerializationFeature.INDENT_OUTPUT, true)
				.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, true)
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true)
				.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true)
				.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
				.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false)
				.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
				.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true)
				.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
				.build();

			// 尝试注册能发现的所有模块
			JacksonModuleUtils.loadModulesAndRegister(JSON_MAPPER, null);

			JSON_PATH_CONF = Configuration.builder()
				.jsonProvider(new JacksonJsonProvider(JSON_MAPPER))
				.mappingProvider(new JacksonMappingProvider(JSON_MAPPER))
				.build();
		}

		private static ObjectMapper getJsonMapper() {
			return JSON_MAPPER;
		}

		private static Configuration getJsonPathConf() {
			return JSON_PATH_CONF;
		}
	}

}
