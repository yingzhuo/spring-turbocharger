package com.github.yingzhuo.turbocharger.xxljob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ConfigurationProperties(prefix = "springturbo.xxljob")
public class XXLJobProperties implements InitializingBean, Serializable {

	private boolean enabled = true;

	private String adminAddresses;

	private String accessToken;

	private String executorApplicationName;

	private String executorAddress;

	private String executorIp;

	private int executorPort = 9999;

	private String logPath = "/tmp";

	private int logRetentionDays = 7;

	@Override
	public void afterPropertiesSet() {
	}
}
