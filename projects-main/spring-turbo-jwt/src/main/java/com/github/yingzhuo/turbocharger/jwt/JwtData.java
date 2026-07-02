package com.github.yingzhuo.turbocharger.jwt;

import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Supplier;

public final class JwtData implements JwtConstants {

	private final Map<String, Object> headerMap = new HashMap<>();
	private final Map<String, Object> payloadMap = new HashMap<>();

	public JwtData() {
		addHeaderType("JWT");
	}

	public static JwtData newInstance() {
		return new JwtData();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		JwtData jwtData = (JwtData) o;
		return Objects.equals(headerMap, jwtData.headerMap) && Objects.equals(payloadMap, jwtData.payloadMap);
	}

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
