package examples.controller;

import com.github.yingzhuo.turbocharger.captcha.CaptchaService;
import com.github.yingzhuo.turbocharger.webmvc.support.response.ImageResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CaptchaController {

	private final CaptchaService captchaService;

	@GetMapping("/captcha")
	public ImageResponseEntity captcha() {
		final var captcha = this.captchaService.create();

		return ImageResponseEntity.builder()
			.status(HttpStatus.OK)
			.format("png")
			.image(captcha.getImage())
			.build();
	}

}
