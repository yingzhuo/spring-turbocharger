package com.github.yingzhuo.turbocharger.redis.lock;

import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.util.Stack;

public final class LockStack implements Serializable {

	private final Stack<LockFrame> frames = new Stack<>();

	public LockStack() {
		super();
	}

	public void push(LockFrame lockFrame) {
		frames.push(lockFrame);
	}

	@Nullable
	public LockFrame pop() {
		return isEmpty() ? null : frames.pop();
	}

	@Nullable
	public LockFrame peek() {
		return isEmpty() ? null : frames.peek();
	}

	public boolean isEmpty() {
		return frames.isEmpty();
	}

}
