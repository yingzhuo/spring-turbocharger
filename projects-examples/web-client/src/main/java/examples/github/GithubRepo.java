package examples.github;

import org.springframework.lang.Nullable;

@FunctionalInterface
public interface GithubRepo {

	@Nullable
	public User whoami();

}
