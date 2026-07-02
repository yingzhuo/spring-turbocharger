package com.github.yingzhuo.turbocharger.util.collection.iterator;

import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingletonIterator<T> implements Iterator<T> {

	@Nullable
	private T element;

	public SingletonIterator(@Nullable T element) {
		this.element = element;
	}

	public static <T> SingletonIterator<T> of(@Nullable T element) {
		return new SingletonIterator<>(element);
	}

	@Override
	public boolean hasNext() {
		return element != null;
	}

	@Override
	public T next() {
		if (element != null) {
			var e = element;
			element = null;
			return e;
		} else {
			throw new NoSuchElementException();
		}
	}

}
