package com.github.yingzhuo.turbocharger.util;

import java.io.Serializable;

/**
 * {@link java.security.KeyStore}格式类型，本程序库只支持以下两种。<br>
 * 其中 <em>pkcs12</em> 为推荐格式
 *
 * <ul>
 *     <li>pkcs12</li>
 *     <li>jks</li>
 * </ul>
 *
 * @author 应卓
 * @since 3.3.1
 */
public enum KeyStoreType implements Serializable {

	/**
	 * PKCS#12格式, Java9以后此格式为默认。 推荐此格式，文件扩展名为 p12或pfx。
	 */
	PKCS12("pkcs12"),

	/**
	 * JKS，Java8及以前使用的格式。 文件扩展名为jks。
	 */
	JKS("jks");

	/**
	 * 字符串类型值
	 */
	private final String value;

	/**
	 * 私有构造方法
	 *
	 * @param stringValue 字符串类型值
	 */
	KeyStoreType(String stringValue) {
		this.value = stringValue;
	}

	public String getValue() {
		return value;
	}

}
