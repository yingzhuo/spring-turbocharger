package com.github.yingzhuo.turbocharger.util;

import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public final class KeyStoreUtils {

	private KeyStoreUtils() {
		super();
	}

	public static KeyStore loadKeyStore(InputStream inputStream, KeyStoreType type, String storepass) {
		Assert.notNull(inputStream, "inputStream is required");
		Assert.notNull(type, "type is required");
		Assert.notNull(storepass, "storepass is required");

		try (var input = inputStream) {
			var keyStore = KeyStore.getInstance(type.getValue());
			keyStore.load(input, storepass.toCharArray());
			return keyStore;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static <T extends Key> T getKey(KeyStore loadedKeyStore, String alias, String keypass) {
		Assert.notNull(loadedKeyStore, "keyStore is required");
		Assert.hasText(alias, "alias is required");
		Assert.notNull(keypass, "keypass is required");

		T key;
		try {
			key = (T) loadedKeyStore.getKey(alias, keypass.toCharArray());
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}

		if (key == null) {
			throw new IllegalArgumentException("cannot find key with alias: " + alias);
		}
		return key;
	}

	public static <T extends PrivateKey> T getPrivateKey(KeyStore loadedKeyStore, String alias, String keypass) {
		return getKey(loadedKeyStore, alias, keypass);
	}

	public static <T extends PublicKey> T getPublicKey(KeyStore loadedKeyStore, String alias) {
		var cert = getCertificate(loadedKeyStore, alias);
		return (T) cert.getPublicKey();
	}

	public static List<Certificate> getCertificateChain(KeyStore loadedKeyStore, String alias) {
		try {
			return Arrays.asList(loadedKeyStore.getCertificateChain(alias));
		} catch (KeyStoreException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static <T extends Certificate> T getCertificate(KeyStore loadedKeyStore, String alias) {
		Assert.notNull(loadedKeyStore, "keyStore is required");
		Assert.hasText(alias, "alias is required");

		T certificate;
		try {
			certificate = (T) loadedKeyStore.getCertificate(alias);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}

		if (certificate == null) {
			throw new IllegalArgumentException("cannot find certificate with alias: " + alias);
		}
		return certificate;
	}

	public static KeyPair getKeyPair(KeyStore loadedKeyStore, String alias, String keypass) {
		return new KeyPair(getPublicKey(loadedKeyStore, alias), getPrivateKey(loadedKeyStore, alias, keypass));
	}

	public static String getSigAlgName(KeyStore loadedKeyStore, String alias) {
		var cert = getCertificate(loadedKeyStore, alias);
		if (cert instanceof X509Certificate x509Cert) {
			return x509Cert.getSigAlgName();
		}
		throw new IllegalArgumentException("cannot get SigAlg");
	}

	public static String getSigAlgOID(KeyStore loadedKeyStore, String alias) {
		var cert = getCertificate(loadedKeyStore, alias);
		if (cert instanceof X509Certificate x509Cert) {
			return x509Cert.getSigAlgOID();
		}
		throw new IllegalArgumentException("cannot get SigAlgOID");
	}

	public static <T extends SecretKey> T getSecretKey(KeyStore loadedKeyStore, String alias, String keypass) {
		return getKey(loadedKeyStore, alias, keypass);
	}

	public static List<String> getAliases(KeyStore loadedKeyStore) {
		Assert.notNull(loadedKeyStore, "keyStore is required");

		try {
			return Collections.unmodifiableList(Collections.list(loadedKeyStore.aliases()));
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static boolean containsAlias(KeyStore loadedKeyStore, String alias) {
		Assert.notNull(loadedKeyStore, "keyStore is required");
		Assert.hasText(alias, "alias is required");

		try {
			return loadedKeyStore.containsAlias(alias);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
