package com.github.yingzhuo.turbocharger.jwt;

import java.io.Serializable;

public interface JwtConstants extends Serializable {

	// headers
	// -----------------------------------------------------------------------------------------------------------------
	String HEADER_TYPE = "typ";
	String HEADER_KEY_ID = "kid";
	String HEADER_ALGORITHM = "alg";
	String HEADER_CONTENT_TYPE = "cty";

	// payload
	// -----------------------------------------------------------------------------------------------------------------
	String PAYLOAD_ISSUER = "iss";
	String PAYLOAD_SUBJECT = "sub";
	String PAYLOAD_AUDIENCE = "aud";
	String PAYLOAD_EXPIRES = "exp";
	String PAYLOAD_NOT_BEFORE = "nbf";
	String PAYLOAD_ISSUED_AT = "iat";
	String PAYLOAD_JWT_ID = "jti";

}
