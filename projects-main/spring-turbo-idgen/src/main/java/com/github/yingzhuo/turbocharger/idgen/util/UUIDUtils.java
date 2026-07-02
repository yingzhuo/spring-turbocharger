package com.github.yingzhuo.turbocharger.idgen.util;

import com.github.yingzhuo.turbocharger.core.SpringUtils;
import com.github.yingzhuo.turbocharger.idgen.UUIDGenerator;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

public final class UUIDUtils {

	private UUIDUtils() {
		super();
	}

	public static String v1() {
		return v1(false);
	}

	public static String v1(boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v1()
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v2(int localIdentifier) {
		return v2(localIdentifier, false);
	}

	public static String v2(int localIdentifier, boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v2(localIdentifier)
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v3(String namespace) {
		return v3(namespace, false);
	}

	public static String v3(String namespace, boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v3(namespace)
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v4() {
		return v4(false);
	}

	public static String v4(boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v4()
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v5(String namespace) {
		return v5(namespace, false);
	}

	public static String v5(String namespace, boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v5(namespace)
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v6() {
		return v6(false);
	}

	public static String v6(boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v6()
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v7() {
		return v7(false);
	}

	public static String v7(boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v7()
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}
}
