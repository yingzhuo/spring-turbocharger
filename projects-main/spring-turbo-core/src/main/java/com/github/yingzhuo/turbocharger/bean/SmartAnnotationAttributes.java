package com.github.yingzhuo.turbocharger.bean;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.stream.Stream;

public class SmartAnnotationAttributes extends AnnotationAttributes {

	private final Environment environment;

	public SmartAnnotationAttributes(Environment environment, AnnotationAttributes other) {
		super(other);
		this.environment = environment;
	}

	public SmartAnnotationAttributes(Environment environment, Map<String, Object> other) {
		this(environment, new AnnotationAttributes(other));
	}

	@Override
	public String getString(String attributeName) {
		var text = super.getString(attributeName);
		return environment.resolvePlaceholders(text);
	}

	@Override
	public String[] getStringArray(String attributeName) {
		var array = super.getStringArray(attributeName);
		return Stream.of(array)
			.map(environment::resolvePlaceholders)
			.toArray(String[]::new);
	}

}
