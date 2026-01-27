package examples.github;

import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface GithubRepo {

	@Nullable
	public User whoami();

}
