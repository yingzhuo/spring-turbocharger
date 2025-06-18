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
package examples.github;

import com.github.yingzhuo.turbocharger.jackson.util.JsonUtils;
import com.github.yingzhuo.turbocharger.webcli.cli.Apache5ClientHttpRequestFactoryFactories;
import com.github.yingzhuo.turbocharger.webcli.error.NoopResponseErrorHandler;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GithubRepoImpl implements GithubRepo, InitializingBean {

	private @Autowired Environment environment;
	private @Autowired(required = false) RestClient restClient;

	@Override
	@Nullable
	public User whoami() {
		var uri = "/user";

		var body = restClient.get()
			.uri(uri)
			.retrieve()
			.body(String.class);

		if (body == null) {
			return null;
		}

		return JsonUtils.parseJson(body, "$", User.class);
	}

	@Override
	public void afterPropertiesSet() {
		var token = environment.getProperty("GITHUB_ACCESS_TOKEN");
		if (token == null || token.isBlank()) {
			throw new BeanCreationException("token is null or blank");
		}

		this.restClient = RestClient.builder()
			.requestFactory(Apache5ClientHttpRequestFactoryFactories.create())
			.baseUrl("https://api.github.com:443/")
			.defaultHeaders(headers -> {
				headers.add("X-GitHub-Api-Version", "2022-11-28");
				headers.setAccept(List.of(MediaType.APPLICATION_JSON));
				headers.setBearerAuth(token);
			})
			.defaultStatusHandler(NoopResponseErrorHandler.getInstance())
			.build();
	}

}
