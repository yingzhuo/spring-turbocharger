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
