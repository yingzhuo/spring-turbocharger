package examples;

import examples.github.GithubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

	@Autowired
	private GithubRepo github;

	@Override
	public void run(ApplicationArguments args) {
		System.out.println(github.whoami());
	}

}
