package examples.security;

import com.github.yingzhuo.turbocharger.jackson.util.JsonUtils;
import com.github.yingzhuo.turbocharger.jwt.JwtAssertions;
import com.github.yingzhuo.turbocharger.jwt.JwtService;
import com.github.yingzhuo.turbocharger.security.authentication.TokenToUserConverter;
import com.github.yingzhuo.turbocharger.security.jwt.AbstractJwtTokenToUserConverter;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author 应卓
 */
@Component
public class JwtTokenToUserConverter extends AbstractJwtTokenToUserConverter implements TokenToUserConverter {

	public JwtTokenToUserConverter(JwtService jwtService) {
		super(jwtService);
	}

	@Nullable
	@Override
	protected JwtAssertions getJwtAssertions() {
		return null;
	}

	@Nullable
	@Override
	protected UserDetails doAuthenticate(String rawToken, String headerJson, String payloadJson) throws AuthenticationException {
		return JsonUtils.parseJson(payloadJson, UserAttributes.class);
	}

	// -----------------------------------------------------------------------------------------------------------------

	@Data
	public static class UserAttributes implements UserDetails {

		private static final String NO_PASS = UserAttributes.class.getName() + "#NO_PASS";

		private String userId;
		private String username;
		private String[] roles;

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			var roles = this.getRoles();
			if (roles == null) {
				return List.of();
			}

			return Arrays.stream(roles)
				.map(SimpleGrantedAuthority::new)
				.toList();
		}

		@Override
		public String getPassword() {
			return NO_PASS;
		}

	}

}
