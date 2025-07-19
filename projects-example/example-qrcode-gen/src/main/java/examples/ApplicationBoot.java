package examples;

import com.github.yingzhuo.turbocharger.zxing.qrcode.QRCodeGenerator;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		var s = gen.generate("hello", null, ErrorCorrectionLevel.H, 400);
		log.debug("Generated QR Code: {}", s);
	}

}
