package examples;

import com.github.yingzhuo.turbocharger.hocon.HoconPropertySourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@PropertySource(value = "classpath:examples.conf", factory = HoconPropertySourceFactory.class)
@SpringBootApplication
public class ApplicationBoot implements ApplicationRunner {

	@Value("${github.url}")
	private String githubUrl;

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		log.debug("-----");
		log.debug("githubUrl: {}", githubUrl);
		log.debug("-----");
	}

}
