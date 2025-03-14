/*
 *
 * Copyright 2022-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
