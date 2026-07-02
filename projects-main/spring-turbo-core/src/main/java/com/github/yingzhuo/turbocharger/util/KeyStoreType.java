package com.github.yingzhuo.turbocharger.util;

import java.io.Serializable;

public enum KeyStoreType implements Serializable {

	PKCS12("pkcs12"),

	JKS("jks");

	private final String value;

	KeyStoreType(String stringValue) {
		this.value = stringValue;
	}

	public String getValue() {
		return value;
	}

}
