package examples;

import examples.github.GithubRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

	private final GithubRepo github;

	@Override
	public void run(ApplicationArguments args) {
		System.out.println(github.whoami());
	}

}
