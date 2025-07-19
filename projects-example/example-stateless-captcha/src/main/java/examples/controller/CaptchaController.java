package examples.controller;

import com.github.yingzhuo.turbocharger.captcha.CaptchaService;
import com.github.yingzhuo.turbocharger.captcha.EncodedCaptcha;
import com.github.yingzhuo.turbocharger.captcha.support.AccessKeyGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CaptchaController {

	private final CaptchaService captchaService;
	private final AccessKeyGenerator accessKeyGenerator;

	@GetMapping("/captcha")
	public Map<String, Object> captcha() {
		final var captcha = this.captchaService.create();
		final var encodedCaptcha = EncodedCaptcha.of(captcha);

		return Map.of(
			"encoded-captcha", encodedCaptcha.getEncodedImage(),
			"word", encodedCaptcha.getWord()
		);
	}

}
