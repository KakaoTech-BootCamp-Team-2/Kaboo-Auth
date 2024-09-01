package kaboo.kaboo_auth.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberListResponse {
	private int classNum;
	private int memberNum;
	private MemberInfoResponse[] memberList;
}
