package kaboo.kaboo_auth.domain.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kaboo.kaboo_auth.domain.dto.response.LoginSucessResponse;
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

	@Value("${AUTH.REDIRECT_URL}")
	private String redirectURL;

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

		LoginSucessResponse loginSucessResponse = new LoginSucessResponse(username, accessToken, refreshToken);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = objectMapper.writeValueAsString(loginSucessResponse);

		// 응답 설정
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// JSON 응답을 출력 스트림에 작성
		response.getWriter().write(jsonResponse);
	}
}
