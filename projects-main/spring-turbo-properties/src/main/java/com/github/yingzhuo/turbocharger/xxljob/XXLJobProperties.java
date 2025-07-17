/*
 * Copyright 2025-present the original author or authors.
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
package com.github.yingzhuo.turbocharger.xxljob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 3.4.3
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ConfigurationProperties(prefix = "springturbo.xxljob")
public class XXLJobProperties implements InitializingBean, Serializable {

	/**
	 * enable this starter if true
	 */
	private boolean enabled = true;

	/**
	 * xxl-job admin address list, such as "<a href="http://address">...</a>" or "<a href="http://address01,http://address02">...</a>"
	 */
	private String adminAddresses;

	/**
	 * access token
	 */
	private String accessToken;

	/**
	 * application name
	 */
	private String executorApplicationName;

	/**
	 * executor registry-address: default use address to registry , otherwise use ip:port if address is null
	 */
	private String executorAddress;

	/**
	 * executor IP address
	 */
	private String executorIp;

	/**
	 * executor port
	 */
	private int executorPort = 9999;

	/**
	 * executor log for path
	 */
	private String logPath = "/tmp";

	/**
	 * log retention days
	 */
	private int logRetentionDays = 7;

	@Override
	public void afterPropertiesSet() {
	}
}
