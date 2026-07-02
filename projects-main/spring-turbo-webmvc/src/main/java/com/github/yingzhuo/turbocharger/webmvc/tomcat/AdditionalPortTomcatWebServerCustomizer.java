package com.github.yingzhuo.turbocharger.webmvc.tomcat;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
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
		//factory.addAdditionalTomcatConnectors(createStanderConnector());
		factory.addAdditionalConnectors(createStanderConnector());
	}

	private Connector createStanderConnector() {
		var connector = new Connector(protocol);
		connector.setPort(port);
		return connector;
	}

}
