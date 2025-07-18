/*
 * Copyright 2022-present the original author or authors.
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
package com.github.yingzhuo.turbocharger.databinding;

import org.springframework.context.MessageSourceResolvable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 包含多个 {@link MessageSourceResolvable} 对象的类型
 *
 * @author 应卓
 * @see MessageSourceResolvable
 * @since 3.3.1
 */
@FunctionalInterface
public interface MultiMessageSourceResolvable extends Iterable<MessageSourceResolvable>, Serializable {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<MessageSourceResolvable> iterator();

	/**
	 * 转换成 {@link Stream}
	 *
	 * @return stream
	 */
	public default Stream<MessageSourceResolvable> stream() {
		return stream(false);
	}

	/**
	 * 转换成 {@link Stream}
	 *
	 * @param parallel 是否生成并行的Stream
	 * @return stream
	 */
	public default Stream<MessageSourceResolvable> stream(boolean parallel) {
		return StreamSupport.stream(spliterator(), parallel);
	}

}
