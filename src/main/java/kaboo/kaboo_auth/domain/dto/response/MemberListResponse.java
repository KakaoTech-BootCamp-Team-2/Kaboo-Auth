package kaboo.kaboo_auth.domain.dto.response;

import java.util.List;

import kaboo.kaboo_auth.domain.entity.Member;
import lombok.Getter;

@Getter
public class MemberListResponse {
	private final int classNum;
	private final int memberNum;
	private final List<MemberInfoResponse> memberList;

	public MemberListResponse(List<Member> members, int classNum) {
		this.classNum = classNum;
		this.memberNum = members.size();
		this.memberList = members.stream()
				.map(MemberInfoResponse::new)
				.toList();
	}
}
