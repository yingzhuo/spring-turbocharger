package com.github.yingzhuo.turbocharger.util;

import org.springframework.util.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class DigestUtils {

	public static final String ALG_MD2 = "MD2";
	public static final String ALG_MD5 = "MD5";
	public static final String ALG_SHA_1 = "SHA-1";
	public static final String ALG_SHA_256 = "SHA-256";
	public static final String ALG_SHA_384 = "SHA-384";
	public static final String ALG_SHA_512 = "SHA-512";

	private DigestUtils() {
		super();
	}

	public static byte[] md2(byte[] data) {
		try {
			var digest = MessageDigest.getInstance(ALG_MD2);
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static String md2Hex(String data) {
		Assert.notNull(data, "data is required");
		return HexUtils.encodeToString(md2(data.getBytes(UTF_8)));
	}

	public static byte[] md5(byte[] data) {
		try {
			var digest = MessageDigest.getInstance(ALG_MD5);
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static String md5Hex(String data) {
		Assert.notNull(data, "data is required");
		return HexUtils.encodeToString(md5(data.getBytes(UTF_8)));
	}

	public static byte[] sha1(byte[] data) {
		try {
			var digest = MessageDigest.getInstance(ALG_SHA_1);
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static String sha1Hex(String data) {
		Assert.notNull(data, "data is required");
		return HexUtils.encodeToString(sha1(data.getBytes(UTF_8)));
	}

	public static byte[] sha256(byte[] data) {
		try {
			var digest = MessageDigest.getInstance(ALG_SHA_256);
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static String sha256Hex(String data) {
		Assert.notNull(data, "data is required");
		return HexUtils.encodeToString(sha256(data.getBytes(UTF_8)));
	}

	public static byte[] sha384(byte[] data) {
		try {
			var digest = MessageDigest.getInstance(ALG_SHA_384);
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static String sha384Hex(String data) {
		Assert.notNull(data, "data is required");
		return HexUtils.encodeToString(sha384(data.getBytes(UTF_8)));
	}

	public static byte[] sha512(byte[] data) {
		try {
			var digest = MessageDigest.getInstance(ALG_SHA_512);
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static String sha512Hex(String data) {
		Assert.notNull(data, "data is required");
		return HexUtils.encodeToString(sha512(data.getBytes(UTF_8)));
	}

}
