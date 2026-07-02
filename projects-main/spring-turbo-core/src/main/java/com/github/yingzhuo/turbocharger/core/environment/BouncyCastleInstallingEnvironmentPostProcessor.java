package com.github.yingzhuo.turbocharger.core.environment;

import org.apache.commons.logging.Log;
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.env.ConfigurableEnvironment;

import java.security.Provider;
import java.security.Security;

import static org.springframework.util.ClassUtils.forName;
import static org.springframework.util.ClassUtils.getDefaultClassLoader;

public class BouncyCastleInstallingEnvironmentPostProcessor implements EnvironmentPostProcessor {

	private static final String BOUNCY_CASTLE_PROVIDER_CLASS = "org.bouncycastle.jce.provider.BouncyCastleProvider";

	private final Log log;

	public BouncyCastleInstallingEnvironmentPostProcessor(DeferredLogFactory logFactory) {
		this.log = logFactory.getLog(BouncyCastleInstallingEnvironmentPostProcessor.class);
	}

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		try {
			var cls = forName(BOUNCY_CASTLE_PROVIDER_CLASS, getDefaultClassLoader());
			var ctor = cls.getConstructor();
			var provider = ctor.newInstance();
			Security.addProvider((Provider) provider);
			log.debug("BouncyCastle provider class: " + provider);
		} catch (Throwable ignored) {
			// noop
		}
	}

}
