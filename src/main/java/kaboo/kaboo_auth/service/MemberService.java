package kaboo.kaboo_auth.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kaboo.kaboo_auth.domain.dto.request.MemberInfoUpdateRequest;
import kaboo.kaboo_auth.domain.dto.response.MemberInfoResponse;
import kaboo.kaboo_auth.domain.dto.response.MemberListResponse;
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

	public MemberListResponse getAllMembers() {
		List<Member> members = memberRepository.findAll();
		return new MemberListResponse(members, 0);
	}

	public MemberListResponse getMembersByClassNum(int classNum) {
		List<Member> members = memberRepository.findByClassNum(classNum);
		return new MemberListResponse(members, classNum);
	}

	public MemberInfoResponse getMemberInfoByKoreaName(String koreaName) {
		return new MemberInfoResponse(
				getMember(koreaName));
	}

	@Transactional
	public MemberInfoResponse updateMemberInfoByKoreaName(String koreaName, MemberInfoUpdateRequest request) {
		Member member = getMember(koreaName);

		member.updateInfo(request.getKoreaName(), request.getEnglishName(), request.getClassNum(), request.getCourse());

		return new MemberInfoResponse(member);
	}

	public String getMemberIntroduceByKoreaName(String koreaName) {
		return getMember(koreaName).getIntroduce();
	}

	@Transactional
	public String updateMemberIntroduceByKoreaName(String koreaName, String request) {
		Member member = getMember(koreaName);
		member.updateIntroduce(request);

		return member.getIntroduce();
	}

	private Member getMember(String koreaName) {
		return memberRepository.findByKoreaName(koreaName)
				.orElseThrow(() -> new IllegalStateException(koreaName + "을 찾을 수 없습니다. 다시 한번 확인해주세요."));
	}
}
