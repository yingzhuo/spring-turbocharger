package com.github.yingzhuo.turbocharger.secret;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * {@link KeyBundle} 默认实现类
 *
 * @author 应卓
 * @since 3.5.3
 */
@SuppressWarnings("unchecked")
public class KeyBundleImpl implements KeyBundle {

	private final X509Certificate certificate;
	private final PrivateKey privateKey;

	/**
	 * 构造方法
	 *
	 * @param certificate 证书
	 * @param privateKey  私钥
	 */
	public KeyBundleImpl(X509Certificate certificate,
						 PrivateKey privateKey) {
		this.certificate = certificate;
		this.privateKey = privateKey;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends PublicKey> T getPublicKey() {
		return (T) getCertificate().getPublicKey();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends PrivateKey> T getPrivateKey() {
		return (T) this.privateKey;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends X509Certificate> T getCertificate() {
		return (T) this.certificate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<X509Certificate> getCertificateChain() {
		return List.of(this.certificate);
	}

}
