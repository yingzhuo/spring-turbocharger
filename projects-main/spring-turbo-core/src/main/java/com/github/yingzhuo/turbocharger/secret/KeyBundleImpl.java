package com.github.yingzhuo.turbocharger.secret;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

@SuppressWarnings("unchecked")
public class KeyBundleImpl implements KeyBundle {

	private final X509Certificate certificate;
	private final PrivateKey privateKey;

	public KeyBundleImpl(X509Certificate certificate,
						 PrivateKey privateKey) {
		this.certificate = certificate;
		this.privateKey = privateKey;
	}

	@Override
	public <T extends PublicKey> T getPublicKey() {
		return (T) getCertificate().getPublicKey();
	}

	@Override
	public <T extends PrivateKey> T getPrivateKey() {
		return (T) this.privateKey;
	}

	@Override
	public <T extends X509Certificate> T getCertificate() {
		return (T) this.certificate;
	}

	@Override
	public List<X509Certificate> getCertificateChain() {
		return List.of(this.certificate);
	}

}
