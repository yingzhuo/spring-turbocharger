package com.github.yingzhuo.turbocharger.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Base64;

public abstract class AbstractAlgorithm extends Algorithm implements Serializable {

	protected AbstractAlgorithm(String name) {
		this(name, name);
	}

	protected AbstractAlgorithm(String name, String description) {
		super(name, description);
	}

	@Override
	public final void verify(DecodedJWT jwt) throws SignatureVerificationException {

		// header + '.' + payload
		var data = String.join(".", jwt.getHeader(), jwt.getPayload())
			.getBytes(StandardCharsets.UTF_8);

		// 签名
		var signature = Base64.getUrlDecoder().decode(jwt.getSignature());

		try {
			doVerify(data, signature);
		} catch (SignatureVerificationException e) {
			throw e;
		} catch (Exception e) {
			throw new SignatureVerificationException(this, e);
		}
	}

	@Override
	public final byte[] sign(byte[] bytes) throws SignatureGenerationException {
		try {
			return doSign(bytes);
		} catch (SignatureGenerationException e) {
			throw e;
		} catch (Exception e) {
			throw new SignatureGenerationException(this, e);
		}
	}

	protected abstract byte[] doSign(byte[] data) throws Exception;

	protected abstract void doVerify(byte[] data, byte[] signature) throws Exception;

	public abstract X509Certificate getCertificate();

	public abstract PublicKey getPublicKey();

	public abstract PrivateKey getPrivateKey();

	public final KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}

}
