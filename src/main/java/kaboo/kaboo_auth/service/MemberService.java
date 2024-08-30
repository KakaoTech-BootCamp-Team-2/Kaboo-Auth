package kaboo.kaboo_auth.service;

import org.springframework.stereotype.Service;

import kaboo.kaboo_auth.domain.entity.Member;
import kaboo.kaboo_auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public Member getMemberByUsername(String username) {
		return memberRepository.findByUsername(username).orElseThrow(() ->
				new IllegalStateException("존재하지 않는 ID 입니다.")
		);
	}
}
