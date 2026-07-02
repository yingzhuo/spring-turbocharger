package com.github.yingzhuo.turbocharger.secret;

import com.github.yingzhuo.turbocharger.core.ResourceUtils;
import com.github.yingzhuo.turbocharger.util.KeyStoreType;
import com.github.yingzhuo.turbocharger.util.KeyStoreUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.util.Assert;

import java.security.cert.X509Certificate;

public final class KeyBundleFactories {

	private KeyBundleFactories() {
		super();
	}

	public static KeyBundle loadFromPemContent(String pemContent, @Nullable String keypass) {
		Assert.hasText(pemContent, "pemContent must not be empty");

		var pc = PemContent.of(pemContent);
		return new KeyBundleImpl(pc.getCertificates().get(0), pc.getPrivateKey(keypass));
	}

	public static KeyBundle loadFromPem(String location, @Nullable String keypass) {
		Assert.hasText(location, "location must not be empty");

		var pc = PemContent.of(ResourceUtils.readResourceAsString(location));
		return new KeyBundleImpl(pc.getCertificates().get(0), pc.getPrivateKey(keypass));
	}

	public static KeyBundle loadFromStore(String location, KeyStoreType type, String storepass, String alias, @Nullable String keypass) {
		Assert.hasText(location, "location must not be empty");
		Assert.notNull(type, "type must not be null");
		Assert.hasText(storepass, "storepass must not be empty");
		Assert.hasText(alias, "alias must not be empty");

		var input = ResourceUtils.loadResourceAsInputStream(location);
		var ks = KeyStoreUtils.loadKeyStore(input, type, storepass);

		if (keypass == null) {
			keypass = storepass;
		}

		var cert = (X509Certificate) KeyStoreUtils.getCertificate(ks, alias);
		var privateKey = KeyStoreUtils.getPrivateKey(ks, alias, keypass);
		return new KeyBundleImpl(cert, privateKey);
	}

}
