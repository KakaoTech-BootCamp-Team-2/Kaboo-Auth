package kaboo.kaboo_auth.domain.dto.response;

import java.util.List;

import kaboo.kaboo_auth.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberListResponse {
	private int classNum;
	private int memberNum;
	private MemberInfoResponse[] memberList;

	public MemberListResponse(List<Member> members, int classNum) {
		this.classNum = classNum;
		this.memberNum = members.size();
		this.memberList = members.stream()
				.map(MemberInfoResponse::new)
				.toArray(MemberInfoResponse[]::new);
	}
}
