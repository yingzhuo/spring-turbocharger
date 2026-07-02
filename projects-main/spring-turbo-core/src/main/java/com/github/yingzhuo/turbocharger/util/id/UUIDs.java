package com.github.yingzhuo.turbocharger.util.id;

import com.github.yingzhuo.turbocharger.util.StringPool;

import java.util.UUID;

public final class UUIDs {

	private UUIDs() {
		super();
	}

	public static String v4(boolean removeHyphen) {
		var uuid = UUID.randomUUID().toString();
		return removeHyphen ? uuid.replaceAll(StringPool.HYPHEN, StringPool.EMPTY) : uuid;
	}

	public static String classic32() {
		return v4(true);
	}

	public static String classic36() {
		return v4(false);
	}

}
