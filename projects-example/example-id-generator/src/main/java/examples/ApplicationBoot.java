package examples;

import com.github.yingzhuo.turbocharger.idgen.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ApplicationBoot implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		System.out.println(UUIDUtils.v7());
		System.out.println(UUIDUtils.v7());
		System.out.println(UUIDUtils.v7());
		System.out.println(UUIDUtils.v7());
	}

}
