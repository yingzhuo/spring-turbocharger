package com.github.yingzhuo.turbocharger.util.io;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;

public final class DirectoryWatcher {

	private final Path directory;
	private final Listener listener;

	private DirectoryWatcher(Path directory, Listener listener) {
		this.directory = directory;
		this.listener = listener;
	}

	public static Builder builder() {
		return new Builder();
	}

	public void start() {
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();

			directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);

			WatchKey key;
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					listener.execute("" + event.kind(), "" + event.context());
				}
				key.reset();
			}

		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	// -----------------------------------------------------------------------------------------------------------------

	@FunctionalInterface
	public static interface Listener {
		public void execute(String kind, String file);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static final class Builder {

		private Path dir;
		private Listener listener;

		private Builder() {
		}

		public Builder directory(String first, String... more) {
			this.dir = Paths.get(first, more);
			return this;
		}

		public Builder directory(Path dir) {
			this.dir = dir;
			return this;
		}

		public Builder directory(File dir) {
			this.dir = FileUtils.toPath(dir);
			return this;
		}

		public DirectoryWatcher build() {
			return new DirectoryWatcher(dir, listener);
		}
	}

}
