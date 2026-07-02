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
