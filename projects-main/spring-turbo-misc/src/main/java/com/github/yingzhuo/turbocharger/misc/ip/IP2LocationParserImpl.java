/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.misc.ip;

import jakarta.annotation.Nullable;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

/**
 * {@link IP2LocationParser} 基础实现
 *
 * @author 应卓
 * @see IP2LocationParser
 * @since 3.5.6
 */
public class IP2LocationParserImpl implements IP2LocationParser {

	private static final byte[] DB_DATA;

	static {
		try {
			var resource = new ClassPathResource("ip2region/ip2region.db");
			DB_DATA = resource.getContentAsByteArray();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 默认构造方法
	 */
	public IP2LocationParserImpl() {
		super();
	}

	@Nullable
	@Override
	public String parse(String ip) {
		try {
			ClassPathResource classPathResource = new ClassPathResource("ip2region.db");
			InputStream inputStream = classPathResource.getInputStream();
			DbConfig config = new DbConfig();
			DbSearcher searcher = new DbSearcher(config, DB_DATA);
			DataBlock dataBlock = searcher.memorySearch(ip);
			return dataBlock.getRegion();
		} catch (Exception e) {
			return null;
		}
	}

}
