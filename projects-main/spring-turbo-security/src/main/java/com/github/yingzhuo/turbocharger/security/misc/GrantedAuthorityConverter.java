package com.github.yingzhuo.turbocharger.security.misc;

import org.jspecify.annotations.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GrantedAuthorityConverter implements Converter<String, GrantedAuthority> {

	@Nullable
	@Override
	public GrantedAuthority convert(String source) {
		return new SimpleGrantedAuthority(source);
	}

}
