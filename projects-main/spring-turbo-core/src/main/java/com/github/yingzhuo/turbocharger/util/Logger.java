/*
 *
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
 *
 */

package com.github.yingzhuo.turbocharger.util;

import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * {@link Logger} 的包装工具
 *
 * @author 应卓
 * @see LogLevel
 * @since 1.0.0
 */
public final class Logger implements Serializable {

	@Nullable
	private final org.slf4j.Logger log;

	private final boolean enabled;

	private final LogLevel level;

	public Logger(String loggerName, LogLevel level) {
		Assert.notNull(level, "level is required");
		Assert.hasText(loggerName, "loggerName is required");

		this.level = level;

		if (level == LogLevel.STDOUT || level == LogLevel.STDERR) {
			this.log = null;
		} else {
			this.log = LoggerFactory.getLogger(loggerName);
		}

		this.enabled = this.checkEnabled();
	}

	public Logger(Class<?> loggerName, LogLevel level) {
		Assert.notNull(level, "level is required");
		Assert.notNull(loggerName, "loggerName is required");

		this.level = level;

		if (level == LogLevel.STDOUT || level == LogLevel.STDERR) {
			this.log = null;
		} else {
			this.log = LoggerFactory.getLogger(loggerName);
		}

		this.enabled = this.checkEnabled();
	}

	public boolean isEnabled() {
		return enabled;
	}

	private boolean checkEnabled() {
		return switch (level) {
			case STDOUT, STDERR -> true;
			case TRACE -> log != null && log.isTraceEnabled();
			case DEBUG -> log != null && log.isDebugEnabled();
			case INFO -> log != null && log.isInfoEnabled();
			case WARN -> log != null && log.isWarnEnabled();
			case ERROR -> log != null && log.isErrorEnabled();
			default -> false;
		};
	}

	public void log(String format, Object... args) {
		if (isEnabled()) {
			switch (level) {
				case TRACE:
					trace(format, args);
					break;
				case DEBUG:
					debug(format, args);
					break;
				case INFO:
					info(format, args);
					break;
				case WARN:
					warn(format, args);
					break;
				case ERROR:
					error(format, args);
					break;
				case STDOUT:
					stdout(format, args);
					break;
				case STDERR:
					stderr(format, args);
				default:
					// nop
			}
		}
	}

	private void stdout(String format, Object... args) {
		System.out.println(StringFormatter.format(format, args));
	}

	private void stderr(String format, Object... args) {
		System.err.println(StringFormatter.format(format, args));
	}

	private void trace(String format, Object... args) {
		if (log != null) {
			log.trace(format, args);
		}
	}

	private void debug(String format, Object... args) {
		if (log != null) {
			log.debug(format, args);
		}
	}

	private void info(String format, Object... args) {
		if (log != null) {
			log.info(format, args);
		}
	}

	private void warn(String format, Object... args) {
		if (log != null) {
			log.warn(format, args);
		}
	}

	private void error(String format, Object... args) {
		if (log != null) {
			log.warn(format, args);
		}
	}

}
