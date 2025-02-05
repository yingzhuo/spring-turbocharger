package examples;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import spring.turbo.module.configuration.env.HoconPropertySourceFactory;

@SpringBootApplication
@PropertySource(value = {"classpath:examples.conf"}, factory = HoconPropertySourceFactory.class)
public class ApplicationBoot implements ApplicationRunner {

    @Value("${github.url}")
    private String githubUrl;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBoot.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("---");
        System.out.println(githubUrl);
        System.out.println("---");
    }

}
