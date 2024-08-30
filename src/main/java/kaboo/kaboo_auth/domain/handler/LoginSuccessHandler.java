package kaboo.kaboo_auth.domain.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kaboo.kaboo_auth.domain.jwt.JwtTokenProvider;
import kaboo.kaboo_auth.domain.jwt.entity.JwtAccessToken;
import kaboo.kaboo_auth.domain.jwt.entity.JwtRefreshToken;
import kaboo.kaboo_auth.domain.jwt.repository.JwtAccessTokenRepository;
import kaboo.kaboo_auth.domain.jwt.repository.JwtRefreshTokenRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtAccessTokenRepository jwtAccessTokenRepository;
	private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final int accessTokenValidTime = 10 * 60; // 유효기간 : 10분
	private final int refreshTokenValidTime = 10 * 24 * 60 * 60; // 유효기간 : 10일

	private Cookie createCookie(String key, String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		cookie.setHttpOnly(true);

		return cookie;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws
			IOException {
		String username = authentication.getName();
		String accessToken = jwtTokenProvider.createAccessToken(username);
		String refreshToken = jwtTokenProvider.createRefreshToken(username);

		jwtAccessTokenRepository.save(new JwtAccessToken(username, accessToken));
		jwtRefreshTokenRepository.save(new JwtRefreshToken(username, refreshToken));

		response.addCookie(createCookie("Username", username, refreshTokenValidTime));
		response.addCookie(createCookie("Authorization", accessToken, accessTokenValidTime));
		response.addCookie(createCookie("RefreshToken", refreshToken, refreshTokenValidTime));
		response.sendRedirect("/");
	}
}
