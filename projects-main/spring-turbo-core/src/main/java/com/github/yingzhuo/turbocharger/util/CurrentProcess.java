package com.github.yingzhuo.turbocharger.util;

public final class CurrentProcess {

	private CurrentProcess() {
		super();
	}

	public static long pid() {
		return SyncAvoid.PROCESS_HANDLE.pid();
	}

	public static long parentPid() {
		return SyncAvoid.PROCESS_HANDLE.parent()
			.map(ProcessHandle::pid)
			.orElse(-1L);
	}

	public static String user() {
		return SyncAvoid.PROCESS_HANDLE
			.info()
			.user()
			.orElse(StringPool.EMPTY);
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final ProcessHandle PROCESS_HANDLE = ProcessHandle.current();
	}

}
