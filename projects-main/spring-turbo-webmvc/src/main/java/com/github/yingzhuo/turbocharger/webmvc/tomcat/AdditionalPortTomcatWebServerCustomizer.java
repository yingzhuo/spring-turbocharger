/*
 * Copyright 2022-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.turbocharger.webmvc.tomcat;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * @author 应卓
 * @since 3.3.1
 */
public class AdditionalPortTomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	private int port = 8080;
	private String protocol = TomcatServletWebServerFactory.DEFAULT_PROTOCOL;

	public void setPort(int port) {
		this.port = port;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		factory.addAdditionalTomcatConnectors(createStanderConnector());
	}

	private Connector createStanderConnector() {
		var connector = new Connector(protocol);
		connector.setPort(port);
		return connector;
	}

}
