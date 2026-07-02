package com.github.yingzhuo.turbocharger.core.io;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuppressWarnings("unchecked")
public class PemResource extends DelegatingResource {

	private final PemContent pemContent;
	private final @Nullable String keypass;

	public PemResource(Resource delegatingResource) {
		this(delegatingResource, null);
	}

	public PemResource(Resource delegatingResource, @Nullable String keypass) {
		super(delegatingResource);
		this.keypass = keypass;

		try {
			this.pemContent = PemContent.of(delegatingResource.getContentAsString(UTF_8));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Nullable
	public String getKeypass() {
		return keypass;
	}

	public PemContent getPemContent() {
		return pemContent;
	}

	public List<X509Certificate> getCertificates() {
		return pemContent.getCertificates();
	}

	public X509Certificate getCertificate() {
		var certs = pemContent.getCertificates();
		if (certs.size() != 1) {
			throw new IllegalArgumentException("not unique certificate");
		}
		return certs.get(0);
	}

	public <T extends PublicKey> T getPublicKey() {
		return (T) getCertificate().getPublicKey();
	}

	public <T extends PrivateKey> T getPrivateKey() {
		return (T) pemContent.getPrivateKey(keypass);
	}

	public KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}
}
