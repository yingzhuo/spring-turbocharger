package com.github.yingzhuo.turbocharger.util.concurrent;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ThreadUtils {

	private ThreadUtils() {
		super();
	}

	public static Set<Thread> getNonDaemonThreads() {
		return getThreads(AsyncVoid.NON_DAEMON);
	}

	public static Set<Thread> getDaemonThreads() {
		return getThreads(AsyncVoid.IS_DAEMON);
	}

	public static Set<Thread> getAllThreads() {
		return getThreads(AsyncVoid.ANY);
	}

	public static Set<Thread> getThreads(@Nullable Predicate<Thread> filter) {
		filter = Objects.requireNonNullElse(filter, AsyncVoid.ANY);

		return Thread.getAllStackTraces()
			.keySet()
			.stream()
			.filter(filter)
			.collect(Collectors.toUnmodifiableSet());
	}

	public static Optional<Thread> getById(long id) {
		return getById(id, false);
	}

	public static Optional<Thread> getById(long id, boolean includingDaemonThread) {
		if (id < 0) {
			return Optional.empty();
		}

		var p = includingDaemonThread ? AsyncVoid.ANY : AsyncVoid.NON_DAEMON;

		return Thread.getAllStackTraces()
			.keySet()
			.stream()
			.filter(p)
			.filter(t -> t.getId() == id)
			.findFirst();
	}

	public static Optional<Thread> getByName(String name) {
		return getByName(name, false);
	}

	public static Optional<Thread> getByName(String name, boolean includingDaemonThread) {
		Assert.hasText(name, "name is null or blank");

		var p = includingDaemonThread ? AsyncVoid.ANY : AsyncVoid.NON_DAEMON;

		return Thread.getAllStackTraces()
			.keySet()
			.stream()
			.filter(p)
			.filter(t -> t.getName().equals(name))
			.findFirst();
	}

	// 延迟加载
	private static class AsyncVoid {
		private static final Predicate<Thread> ANY = t -> true;
		private static final Predicate<Thread> IS_DAEMON = Thread::isDaemon;
		private static final Predicate<Thread> NON_DAEMON = Predicate.not(IS_DAEMON);
	}

}
