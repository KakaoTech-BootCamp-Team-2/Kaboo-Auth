package kaboo.kaboo_auth.domain.jwt.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kaboo.kaboo_auth.domain.CustomUserDetails;
import kaboo.kaboo_auth.domain.entity.Member;
import kaboo.kaboo_auth.domain.jwt.JwtTokenProvider;
import kaboo.kaboo_auth.domain.jwt.entity.JwtAccessToken;
import kaboo.kaboo_auth.domain.jwt.entity.JwtRefreshToken;
import kaboo.kaboo_auth.domain.jwt.repository.JwtAccessTokenRepository;
import kaboo.kaboo_auth.domain.jwt.repository.JwtRefreshTokenRepository;
import kaboo.kaboo_auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final MemberRepository memberRepository;
	private final JwtAccessTokenRepository jwtAccessTokenRepository;
	private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 쿠키가 없다면 다음 로직으로
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			chain.doFilter(request, response);
			return;
		}

		String username = null, cookieAccessToken = null, cookieRefreshToken = null;
		for (Cookie cookie : cookies) {
			switch (cookie.getName()) {
				case "Username" -> username = cookie.getValue();
				case "Authorization" -> cookieAccessToken = cookie.getValue();
				case "RefreshToken" -> cookieRefreshToken = cookie.getValue();
			}
		}

		// 쿠키에 토큰이 있는지 검사
		if (cookieAccessToken == null || username == null) {
			chain.doFilter(request, response);
			return;
		}
		Optional<JwtAccessToken> jwtAccessToken = jwtAccessTokenRepository.findById(username);

		// 요청한 사용자정보로 redis 에 accessToken 이 존재할 때
		if (jwtAccessToken.isPresent()) {
			JwtAccessToken accessToken = jwtAccessToken.get();

			// 토큰에서 꺼낸 사용자 id와 전달 받은 사용자 id가 같고
			// Redis에 저장된 AccessToken과 전달 받은 AccessToken이 일치할 때
			if (accessToken.getUsername().equals(username) && accessToken.getAccessToken()
					.equals(cookieAccessToken)) {
				Member member = memberRepository.findByUsername(username) // 해당 사용자 id 로 사용자 정보가 있는지 찾기
						.orElseThrow(() -> {
							// 로그인에 성공한 사람들(사용자 정보가 있는 경우)만 토큰을 부여받기 때문에 예외를 던진다면 데이터베이스 오류이거나 로그인 로직에 버그가 있는 것
							return new UsernameNotFoundException("해당 사용자 정보를 찾을 수 없습니다. [DB OR 로그인 로직 버그]");
						});

				CustomUserDetails userDetails = new CustomUserDetails(member);
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication);
				chain.doFilter(request, response);

				return;
			}
		}

		if (cookieRefreshToken != null) {    // 리프레시 토큰이 전달되었다면
			Optional<JwtRefreshToken> jwtRefreshToken = jwtRefreshTokenRepository.findById(username);
			if (jwtRefreshToken.isPresent()) { // Redis에 리프레시 토큰이 존재한다면
				JwtRefreshToken refreshToken = jwtRefreshToken.get();

				// 토큰에서 꺼낸 사용자 id와 전달 받은 사용자 id가 같고
				// Redis에 저장된 AccessToken과 전달 받은 AccessToken이 일치할 때
				if (refreshToken.getUsername().equals(username) && refreshToken.getRefreshToken()
						.equals(cookieRefreshToken)) {
					JwtAccessToken newAccessToken = new JwtAccessToken(username,
							jwtTokenProvider.createAccessToken(username));

					jwtAccessTokenRepository.save(newAccessToken);
					response.addCookie(createCookie("Authorization", newAccessToken.getAccessToken()));
				}
			}
		}
	}

	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(10 * 60);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		return cookie;
	}
}
