/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.jwt;

import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Supplier;

/**
 * JWT签名所包含的信息
 *
 * @author 应卓
 * @see #newInstance()
 * @see JwtConstants
 * @since 3.1.1
 */
public final class JwtData implements JwtConstants {

	private final Map<String, Object> headerMap = new HashMap<>();
	private final Map<String, Object> payloadMap = new HashMap<>();

	/**
	 * 默认构造方法
	 */
	public JwtData() {
		addHeaderType("JWT");
	}

	/**
	 * 创建新实例
	 *
	 * @return 新实例
	 */
	public static JwtData newInstance() {
		return new JwtData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		JwtData jwtData = (JwtData) o;
		return Objects.equals(headerMap, jwtData.headerMap) && Objects.equals(payloadMap, jwtData.payloadMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(headerMap, payloadMap);
	}

	public JwtData addHeaderType(String type) {
		headerMap.put(HEADER_TYPE, type);
		return this;
	}

	public JwtData addHeaderKeyId(String id) {
		headerMap.put(HEADER_KEY_ID, id);
		return this;
	}

	public JwtData addHeaderKeyId(Supplier<String> keyIdSupplier) {
		Assert.notNull(keyIdSupplier, "keyIdSupplier is null");
		return addHeaderKeyId(keyIdSupplier.get());
	}

	public JwtData addHeaderContentType(String contentType) {
		Assert.hasText(contentType, "contentType is null or blank");
		headerMap.put(HEADER_CONTENT_TYPE, contentType);
		return this;
	}

	@Deprecated
	public JwtData addHeaderAlgorithm(String algorithm) {
		Assert.hasText(algorithm, "algorithm is null or blank");
		headerMap.put(HEADER_ALGORITHM, algorithm);
		return this;
	}

	public JwtData addPayloadIssuer(String issuer) {
		Assert.hasText(issuer, "issuer is null or blank");
		payloadMap.put(PAYLOAD_ISSUER, issuer);
		return this;
	}

	public JwtData addPayloadSubject(String subject) {
		Assert.hasText(subject, "subject is null or blank");
		payloadMap.put(PAYLOAD_SUBJECT, subject);
		return this;
	}

	public JwtData addPayloadAudience(String... audience) {
		Assert.notNull(audience, "audience is null");
		payloadMap.put(PAYLOAD_AUDIENCE, audience);
		return this;
	}

	public JwtData addPayloadExpiresAt(LocalDateTime time) {
		Assert.notNull(time, "time is null");
		payloadMap.put(PAYLOAD_EXPIRES, toDate(time));
		return this;
	}

	public JwtData addPayloadExpiresAtFuture(Duration duration) {
		Assert.notNull(duration, "duration is null");
		payloadMap.put(PAYLOAD_EXPIRES, toDate(LocalDateTime.now().plus(duration)));
		return this;
	}

	public JwtData addPayloadNotBefore(LocalDateTime time) {
		Assert.notNull(time, "time is null");
		payloadMap.put(PAYLOAD_NOT_BEFORE, toDate(time));
		return this;
	}

	public JwtData addPayloadNotBeforeAtFuture(Duration duration) {
		Assert.notNull(duration, "duration is null");
		payloadMap.put(PAYLOAD_NOT_BEFORE, toDate(LocalDateTime.now().plus(duration)));
		return this;
	}

	public JwtData addPayloadIssuedAt(LocalDateTime time) {
		Assert.notNull(time, "time is null");
		payloadMap.put(PAYLOAD_ISSUED_AT, toDate(time));
		return this;
	}

	public JwtData addPayloadIssuedAtNow() {
		return addPayloadIssuedAt(LocalDateTime.now());
	}

	public JwtData addPayloadJwtId(Object jwtId) {
		Assert.notNull(jwtId, "jwtId is null");
		payloadMap.put(PAYLOAD_JWT_ID, jwtId);
		return this;
	}

	public JwtData addPayloadJwtId(Supplier<Object> jwtIdSupplier) {
		Assert.notNull(jwtIdSupplier, "jwtIdSupplier is null");
		return addPayloadJwtId(jwtIdSupplier.get());
	}

	public JwtData addHeader(String name, Object value) {
		Assert.hasText(name, "name is null or blank");
		Assert.notNull(value, "value is null");
		headerMap.put(name, value);
		return this;
	}

	public JwtData addPayload(String name, Object value) {
		Assert.hasText(name, "name is null or blank");
		Assert.notNull(value, "value is null");
		payloadMap.put(name, value);
		return this;
	}

	public Map<String, Object> getHeaderMap() {
		return Collections.unmodifiableMap(this.headerMap);
	}

	public Map<String, Object> getPayloadMap() {
		return Collections.unmodifiableMap(this.payloadMap);
	}

	private Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

}
