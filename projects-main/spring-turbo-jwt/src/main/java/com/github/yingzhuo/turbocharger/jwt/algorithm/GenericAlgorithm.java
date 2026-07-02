package com.github.yingzhuo.turbocharger.jwt.algorithm;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.ssl.pem.PemContent;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Map;

public class GenericAlgorithm extends AbstractAlgorithm {

	private static final Map<String, String> ALG_NAME_MAPPING;

	static {
		ALG_NAME_MAPPING = Map.of(
			"SHA256withRSA", "RS256",
			"SHA385withRSA", "RS384",
			"SHA512withRSA", "RS512",
			"SHA256withECDSA", "ES256",
			"SHA384withECDSA", "ES384",
			"SHA512withECDSA", "ES512"
		);
	}

	private final X509Certificate certificate;
	private final PublicKey publicKey;
	private final PrivateKey privateKey;
	private final String sigAlgName;

	public GenericAlgorithm(String pemContent) {
		this(pemContent, null);
	}

	public GenericAlgorithm(String pemContent, @Nullable String password) {
		super("<no name>", "<no description>");
		var pc = PemContent.of(pemContent);
		this.certificate = pc.getCertificates().get(0);
		this.sigAlgName = this.certificate.getSigAlgName();
		this.publicKey = this.certificate.getPublicKey();
		this.privateKey = pc.getPrivateKey(password);
	}

	@Override
	public String getName() {
		var newName = ALG_NAME_MAPPING.get(sigAlgName);
		return newName != null ? newName : sigAlgName;
	}

	@Override
	protected byte[] doSign(byte[] data) throws Exception {
		var s = Signature.getInstance(sigAlgName);
		s.initSign(privateKey, new SecureRandom());
		s.update(data);
		return s.sign();
	}

	@Override
	protected void doVerify(byte[] data, byte[] signature) throws Exception {
		var s = Signature.getInstance(sigAlgName);
		s.initVerify(publicKey);
		s.update(data);
		if (!s.verify(signature)) {
			throw new SignatureVerificationException(this);
		}
	}

	@Override
	public X509Certificate getCertificate() {
		return this.certificate;
	}

	@Override
	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	@Override
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

}
