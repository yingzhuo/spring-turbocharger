package examples.controller;

import com.github.yingzhuo.turbocharger.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

	public final JwtService jwtService;

	@PostMapping
	public Object login(@RequestParam("username") String username, @RequestParam("password") String password) {
		throw new UnsupportedOperationException();
	}

}
