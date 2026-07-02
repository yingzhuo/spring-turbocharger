package com.github.yingzhuo.turbocharger.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;

public final class SignatureUtils {

	private SignatureUtils() {
		super();
	}

	public static byte[] sign(byte[] data, String sigAlgName, PrivateKey privateKey) {
		try {
			var signature = Signature.getInstance(sigAlgName);
			signature.initSign(privateKey);
			signature.update(data);
			return signature.sign();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static boolean verify(byte[] data, byte[] sign, String sigAlgName, PublicKey publicKey) {
		try {
			var signature = Signature.getInstance(sigAlgName);
			signature.initVerify(publicKey);
			signature.update(data);
			return signature.verify(sign);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static boolean verify(byte[] data, byte[] sign, X509Certificate certificate) {
		return verify(data, sign, certificate.getSigAlgName(), certificate.getPublicKey());
	}

}
