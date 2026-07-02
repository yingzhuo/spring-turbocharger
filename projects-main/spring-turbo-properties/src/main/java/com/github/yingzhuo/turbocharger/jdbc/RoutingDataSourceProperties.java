package com.github.yingzhuo.turbocharger.jdbc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "springturbo.routing-data-source")
public class RoutingDataSourceProperties implements Serializable, InitializingBean {

	private boolean enabled = true;
	private String defaultDataSourceName;
	private Map<String, HikariProperties> hikariDataSources;

	@Override
	public void afterPropertiesSet() {
		Assert.hasText(defaultDataSourceName, "defaultDataSourceName is required");
		Assert.notEmpty(hikariDataSources, "hikariDataSources is empty");
	}

}
