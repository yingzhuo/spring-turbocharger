package com.github.yingzhuo.turbocharger.security.passwordencoder;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordEncoderProvider {

	public String getId();

	public PasswordEncoder getEncoder();

}
