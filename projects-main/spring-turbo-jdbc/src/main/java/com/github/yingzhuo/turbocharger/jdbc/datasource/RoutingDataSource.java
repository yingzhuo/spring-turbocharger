package com.github.yingzhuo.turbocharger.jdbc.datasource;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class RoutingDataSource extends AbstractRoutingDataSource implements DataSource, InitializingBean {

	public RoutingDataSource(DataSource defaultDataSource, Map<String, DataSource> targetDataSources) {
		Assert.notNull(defaultDataSource, "defaultDataSource is required");
		Assert.notEmpty(targetDataSources, "targetDataSources is null or empty");

		super.setDefaultTargetDataSource(defaultDataSource);
		super.setTargetDataSources(new HashMap<>(targetDataSources));
		super.setDataSourceLookup(new MapDataSourceLookup());
	}

	@Nullable
	@Override
	protected Object determineCurrentLookupKey() {
		return RoutingDataSourceLookup.get();
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
	}

}
