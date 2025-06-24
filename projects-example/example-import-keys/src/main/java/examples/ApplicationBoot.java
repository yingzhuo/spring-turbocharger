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

import com.github.yingzhuo.turbocharger.key.autoconfiguration.ImportKeyBundleFromPem;
import com.github.yingzhuo.turbocharger.key.autoconfiguration.ImportKeyBundleFromStore;
import com.github.yingzhuo.turbocharger.key.autoconfiguration.ImportUserDefaultKeyStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyStore;

@SpringBootApplication
@ImportKeyBundleFromPem(beanName = "kb1", location = "classpath:/rsa2048-nopassword.pem")
@ImportKeyBundleFromStore(beanName = "kb3", location = "classpath:keystore.p12", storepass = "123456", aliasOfStore = "rsa2048")
@ImportUserDefaultKeyStore(beanName = "defaultKeyStore", storepass = "123456")
public class ApplicationBoot implements ApplicationRunner {

	@Autowired
	@Qualifier("defaultKeyStore")
	private KeyStore defaultKeyStore;

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(defaultKeyStore);
	}
}
