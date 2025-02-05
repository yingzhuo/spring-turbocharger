package examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.turbo.module.misc.qrcode.QRCodeGenerator;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ApplicationBoot implements ApplicationRunner {

    private final QRCodeGenerator gen;


    public static void main(String[] args) {
        SpringApplication.run(ApplicationBoot.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        var s = gen.generate("Hello SpringTurbocharger");
        log.debug("Generated QR Code: {}", s);
    }

}
