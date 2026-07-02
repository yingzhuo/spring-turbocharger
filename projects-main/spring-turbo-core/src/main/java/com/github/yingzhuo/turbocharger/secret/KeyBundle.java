package com.github.yingzhuo.turbocharger.secret;

import com.github.yingzhuo.turbocharger.util.SignatureUtils;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.function.Supplier;

/**
 * 对秘钥/密钥对的简易封装
 *
 * @author 应卓
 * @see KeyPair
 * @see SignatureUtils
 * @see KeyBundleFactories
 * @since 3.5.3
 */
public interface KeyBundle extends Supplier<KeyPair>, Serializable {

	/**
	 * 获取公钥
	 *
	 * @param <T> 公钥的具体类型
	 * @return 公钥
	 */
	public <T extends PublicKey> T getPublicKey();

	/**
	 * 获取私钥
	 *
	 * @param <T> 私钥的具体类型
	 * @return 私钥
	 */
	public <T extends PrivateKey> T getPrivateKey();

	/**
	 * 获取密钥对
	 *
	 * @return 秘钥对
	 */
	public default KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public default KeyPair get() {
		return getKeyPair();
	}

	/**
	 * 获取证书
	 *
	 * @param <T> 证书的具体类型
	 * @return 证书
	 */
	public <T extends X509Certificate> T getCertificate();

	/**
	 * 获取证书链
	 *
	 * @return 证书链
	 */
	public List<X509Certificate> getCertificateChain();

}
