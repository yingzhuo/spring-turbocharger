package examples.github;

import com.github.yingzhuo.turbocharger.core.EnvironmentUtils;
import com.github.yingzhuo.turbocharger.webcli.annotation.RestClientInterface;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.HashMap;
import java.util.Map;

@RestClientInterface
@HttpExchange(url = "${github.endpoint}")
public interface Github {

	public default User whoami() {
		final Map<String, Object> headers = new HashMap<>();
		headers.put("X-GitHub-Api-Version", "2022-11-28");
		headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + EnvironmentUtils.getPropertyValue("GITHUB_ACCESS_TOKEN"));
		headers.put(HttpHeaders.ACCEPT, "application/json;charset=utf-8");
		headers.put(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");
		return whoami(headers);
	}

	@GetExchange(url = "/user")
	public User whoami(@RequestHeader Map<String, Object> headers);
}
