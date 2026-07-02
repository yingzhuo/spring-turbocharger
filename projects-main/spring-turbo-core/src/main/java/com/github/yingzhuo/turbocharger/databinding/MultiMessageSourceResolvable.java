package com.github.yingzhuo.turbocharger.databinding;

import org.springframework.context.MessageSourceResolvable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@FunctionalInterface
public interface MultiMessageSourceResolvable extends Iterable<MessageSourceResolvable>, Serializable {

	@Override
	public Iterator<MessageSourceResolvable> iterator();

	public default Stream<MessageSourceResolvable> stream() {
		return stream(false);
	}

	public default Stream<MessageSourceResolvable> stream(boolean parallel) {
		return StreamSupport.stream(spliterator(), parallel);
	}

}
