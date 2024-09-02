package kaboo.kaboo_auth.domain.dto.response;

import kaboo.kaboo_auth.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberIntroduceResponse {
	private String introduce;

	public MemberIntroduceResponse(Member member) {
		this.introduce = member.getIntroduce();
	}
}
