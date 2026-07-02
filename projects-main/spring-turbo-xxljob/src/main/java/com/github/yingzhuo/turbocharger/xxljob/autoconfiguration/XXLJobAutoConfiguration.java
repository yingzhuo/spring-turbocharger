package com.github.yingzhuo.turbocharger.xxljob.autoconfiguration;

import com.github.yingzhuo.turbocharger.xxljob.XXLJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 3.4.3
 */
@AutoConfiguration
@EnableConfigurationProperties(XXLJobProperties.class)
@ConditionalOnProperty(prefix = "springturbo.xxljob", name = "enabled", havingValue = "true", matchIfMissing = true)
public class XXLJobAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public XxlJobSpringExecutor xxlJobSpringExecutor(XXLJobProperties props) {
		final XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
		executor.setAdminAddresses(props.getAdminAddresses());
		executor.setAppname(props.getExecutorApplicationName());
		executor.setAddress(props.getExecutorAddress());
		executor.setIp(props.getExecutorIp());
		executor.setPort(props.getExecutorPort());
		executor.setAccessToken(props.getAccessToken());
		executor.setLogPath(props.getLogPath());
		executor.setLogRetentionDays(props.getLogRetentionDays());
		return executor;
	}

}
