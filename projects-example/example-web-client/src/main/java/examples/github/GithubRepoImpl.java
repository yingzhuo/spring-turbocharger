package examples.github;

import com.github.yingzhuo.turbocharger.jackson.util.JsonUtils;
import com.github.yingzhuo.turbocharger.webcli.cli.Apache5ClientHttpRequestFactoryFactories;
import com.github.yingzhuo.turbocharger.webcli.error.NoopResponseErrorHandler;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
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
