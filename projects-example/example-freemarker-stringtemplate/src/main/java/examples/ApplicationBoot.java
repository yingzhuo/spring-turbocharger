package examples;

import com.github.yingzhuo.turbocharger.freemarker.StringTemplateRenderer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ApplicationBoot implements ApplicationRunner {

	private final StringTemplateRenderer renderer;

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		System.out.println(
			renderer.render(
				"hello",
				Map.of("freemarker", "freemarker")
			)
		);
	}

}
