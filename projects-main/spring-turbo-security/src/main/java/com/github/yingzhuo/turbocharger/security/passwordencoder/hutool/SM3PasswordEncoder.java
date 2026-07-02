package com.github.yingzhuo.turbocharger.security.passwordencoder.hutool;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 国密算法 (SM3)
 *
 * @author 应卓
 * @see org.springframework.security.crypto.factory.PasswordEncoderFactories
 * @since 1.0.1
 */
public final class SM3PasswordEncoder implements PasswordEncoder {

	/**
	 * 构造方法
	 */
	public SM3PasswordEncoder() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		var digester = DigestUtil.digester("SM3");
		return digester.digestHex(rawPassword.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}

}
