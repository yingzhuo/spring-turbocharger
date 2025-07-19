package examples.security;

import com.github.yingzhuo.turbocharger.jackson.util.JsonUtils;
import com.github.yingzhuo.turbocharger.jwt.JwtService;
import com.github.yingzhuo.turbocharger.security.authentication.TokenToUserConverter;
import com.github.yingzhuo.turbocharger.security.jwt.AbstractJwtTokenToUserConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.stream.Stream;

@Component
public class JwtTokenToUserConverter extends AbstractJwtTokenToUserConverter implements TokenToUserConverter {

	public JwtTokenToUserConverter(JwtService jwtService) {
		super(jwtService);
	}

	@Nullable
	@Override
	protected UserDetails doAuthenticate(String headerJson, String payloadJson) throws AuthenticationException {
		return JsonUtils.parseJson(payloadJson, UserAttributes.class)
			.toUserDetails();
	}

	// -----------------------------------------------------------------------------------------------------------------

	@Getter
	@Setter
	@ToString
	private static class UserAttributes implements Serializable {
		private String userId;
		private String username;
		private String[] roles;

		private UserDetails toUserDetails() {
			return User.builder()
				.username(username)
				.password("<DO NOT NEED PASSWORD>")
				.roles(prettyRoles())
				.disabled(false)
				.accountExpired(false)
				.credentialsExpired(false)
				.build();
		}

		private String[] prettyRoles() {
			return Stream.of(roles)
				.map(role -> {
					if (role.startsWith("ROLE_")) {
						return role.substring("ROLE_".length()).trim();
					} else {
						return role.trim();
					}
				})
				.toList()
				.toArray(new String[0]);
		}
	}

}
