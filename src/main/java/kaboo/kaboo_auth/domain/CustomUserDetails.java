package kaboo.kaboo_auth.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import kaboo.kaboo_auth.domain.entity.Member;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements OAuth2User, UserDetails {
	private final Member member;

	@Override
	public String getUsername() {
		return member.getUsername();
	}

	@Override
	public String getName() {
		return member.getNickname();
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add((GrantedAuthority)() -> member.getRole().toString());

		return collection;
	}
}
