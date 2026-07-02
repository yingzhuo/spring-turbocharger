package com.github.yingzhuo.turbocharger.secret;

import com.github.yingzhuo.turbocharger.util.SignatureUtils;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.function.Supplier;

public interface KeyBundle extends Supplier<KeyPair>, Serializable {

	public <T extends PublicKey> T getPublicKey();

	public <T extends PrivateKey> T getPrivateKey();

	public default KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}

	@Override
	public default KeyPair get() {
		return getKeyPair();
	}

	public <T extends X509Certificate> T getCertificate();

	public List<X509Certificate> getCertificateChain();

}
