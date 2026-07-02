package com.github.yingzhuo.turbocharger.jwt;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public enum ValidatingResult implements Serializable {

	OK,

	INVALID_JWT_FORMAT,

	INVALID_SIGNATURE,

	INVALID_TIME,

	INVALID_CLAIM

}
