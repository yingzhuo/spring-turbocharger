package examples.controller;

import com.github.yingzhuo.turbocharger.jwt.JwtData;
import com.github.yingzhuo.turbocharger.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/security")
public class SecurityController {

	public final JwtService jwtService;

	@PostMapping("/login")
	public Object login(@RequestParam("username") String username, @RequestParam("password") String password) {
		if (username.equals("admin") && password.equals("admin")) {
			var jwtData = JwtData.newInstance()
				.addPayload("userId", 1L)
				.addPayload("username", "admin")
				.addPayload("roles", List.of("ROLE_ADMIN", "ROLE_USER"));

			var jwt = jwtService.createToken(jwtData);
			return Map.of(
				"token", jwt
			);
		}

		return Map.of(
			"error", "登录失败"
		);
	}

	@PostMapping("/show")
	public Object show(Authentication authentication) {
		log.debug("authorities: {}", authentication.getAuthorities());
		return Map.of();
	}

}
