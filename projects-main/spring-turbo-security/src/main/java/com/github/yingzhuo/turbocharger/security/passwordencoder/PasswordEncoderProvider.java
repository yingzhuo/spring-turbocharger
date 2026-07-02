package com.github.yingzhuo.turbocharger.security.passwordencoder;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 应卓
 * @see PasswordEncoderFactoryBean
 * @see org.springframework.security.crypto.password.DelegatingPasswordEncoder
 * @see com.github.yingzhuo.turbocharger.util.spi.SPILoader
 * @since 3.5.3
 */
public interface PasswordEncoderProvider {

	/**
	 * 获取ID
	 *
	 * @return ID
	 */
	public String getId();

	/**
	 * 获取{@link PasswordEncoder} 实例
	 *
	 * @return {@link PasswordEncoder} 实例
	 */
	public PasswordEncoder getEncoder();

}
