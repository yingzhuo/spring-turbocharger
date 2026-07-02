package com.github.yingzhuo.turbocharger.util.concurrent;

import com.github.yingzhuo.turbocharger.util.id.UUIDs;

public final class CurrentThreadUtils {

	private CurrentThreadUtils() {
		super();
	}

	public static Thread getCurrent() {
		return Thread.currentThread();
	}

	public static String getName() {
		return getCurrent().getName();
	}

	public static long getId() {
		try {
			return getCurrent().getId();
		} catch (Exception e) {
			return -1L;
		}
	}

	public static Thread.State getState() {
		return getCurrent().getState();
	}

	public static boolean isNew() {
		return getState() == Thread.State.NEW;
	}

	public static boolean isRunnable() {
		return getState() == Thread.State.RUNNABLE;
	}

	public static boolean isWaiting() {
		return getState() == Thread.State.WAITING;
	}

	public static boolean isBlocked() {
		return getState() == Thread.State.BLOCKED;
	}

	public static boolean isTimedWaiting() {
		return getState() == Thread.State.TIMED_WAITING;
	}

	public static boolean isTerminated() {
		return getState() == Thread.State.TERMINATED;
	}

	public static boolean isInterrupted() {
		return getCurrent().isInterrupted();
	}

	public static String getTrait() {
		return String.format("%s:%06d", SyncAvoid.PSEUDO_VM_ID, getId());
	}

	// 延迟加载
	private static class SyncAvoid {

		private static final String PSEUDO_VM_ID = UUIDs.classic32();
	}

}
