package com.github.yingzhuo.turbocharger.util.id;

import com.github.yingzhuo.turbocharger.util.StringPool;

import java.util.UUID;

/**
 * {@link UUID} 相关工具
 *
 * @author 应卓
 * @since 3.4.4
 */
public final class UUIDs {

	/**
	 * 私有构造方法
	 */
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
