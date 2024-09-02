package kaboo.kaboo_auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kaboo.kaboo_auth.domain.CustomUserDetails;
import kaboo.kaboo_auth.domain.UserRole;
import kaboo.kaboo_auth.domain.dto.response.KakaoResponse;
import kaboo.kaboo_auth.domain.dto.response.OAuth2Response;
import kaboo.kaboo_auth.domain.entity.Member;
import kaboo.kaboo_auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${AUTH.PASSWORD_POSTFIX}")
	String passwordPostfix;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		OAuth2Response response;
		if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			response = new KakaoResponse(oauth2User.getAttributes());
		} else {
			throw new IllegalArgumentException("사용할  수 없는 인증방법입니다.");
		}

		String provider = response.getProvider();
		String providerId = response.getProviderId();

		// 중복이 발생하지 않도록 provider와 providerId를 조합
		String username = provider + "_" + providerId;
		String email = response.getEmail();
		String nickname = response.getNickname();

		Optional<Member> byUsername = memberRepository.findByUsername(username);
		Member member = null;
		if (byUsername.isEmpty()) {
			String rawPassword = username + passwordPostfix;
			member = Member.builder()
					.username(username)
					.koreaName(nickname)
					.email(email)
					.password(passwordEncoder.encode(rawPassword))
					.role(UserRole.ROLE_USER)
					.build();

			memberRepository.save(member);
		} else {
			member = byUsername.get();
		}

		return new CustomUserDetails(member);
	}
}
